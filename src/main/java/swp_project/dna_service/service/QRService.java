package swp_project.dna_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import swp_project.dna_service.dto.request.VietQRRequest;
import swp_project.dna_service.dto.response.VietQRResponse;

@Service
public class QRService {

    private final WebClient webClient;

    public QRService(WebClient webClient) {
        this.webClient = webClient;
    }

    public VietQRResponse generateQR() {
        VietQRRequest request = new VietQRRequest();
        request.setAccountNo("00001427469");
        request.setAccountName("VO TRUONG THANH PHAT");
        request.setAcqId("970423");
        request.setAmount(String.valueOf(100000));
        request.setAddInfo("Thanh toan don hang 001");
        request.setTemplate("compact");

        return webClient.post()
                .uri("/generate")
                .bodyValue(request)
                .retrieve()
                .onStatus(status -> status.isError(), response ->
                        response.bodyToMono(String.class).flatMap(body -> {
                            System.err.println("❌ API error body: " + body);
                            return Mono.error(() -> new RuntimeException("API error: " + body));
                        })
                )
                .bodyToMono(VietQRResponse.class)

                .block();
    }


    public VietQRResponse generateQR(VietQRRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.vietqr.io/v2/generate";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<VietQRRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<VietQRResponse> response = restTemplate.postForEntity(url, entity, VietQRResponse.class);

        return response.getBody();
    }
}

