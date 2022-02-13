package com.egs.atmservice.utils;

import com.egs.atmservice.exception.*;
import com.egs.atmservice.model.dto.response.Response;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static com.egs.atmservice.common.ErrorMessages.*;

@Component
public class RestTemplateUtil {
    private final Logger logger = LoggerFactory.getLogger("rest");
    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;

    public RestTemplateUtil(ObjectMapper mapper, RestTemplate restTemplate) {
        this.mapper = mapper;
        this.restTemplate = restTemplate;
    }

    /**
     *  todo hystrix will be implement here
  /*  @HystrixCommand(ignoreExceptions = {BadRequestException.class,
            NotFoundException.class,
            UnauthorizedException.class
    })*/
    public <B, R> ResponseEntity<R> callService(String address,
                                                MultiValueMap<String, String> headers,
                                                B body,
                                                HttpMethod httpMethod,
                                                ParameterizedTypeReference<R> typeReference) {
        try {
            HttpEntity<B> request = new HttpEntity<>(body, headers);
            return restTemplate.exchange(address, httpMethod, request, typeReference);

        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof HttpStatusCodeException) {
                handleException(e);
            } else if (e instanceof ResourceAccessException) {
                throw new ServiceNotUnavailableException(SERVICE_UNAVAILABLE);
            }
            throw new InternalServerException(SERVICE_UNAVAILABLE);
        }
    }

    private void handleException(Exception e) {
        HttpStatusCodeException exception = (HttpStatusCodeException) e;
        HttpStatus status = exception.getStatusCode();
        Response<String> errorResponse;
        try {
            errorResponse = mapper.readValue(exception.getResponseBodyAsString(), new TypeReference<Response<String>>() {
            });
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            logger.info(exception.getLocalizedMessage());
            logger.info(exception.getResponseBodyAsString());
            throw new BadRequestException(INVALID_DATA);
        }
        if (errorResponse == null) {
            errorResponse = new Response<String>().setMessage(exception.getLocalizedMessage());
        }
        logger.info(String.valueOf(status.value()));
        switch (status.value()) {
            case 400:
                throw new BadRequestException(errorResponse.getMessage());
            case 404:
                throw new NotFoundException(errorResponse.getMessage());
            case 401:
                throw new UnauthorizedException(INVALID_DATA);
            case 503:
                throw new ServiceNotUnavailableException(SERVICE_UNAVAILABLE);
            case 500:
            default:
                throw new InternalServerException(SERVICE_UNAVAILABLE);
        }
    }
}