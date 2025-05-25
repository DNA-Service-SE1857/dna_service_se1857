package swp_project.dna_service.service;


import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import swp_project.dna_service.dto.request.AuthenticationRequest;
import swp_project.dna_service.dto.request.IntrospectRequest;
import swp_project.dna_service.dto.request.LogoutRequest;
import swp_project.dna_service.dto.request.RefreshRequest;
import swp_project.dna_service.dto.response.AuthenticationResponse;
import swp_project.dna_service.dto.response.IntrospectResponse;
import swp_project.dna_service.entity.InvalidatedToken;
import swp_project.dna_service.entity.User;
import swp_project.dna_service.exception.AppException;
import swp_project.dna_service.exception.ErrorCode;
import swp_project.dna_service.repository.InvalidatedTokenRepository;
import swp_project.dna_service.repository.UserRepository;

import java.text.ParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    InvalidatedTokenRepository invalidatedTokenRepository;
    UserRepository userRepository;
    @Lazy
    PasswordEncoder passwordEncoder;
    
    @NonFinal
    @Value("${jwt.signerKey}")
    String SignerKey;
    
    public AuthenticationResponse login(AuthenticationRequest request) {
        log.info("Processing login request for user: {}", request.getUsername());
        
        var user = userRepository.findUserByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
                
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if(!authenticated) throw new AppException(ErrorCode.UNAUTHENTICATED);

        var token = generateToken(user);
        log.info("User {} authenticated successfully", user.getUsername());
        return AuthenticationResponse.builder()
                .authenticated(true)
                .token(token)
                .build();
    }

    public void logout(LogoutRequest token) {
        log.info("Processing logout request for user with token: {}", token);
        try {
            var signedToken = verifyToken(token.getToken(), false);
            String jit = signedToken.getJWTClaimsSet().getJWTID();


            if (invalidatedTokenRepository.existsById(jit)) {
                throw new AppException(ErrorCode.TOKEN_ALREADY_EXISTS);
            }

            Date expirationTime = signedToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jit)
                    .expiryDate(expirationTime)
                    .build();
            invalidatedTokenRepository.save(invalidatedToken);
        } catch (JOSEException | ParseException e) {
            log.error("Invalid token during logout: {}", e.getMessage());
            throw new AppException(ErrorCode.LOGOUT_ERROR);
        }
    }


    private SignedJWT verifyToken(String token , boolean isRefresh)
            throws JOSEException, ParseException {

        JWSVerifier verifier = new MACVerifier(SignerKey.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expirationTime = (isRefresh)
                ? new Date(
                signedJWT
                        .getJWTClaimsSet()
                        .getIssueTime()
                        .toInstant()
                        .plus(1, ChronoUnit.HOURS)
                        .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expirationTime.after(new Date())))
            throw new AppException(ErrorCode.UNAUTHENTICATED);


        return signedJWT;
    }
    public IntrospectResponse introspect(IntrospectRequest request)
            throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;

        try {
            verifyToken(token, false);
        } catch (AppException e) {
            isValid = false;
        }

        return IntrospectResponse.builder().valid(isValid).build();
    }


    private String generateToken (User user){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512) ;

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("DNA Service")
                .issueTime(new java.util.Date())
                .expirationTime(Date.from(new java.util.Date().toInstant().plus(1, ChronoUnit.HOURS)))
                .claim("userId", user.getId())
                .jwtID(UUID.randomUUID().toString())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SignerKey.getBytes()));
            return  jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Error signing JWT: {}", e.getMessage());
                throw new AppException(ErrorCode.JWT_SIGNING_ERROR);
        }
    }

    public AuthenticationResponse refresh(RefreshRequest request)
            throws ParseException, JOSEException {
        var sighToken = verifyToken(request.getToken(), true);

        var jit = sighToken.getJWTClaimsSet().getJWTID();
        var expirationTime = sighToken.getJWTClaimsSet().getExpirationTime();

        if (invalidatedTokenRepository.existsById(jit)) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        InvalidatedToken invalidatedToken = InvalidatedToken.builder().id(jit).expiryDate(expirationTime).build();

        invalidatedTokenRepository.save(invalidatedToken);

        var username = sighToken.getJWTClaimsSet().getSubject();

        var user = userRepository
                .findUserByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        var token = generateToken(user);

        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

}