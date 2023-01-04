package nl.wondergem.wondercooks.exception;

import java.io.Serial;

public class UsernameNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public UsernameNotFoundException(){
        super();
    }
    public UsernameNotFoundException(String message) {
        super(message);
    }
}
