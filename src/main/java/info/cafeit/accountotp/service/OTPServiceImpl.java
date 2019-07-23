package info.cafeit.accountotp.service;

import info.cafeit.accountotp.model.*;
import info.cafeit.accountotp.provider.OneWayGateway;
import info.cafeit.accountotp.repository.SmsCodeRepository;
import info.cafeit.accountotp.repository.UsersRepository;
import info.cafeit.accountotp.utils.Utils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;


@Service

@Slf4j
@RequiredArgsConstructor
public class OTPServiceImpl implements OTPService {

    @NonNull
    private final SmsCodeRepository smsCodeRepository;
    @NonNull
    private final UsersRepository usersRepository;
    @NonNull
    private final OneWayGateway smsGateway;
    @NonNull
    private final OneWayRequest oneWayRequest;

    @Value("${otp.timeOut}")
    long timeout;

    @Override
    @Transactional
    public Result requestOTP(String phoneNum) {
        Result result = new Result();
        Result.Data data = new Result.Data();
        Users user = usersRepository.findUserbyPhoneNumber(phoneNum);
        if (user != null) {
            SmsCodes sms = smsCodeRepository.findByUserId(user.getId());
            if (sms != null) {
                //TODO xữ lý giới hạn số lần send trong ngày
                smsCodeRepository.delete(sms);
            }
            SmsCodes smsCode = new SmsCodes().builder()
                    .code(RandomStringUtils.randomNumeric(6))
                    .status(false)
                    .createAt(new Date())
                    .user(user)
                    .build();
            smsCodeRepository.save(smsCode);

            //set phone number and sent
            oneWayRequest.setMobileno(phoneNum);
            oneWayRequest.setMessage(String.format(oneWayRequest.getMessage(), smsCode.getCode()));
            OneWayResponse response = smsGateway.requestOTP(oneWayRequest);

            if (response != null) {
                response(StatusCode.SENT_OK, phoneNum, StatusCode.SENT_OK.getReasonPhrase(), result, data);

            } else {
                response(StatusCode.SENT_ERROR, phoneNum, StatusCode.SENT_ERROR.getReasonPhrase(), result, data);
            }
        } else {
            response(StatusCode.USER_NOT_EXISTS, phoneNum, StatusCode.USER_NOT_EXISTS.getReasonPhrase(), result, data);
        }
        result.setData(data);
        return result;
    }


    @Override
    @Transactional
    public Result verify(String phone, String code) {
        Result result = new Result();
        Result.Data data = new Result.Data();
        Users users = usersRepository.findUserbyPhoneNumber(phone);

        if (users != null && users.getSmsCode() != null) {

            if (users.getSmsCode().getStatus() && Utils.getDiffTimes(users.getSmsCode().getCreateAt(), new Date()) > timeout) {
                response(StatusCode.CODE_EXPIRED, phone, StatusCode.CODE_EXPIRED.getReasonPhrase(), result, data);
                result.setData(data);
                return result;
            }
            if (users.getSmsCode().equalsCode(code)) {
                response(StatusCode.VERIFY_OK, phone, StatusCode.VERIFY_OK.getReasonPhrase(), result, data);
                users.getSmsCode().setStatus(true);
            } else {
                response(StatusCode.VERIFY_ERROR, phone, StatusCode.VERIFY_ERROR.getReasonPhrase(), result, data);
            }
        } else {
            response(StatusCode.USER_NOT_EXISTS, phone, StatusCode.USER_NOT_EXISTS.getReasonPhrase(), result, data);
        }
        result.setData(data);
        return result;
    }





    private void response(StatusCode statusCode, String phone, String msg, Result result, Result.Data data) {
        result.setStatusCode(statusCode);
        data.setMobile(phone);
        data.setMessage(msg);
    }
}
