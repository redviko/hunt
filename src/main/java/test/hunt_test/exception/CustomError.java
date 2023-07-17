package test.hunt_test.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class CustomError extends RuntimeException {
    private final String message;
    private final HttpStatus httpStatus;
}


