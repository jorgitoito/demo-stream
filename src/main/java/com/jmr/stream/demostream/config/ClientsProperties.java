package com.jmr.stream.demostream.config;


import com.jmr.stream.demostream.config.dto.TrackerClientProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ConfigurationProperties(prefix = com.jmr.stream.demostream.config.ClientsProperties.PREFIX)
public class ClientsProperties {

    /**
     * Properties prefix
     */
    public static final String PREFIX = "demostream.clients";

    @NotNull
    private TrackerClientProperties tracker;
}
