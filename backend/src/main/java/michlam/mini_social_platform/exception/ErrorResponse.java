package michlam.mini_social_platform.exception;

import lombok.Getter;
public class ErrorResponse {
    @Getter
    private final int status;

    @Getter
    private final String message;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
