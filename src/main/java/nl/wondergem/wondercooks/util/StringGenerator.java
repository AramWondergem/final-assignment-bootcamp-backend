package nl.wondergem.wondercooks.util;

import lombok.experimental.UtilityClass;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@UtilityClass
public class StringGenerator {


    public static String badRequestMessageGenerator(BindingResult br) {
        StringBuilder sb = new StringBuilder();

        for (FieldError fe :
                br.getFieldErrors()) {
            sb.append(fe.getField() + ": ");
            sb.append(fe.getDefaultMessage());
            sb.append("\n");
        }

        return sb.toString();
    }


    public static URI uriGenerator(String path) {
        URI uri = URI.create(
                ServletUriComponentsBuilder.
                        fromCurrentContextPath().
                        path(path).toUriString());

        return uri;
    }

}
