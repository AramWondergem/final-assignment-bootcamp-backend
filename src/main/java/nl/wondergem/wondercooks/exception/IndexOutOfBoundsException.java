package nl.wondergem.wondercooks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class IndexOutOfBoundsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public IndexOutOfBoundsException() {
        super();
    }

    public IndexOutOfBoundsException(String message) {
        super(message);
    }
}
