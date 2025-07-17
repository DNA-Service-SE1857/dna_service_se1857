package swp_project.dna_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swp_project.dna_service.dto.request.VietQRRequest;
import swp_project.dna_service.dto.response.VietQRResponse;
import swp_project.dna_service.service.QRService;



@RestController
@RequestMapping("/qr")
public class VietQRController {

    private final QRService qrService;

    public VietQRController(QRService qrService) {
        this.qrService = qrService;
    }

    @GetMapping("/generate")
    public ResponseEntity<?> generateQR() {
        try {
            VietQRResponse response = qrService.generateQR();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi tạo QR: " + e.getMessage());
        }
    }

    @PostMapping("/generate")
    public ResponseEntity<VietQRResponse> generateQR(@RequestBody VietQRRequest request) {
        VietQRResponse response = qrService.generateQR(request);
        return ResponseEntity.ok(response);
    }
}
