package info.cafeit.accountotp.controller;

import info.cafeit.accountotp.service.OTPService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1")
public class OTPController {

    private final OTPService otpService;

    @RequestMapping(value = "/requestOTP/{phoneNum}")
    public void requestOTP(@PathVariable("phoneNum") String phoneNum) {
        otpService.requestOTP(phoneNum);
    }


    @RequestMapping(value = "/verify")
    public void verifyOTP() {

    }

}
