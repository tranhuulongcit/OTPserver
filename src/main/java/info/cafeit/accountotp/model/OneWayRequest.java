package info.cafeit.accountotp.model;


import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

//"file:/external.properties"
@Builder
@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
@PropertySource("classpath:onewaysms.properties")
@ConfigurationProperties(prefix = "oneway")
public class OneWayRequest implements BaseRequest, Serializable {

    private String endpoint;
    private String apiusername;
    private String apipassword;
    private String mobileno;
    private String senderid;
    private String languagetype;
    private String message;
}
