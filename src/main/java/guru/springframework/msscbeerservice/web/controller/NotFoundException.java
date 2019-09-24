package guru.springframework.msscbeerservice.web.controller;

/**
 * Created by jt on 2019-06-06.
 */
public class NotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = -8886706832039727740L;

    public NotFoundException(String message) {
	super(message);
    }
}
