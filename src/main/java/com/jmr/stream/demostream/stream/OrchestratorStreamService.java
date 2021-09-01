package com.jmr.stream.demostream.stream;


import com.jmr.stream.demostream.config.DemoProperties;
import com.jmr.stream.demostream.util.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.cloud.stream.messaging.Sink.INPUT;

/**
 * Orchestrator stream service
 * With default constructors
 */
@Slf4j
@Service
@EnableBinding({Processor.class})
@AllArgsConstructor
public class OrchestratorStreamService {
    /**
     * Finished condition
     */
    private static final String FINISHED_CONDITION = "headers['finished']==true";
    /**
     * Processor
     */
    private final Processor appProcessor;
    /**
     * OnBoardingProperties
     */
    private final DemoProperties properties;

    /**
     * Listen to output messages
     *
     * @param payload object that has finished its workflow chain
     * @param type    type
     */
    @StreamListener(target = INPUT, condition = FINISHED_CONDITION)
    public void stateFinished(@Payload Object payload, @Header(MessageUtil.TYPE_HEADER) String type) {
        log.info("Type {}, payload {}", type, payload);
        //Check the nest state depending on configuration properties
        List<String> nextStates = this.properties.getWorkflow().get(type);
        //If there are next states defined
        if (nextStates != null) {
            //Send messages to start next states
            for (String nextState : nextStates) {
                //Send message for each state
                this.appProcessor.output().send(MessageUtil.message(payload, nextState));
            }
        }
    }

}
