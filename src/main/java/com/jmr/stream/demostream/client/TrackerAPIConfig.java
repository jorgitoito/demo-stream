package com.jmr.stream.demostream.client;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactivefeign.webclient.WebReactiveFeign;


/**
 * Tracker API Configuration
 */
@Configuration
@Slf4j
@AllArgsConstructor
public class TrackerAPIConfig {

    /**
     * TrackerAPI bean
     *
     * @return TrackerAPI bean
     */
    @Bean
    public TrackerAPI engineAPI() {
        //Builder
        TrackerAPI client =
                WebReactiveFeign  //WebClient based reactive feign
                        //JettyReactiveFeign //Jetty http client based
                        //Java11ReactiveFeign //Java 11 http client based
                        .<TrackerAPI>builder()
                        .target(TrackerAPI.class, "http://localhost:35007/tracker");

        // todo: port and context to properties
        log.info("TrackerAPI DONE");
        return client;
    }
}
