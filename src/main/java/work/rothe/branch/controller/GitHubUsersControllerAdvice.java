package work.rothe.branch.controller;

import lombok.val;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.UnknownContentTypeException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import work.rothe.branch.exception.EmptyGitHubResponseException;

import static org.springframework.http.ProblemDetail.forStatus;
import static org.springframework.http.ProblemDetail.forStatusAndDetail;

@ControllerAdvice(assignableTypes = {GitHubUsersController.class})
public class GitHubUsersControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    ProblemDetail handle(RestClientResponseException exception) {
        logger.error(exception);
        return forStatusAndDetail(exception.getStatusCode(), exception.getStatusText());
    }

    @ExceptionHandler
    ProblemDetail handle(UnknownContentTypeException exception) {
        logger.error(exception);
        return forStatusAndDetail(exception.getStatusCode(), exception.getStatusText());
    }

    @ExceptionHandler
    ProblemDetail handle(ResourceAccessException exception) {
        // IOException in GitHub request
        logger.error(exception.getMessage(), exception);
        return forStatus(HttpStatusCode.valueOf(500));
    }

    @ExceptionHandler
    ProblemDetail handle(EmptyGitHubResponseException exception) {
        val message = "Received invalid (empty) response from GitHub with status %s."
                .formatted(exception.getStatus());
        logger.error(message, exception);
        return forStatusAndDetail(
                HttpStatusCode.valueOf(500),
                "Invalid GitHub Response"
        );
    }
}
