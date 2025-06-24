package swp_project.dna_service.service.QRcode;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Base64;

@Service
public class VietQRService {

    public Resource generateVietQR(String accountNumber, String bankCode, String accountName, int amount) {
        RestTemplate restTemplate = new RestTemplate();

        // Map mã Napas theo bankCode
        Map<String, String> bankMap = new HashMap<>();
        bankMap.put("TPB", "970423");  // TPBank
        // thêm các ngân hàng khác nếu cần

        String acqId = bankMap.getOrDefault(bankCode.toUpperCase(), "970423");

        Map<String, Object> body = new HashMap<>();
        body.put("accountNo", accountNumber);
        body.put("accountName", accountName);
        body.put("acqId", acqId);
        body.put("amount", amount);
        body.put("addInfo", "Thanh toan cho Vo Truong Thanh Phat");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        String url = "https://api.vietqr.io/v2/generate";
        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
        String qrBase64 = (String) data.get("qrDataURL");

        // Cắt "data:image/png;base64," và convert thành byte[]
        String base64Image = qrBase64.split(",")[1];
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        return new ByteArrayResource(imageBytes);
    }
}