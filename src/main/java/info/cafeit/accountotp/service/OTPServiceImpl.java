package info.cafeit.accountotp.service;

import info.cafeit.accountotp.model.SmsCodes;
import info.cafeit.accountotp.model.Users;
import info.cafeit.accountotp.repository.SmsCodeRepository;
import info.cafeit.accountotp.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;


@Service
@AllArgsConstructor
public class OTPServiceImpl implements OTPService {

    private final SmsCodeRepository smsCodeRepository;
    private final UsersRepository usersRepository;

    @Override
    @Transactional
    public boolean requestOTP(String phoneNum) {
        Users user = usersRepository.findUserbyPhoneNumber(phoneNum);
        if (user != null) {
            SmsCodes sms = smsCodeRepository.findByUserId(user.getId());
            if (sms != null) {
                smsCodeRepository.delete(sms);
            }
            SmsCodes smsCode = new SmsCodes();
            smsCode.setCode(RandomStringUtils.randomNumeric(6));
            smsCode.setStatus(false);
            smsCode.setCreateAt(new Date());
            smsCode.setUser(user);
            smsCodeRepository.save(smsCode);
        }
        return false;
    }


}
