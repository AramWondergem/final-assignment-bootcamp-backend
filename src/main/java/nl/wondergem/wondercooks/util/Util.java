package nl.wondergem.wondercooks.util;

import nl.wondergem.wondercooks.exception.RecordNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class Util {
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

    public static URI uriGenerator(String path, String createdID) {
        URI uri = URI.create(
                ServletUriComponentsBuilder.
                        fromCurrentContextPath().
                        path(path + createdID).toUriString());

        return uri;
    }

    public static boolean idChecker(long id, JpaRepository repos) {

        if (id < 0) { //check if the id is positive number

            throw new IndexOutOfBoundsException("The ID should be a positive number");

        } else if (!repos.existsById(id)) { // checks if id exist in the database

            throw new RecordNotFoundException("This ID does not match with an instance in the database");

        } else {

            return true;
        }

    }
}
