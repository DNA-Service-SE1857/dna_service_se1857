package swp_project.dna_service.controller;


import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swp_project.dna_service.service.QRcode.VietQRService;


@RestController
@RequestMapping("/pay")
public class VietQRController {

    @Autowired
    private VietQRService vietQRService;

    @GetMapping("/generate")
    public ResponseEntity<Resource> generateQR(
            @RequestParam String accountNumber,
            @RequestParam String bankCode,
            @RequestParam String accountName,
            @RequestParam int amount) {

        Resource qrImage = vietQRService.generateVietQR(accountNumber, bankCode, accountName, amount);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(qrImage);
    }
}
