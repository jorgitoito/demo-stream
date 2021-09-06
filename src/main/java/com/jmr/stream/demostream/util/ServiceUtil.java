package com.jmr.stream.demostream.util;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class ServiceUtil {

    public static final String EXCEPTION = "Exception";

    public static final String NS = "ns-";

    /**
     * Log error and create ResponseStatusException
     *
     * @param e         Exception
     * @param textError text Error
     * @return ResponseStatusException
     */
    public ResponseStatusException getResponseStatusException(@NotNull Exception e, @NotNull String textError) {
        log.debug("Exception [{}]", e.toString());
        if (e instanceof ResponseStatusException) {
            return (ResponseStatusException) e;
        }
        String error = StringUtils.hasText(e.toString()) ? e.toString() : EXCEPTION;
        int rawStatus = e instanceof FeignException ? ((FeignException) e).status() : 500;
        log.info("FeignException status [{}]", rawStatus);
        log.error(textError + ". [{}]", error);
        return new ResponseStatusException
                (
                        HttpStatus.valueOf(rawStatus),
                        textError + " : " + error,
                        e
                );
    }

    /**
     * check response is2xxSuccessful
     *
     * @param response response to check
     * @return true if is2xxSuccessful, false anything else
     */
    public boolean checkResponse(ResponseEntity<?> response) {
        if (response != null &&
                response.getBody() != null &&
                response.getStatusCode().is2xxSuccessful()) {
            return true;
        }
        log.debug("response is NULL or getStatusCode or is not is2xxSuccessful. response [{}]", response);
        return false;
    }

    /**
     * Build up query string
     *
     * @param owner owner
     * @param topic topic
     * @return query string, i.e. : topic:oasis+org:santander-group-scib
     */
    public String getQueryString(@Valid @NotNull String owner, @Valid @NotNull String topic) {
        var query = "";
        if (StringUtils.hasText(topic)) {
            query = "topic:" + topic;
        }
        if (StringUtils.hasText(owner)) {
            if (StringUtils.hasText(query)) {
                // Use whitespace instead of '+'
                query = query + " ";
            }
            query = query + "org:" + owner;
        }
        log.debug("getQueryString. query: {}", query);
        return query;
    }

    /**
     * Get variables map for GraphQL query
     *
     * @param owner organization
     * @param topic topic
     * @param size  size
     * @return variables
     */
    public Map<String, Object> getVariables(String owner, String topic, Integer size) {
        Map<String, Object> variables = new HashMap<>();
        var queryString = getQueryString(owner, topic);
        if (StringUtils.hasText(queryString)) {
            variables.put("queryString", queryString);
        }
        if (size != null) {
            variables.put("size", size);
        }
        return variables;
    }


    /**
     * Throw Exception with message by parameter
     *
     * @param error error message
     */
    public void throwException(String error) {
        log.warn(error);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error);
    }

}
