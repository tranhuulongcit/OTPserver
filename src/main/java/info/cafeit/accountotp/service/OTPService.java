package info.cafeit.accountotp.service;

import info.cafeit.accountotp.model.Result;

public interface OTPService {

    Result requestOTP(String phoneNum);

    Result verify(String phone, String code);

}
