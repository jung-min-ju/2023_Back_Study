package week4.tosspayments.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestClientException;
import week4.tosspayments.Dto.MessageDTO;
import week4.tosspayments.Dto.PhoneNumDTO;
import week4.tosspayments.Dto.SmsResponseDTO;
import week4.tosspayments.Service.SmsService;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@Slf4j
@Controller
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

    @GetMapping("/send")
    public String getSmsPage() {
        return "sendSms";
    }

    @PostMapping("/sms/send")
    public String sendSms(Model model, HttpSession httpSession) throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        try {
            String phoneNumber = (String) httpSession.getAttribute("phoneNumber");
            PhoneNumDTO phoneNumDTO = new PhoneNumDTO();
            phoneNumDTO.setTo(phoneNumber); // 세션에서 가져온 전화번호 설정

            System.out.println("MessageDTO 발신확인" + phoneNumDTO.getTo());
            System.out.println("MessageDTO 발신확인"+phoneNumDTO.getTo());

            SmsResponseDTO response = smsService.sendSms(phoneNumDTO,httpSession);
            log.debug("SmsResponseDTO: {}", response);
            log.debug("requestId: {}", response.getRequestId());
            model.addAttribute("response", response);
        } catch (JsonProcessingException | RestClientException | URISyntaxException | InvalidKeyException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
            // 예외 처리 로직
            e.printStackTrace();
            // 예외 메시지를 모델에 추가하거나 다른 작업을 수행할 수 있습니다.
        }
        return "smsResult";
    }
}

