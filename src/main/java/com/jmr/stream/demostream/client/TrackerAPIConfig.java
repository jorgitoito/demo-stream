package com.jmr.stream.demostream.client;


import com.jmr.stream.demostream.config.ClientsProperties;
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
     * Clients Properties: tracker url
     */
    private ClientsProperties properties;

    /**
     * TrackerAPI bean
     *
     * @return TrackerAPI bean
     */
    @Bean
    public TrackerAPI engineAPI() {
        log.info("TrackerAPI url: [{}]", properties.getTracker().getUrl());
        //Builder
        TrackerAPI client =
                WebReactiveFeign  //WebClient based reactive feign
                        //JettyReactiveFeign //Jetty http client based
                        //Java11ReactiveFeign //Java 11 http client based
                        .<TrackerAPI>builder()
                        .target(TrackerAPI.class, properties.getTracker().getUrl());

        log.info("TrackerAPI DONE");
        return client;
    }
}
