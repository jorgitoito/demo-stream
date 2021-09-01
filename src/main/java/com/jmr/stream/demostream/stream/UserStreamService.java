package com.jmr.stream.demostream.stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmr.stream.demostream.model.entity.EventEntity;
import com.jmr.stream.demostream.model.entity.UserEntity;
import com.jmr.stream.demostream.service.EvenService;
import com.jmr.stream.demostream.stream.dto.LongIdMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.stereotype.Service;

import static org.springframework.cloud.stream.messaging.Sink.INPUT;

/**
 * ALM stream service
 * With default constructors
 */
@Slf4j
@Service
@EnableBinding({Processor.class})
@AllArgsConstructor
public class UserStreamService {

    /**
     * log events in User. create
     */
    private static final String CREATE_USER = "headers['type']=='CREATE_USER'";
    /**
     * log events in User. delete
     */
    private static final String DELETE_USER = "headers['type']=='DELETE_USER'";
    /**
     * log events in User. SEND_EMAIL
     */
    private static final String SEND_EMAIL = "headers['type']=='SEND_EMAIL'";

    private EvenService evenService;

    @StreamListener(target = INPUT, condition = CREATE_USER)
    public void createUser(final LongIdMessage message) {

        log.info("createUser: message [{}] ", message);

        if (message.getObjectJson() != null) {
            UserEntity userEn = this.getUserEntityfromJson(message.getObjectJson());
            log.info("createUser: userEn [{}] ", userEn);
            EventEntity event = new EventEntity();
            event.setType("CREATE_USER");
            if (userEn != null) {
                event.setComment(userEn.toString());
            }
            evenService.recordEvent(event);
        }

    }

    @StreamListener(target = INPUT, condition = DELETE_USER)
    public void deleteUser(final LongIdMessage message) {

        log.info("deleteUser: message [{}] ", message);
        EventEntity event = new EventEntity();
        event.setType("DELETE_USER");
        if (message.getIdS() != null) {
            event.setComment(message.getIdS());
        }
        evenService.recordEvent(event);
    }

    @StreamListener(target = INPUT, condition = SEND_EMAIL)
    public void sendNotification(final LongIdMessage message) {

        log.info("Begin:sendNotification: message [{}] ", message);
        log.info(" ---------------- ");
        log.info("End: sendNotification: message [{}] ", message);

    }


    ///
    private UserEntity getUserEntityfromJson(String json) {
        UserEntity userEn = null;
        try {
            userEn = new ObjectMapper().readValue(json, UserEntity.class);
        } catch (JsonProcessingException e) {
            log.error("createUser: error [{}] ", e.getMessage());
        }
        return userEn;
    }
}
