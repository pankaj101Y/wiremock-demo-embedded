package com.example.wiremockdemoembedded;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class WebUtils {

    public static final String WIREMOCK="http://localhost:9999/";


    public static Mono<Response> getImagesHash(Object bulkImageRequest) {
        try {
            UriComponentsBuilder uriBuilder = getUriBuilder("api/files/bulk_request");
            String uri = uriBuilder.build().toString();

            log.info("uri = {} ",uri);
            return genericPost(uri, new ParameterizedTypeReference<Response>() {
            }, bulkImageRequest, new ParameterizedTypeReference<Request>() {
            });
        } catch (IllegalArgumentException | WebClientResponseException var4) {
            throw var4;
        }
    }






    private static UriComponentsBuilder getgetBulkDisplayNamesUriBuilder() {
        return UriComponentsBuilder.fromUriString(WIREMOCK + "api/v2/polygon/displaynames/bulk");
    }



    private static UriComponentsBuilder getUriBuilder(String api) {
        return UriComponentsBuilder.fromUriString("http://localhost:9999/" + api);
    }

    public static <T> T getMonoSafely(Mono<T> request) {
        try {
            Optional<T> responseOptional = request.blockOptional();
            if (responseOptional.isPresent()) {
                return responseOptional.get();
            }
        } catch (Exception exception) {
            log.error(" exception!!!!!! : {}",exception.getMessage());
        }
        return  null;
    }


    public static <T, E> Mono<T> genericPost(String uri,
                                             ParameterizedTypeReference<T> typeReference, Object body,
                                             ParameterizedTypeReference<E> requestType) {
        try {
            return WebClient.create()
                    .post()
                    .uri(URI.create(uri))
                    .header("Content-Type", "application/json")
                    .body(Mono.just(body), requestType)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError,
                            error -> Mono.error(new RuntimeException("UNABLE_TO_GET_EXCEPTION_MESSAGE "+ uri)))
                    .onStatus(HttpStatus::is5xxServerError,
                            error -> Mono.error(new RuntimeException("SERVER_NOT_RESPONDING")))
                    .bodyToMono(typeReference);
        } catch (WebClientResponseException e) {
            log.error("Exception in creating  web client for uri {} with exception {}", uri, e);
            throw e;
        }
    }

    public static <T> Mono<T> genericGet(String uri,
                                         ParameterizedTypeReference<T> typeReference) {
        try {
            return WebClient.create()
                    .get()
                    .uri(URI.create(uri))
                    .header("Content-Type", "application/json")
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError,
                            error -> Mono.error(new RuntimeException("UNABLE_TO_GET_EXCEPTION_MESSAGE "+ uri)))
                    .onStatus(HttpStatus::is5xxServerError,
                            error -> Mono.error(new RuntimeException("SERVER_NOT_RESPONDING")))
                    .bodyToMono(typeReference);
        } catch (WebClientResponseException e) {
            log.error("Exception in creating web client for uri {}", uri);
            throw e;
        }
    }



}
