package imtiaz.sktech.mytutoron.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    private HttpStatus httpStatus;

    private Boolean isSuccess;

    private String message;

    private Object payload;

    public static ResponseEntity<Response> getResponseEntity(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new Response(httpStatus, httpStatus.equals(HttpStatus.OK), message, null), httpStatus);
    }

    public static ResponseEntity<Response> getResponseEntity(HttpStatus httpStatus, String message, Object payload) {
        return new ResponseEntity<>(new Response(httpStatus, httpStatus.equals(HttpStatus.OK), message, payload), httpStatus);
    }

    public static ResponseEntity<Response> getResponseEntity(Boolean isSuccess, String message) {
        return new ResponseEntity<>(new Response(isSuccess ? HttpStatus.OK : HttpStatus.BAD_REQUEST, isSuccess, message, null), isSuccess ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<Response> getResponseEntity(Boolean isSuccess, String message, Object payload) {
        return new ResponseEntity<>(new Response(isSuccess ? HttpStatus.OK : HttpStatus.BAD_REQUEST, isSuccess, message, payload), isSuccess ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
