package info.cafeit.accountotp.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public class Result {

    @Setter
    private StatusCode statusCode;
    @Getter @Setter
    private Data data;

    @Getter @Setter @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data {
        private String mobile;
        private String message;
    }

    public Integer getStatusCode() {
        return statusCode.value();
    }
}
