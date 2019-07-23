package info.cafeit.accountotp.provider;


import info.cafeit.accountotp.model.OneWayRequest;
import info.cafeit.accountotp.model.OneWayResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Component
public class OneWayGateway extends GatewayProvider<OneWayRequest, OneWayResponse> {

    private OneWayResponse res = null;

    @Override
    public OneWayResponse requestOTP(OneWayRequest req) {
        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            MarubeniNetwoking.Get.create().setUrl(req.getEndpoint())
                    .setParams("apiusername", req.getApiusername())
                    .setParams("apipassword", req.getApipassword())
                    .setParams("mobileno", req.getMobileno())
                    .setParams("senderid", req.getSenderid())
                    .setParams("languagetype", req.getLanguagetype())
                    .setParams("message", req.getMessage())
                    .build().setCallback(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        res = new OneWayResponse();
                        log.info("sent success!");
                        countDownLatch.countDown();
                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    log.info("sent error!");
                }
            }).execute();
            countDownLatch.await();
        } catch (Exception ex) {
            log.error("Wating Interrupted!!");
        }
        return res;
    }
}
