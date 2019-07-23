package info.cafeit.accountotp.provider;

import info.cafeit.accountotp.model.BaseRequest;
import info.cafeit.accountotp.model.BaseResponse;
import org.springframework.stereotype.Component;



public abstract class GatewayProvider<REQEST extends BaseRequest, RESPONSE extends BaseResponse>{


    public abstract RESPONSE requestOTP(REQEST req) throws InterruptedException;

}
