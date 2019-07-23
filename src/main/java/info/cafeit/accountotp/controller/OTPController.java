package info.cafeit.accountotp.controller;

import info.cafeit.accountotp.service.OTPService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1")
public class OTPController {

    private final OTPService otpService;

    @RequestMapping(value = "/requestOTP", method = RequestMethod.POST)
    public ResponseEntity<?> requestOTP(@RequestParam("phoneNumber") String phoneNum) {
        return ResponseEntity.ok(otpService.requestOTP(phoneNum));
    }


    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public ResponseEntity<?> verifyOTP(@RequestParam("phoneNumber") String phoneNum, @RequestParam("code") String code) {
        return ResponseEntity.ok(otpService.verify(phoneNum, code));
    }

}
