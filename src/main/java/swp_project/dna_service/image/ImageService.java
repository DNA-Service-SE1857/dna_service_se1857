package swp_project.dna_service.image;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import swp_project.dna_service.entity.DoctorCertificate;
import swp_project.dna_service.exception.AppException;
import swp_project.dna_service.exception.ErrorCode;
import swp_project.dna_service.repository.DoctorCertificateRepository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE , makeFinal = true)
public class ImageService {

    DoctorCertificateRepository doctorCertificateRepository;
    ImageRepository imageRepository;

    public void uploadImage(MultipartFile file, String doctorCertificateId) throws IOException {
        DoctorCertificate certificate = doctorCertificateRepository.findById(doctorCertificateId)
                .orElseThrow(() -> new AppException(ErrorCode.DOCTOR_NOT_FOUND));

        byte[] compressed = ImageUtils.compressImage(file.getBytes());

        Image image = Image.builder()
                .name(file.getOriginalFilename())
                .imageData(compressed)
                .doctorCertificate(certificate)
                .build();

        imageRepository.save(image);
    }

    public byte[] getDecompressedImage(String imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Ảnh không tồn tại"));

        return ImageUtils.decompressImage(image.getImageData());
    }
}
