package work.rothe.branch.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class EmptyGitHubResponseException extends RuntimeException {
    private final HttpStatusCode status;
    public EmptyGitHubResponseException(HttpStatusCode status, String message) {
        super(message);
        this.status = status;
    }
}
