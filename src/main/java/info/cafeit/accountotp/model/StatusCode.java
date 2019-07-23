package info.cafeit.accountotp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public enum StatusCode {

    SENT_OK(1208, "Sent OTP successfully!"),

    SENT_ERROR(1209, "Sent OTP error!"),

    USER_NOT_EXISTS(1210, "User does not exists!"),

    VERIFY_OK(1211, "Verification success!"),

    CODE_EXPIRED(1212, "Code expired!"),

    VERIFY_ERROR(1213, "Verification error!");

    private final int value;

    private final String reasonPhrase;


    StatusCode(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int value() {
        return this.value;
    }


    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

}
