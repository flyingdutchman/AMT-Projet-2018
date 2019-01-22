package ch.heigvd.amt.api.util;

import org.springframework.http.HttpHeaders;

public class ApiHeaderBuilder {

    public static HttpHeaders errorMessage(String message) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("error-message", message+", please correct your requset");
        return responseHeaders;
    }
}
