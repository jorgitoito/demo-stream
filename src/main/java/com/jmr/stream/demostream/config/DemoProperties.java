package com.jmr.stream.demostream.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * Demo properties
 */
@Getter
@Setter
@ConfigurationProperties(prefix = com.jmr.stream.demostream.config.DemoProperties.PREFIX)
public class DemoProperties {

    /**
     * Properties prefix
     */
    public static final String PREFIX = "demostream";

    /**
     * Workflow
     */
    private Map<String, List<String>> workflow;
}
