package ch.heigvd.amt.api.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public class ApiResponseBuilder {

    public static ResponseEntity badRequestMessage(String message) {
        HttpHeaders responseHeaders = ApiResponseBuilder.errorHeader(message);
        return ResponseEntity.badRequest().headers(responseHeaders).build();
    }

    public static ResponseEntity conflictMessage(URI location) {
        String message = "A double of the same item already exists : ";
        HttpHeaders responseHeaders = ApiResponseBuilder.errorHeader(message);
        return ResponseEntity.status(HttpStatus.CONFLICT).headers(responseHeaders).location(location).build();
    }

    public static ResponseEntity unauthorizedMessage() {
        String message = "Please indicate your API key in the 'apiKey' header";
        HttpHeaders responseHeaders = ApiResponseBuilder.errorHeader(message);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(responseHeaders).build();
    }

    public static ResponseEntity forbiddenMessage() {
        String message = "Authentication error, you do not have access to this object";
        HttpHeaders responseHeaders = ApiResponseBuilder.errorHeader(message);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).headers(responseHeaders).build();
    }

    private static HttpHeaders errorHeader(String message) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("error-message", message);
        return responseHeaders;
    }
}
