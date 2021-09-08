package com.jmr.stream.demostream.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

/**
 * App Configuration
 */
@Configuration
@EnableRetry
//Register all configuration property classes
@EnableConfigurationProperties({DemoProperties.class, ClientsProperties.class})
@Slf4j
@AllArgsConstructor
public class AppConfig {
    /**
     * Delimiter for jpa
     * Security gateway blocks "," in urls
     */
    public static final String PROPERTY_DELIMITER = "-";

    /**
     * Model mapper
     *
     * @return model mapper
     */
    @Bean
    public ModelMapper modelMapper() {
        //Create a new model mapper object
        ModelMapper modelMapper = new ModelMapper();
        //Add condition for avoiding null values
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        return modelMapper;
    }


    // PRIVATE METHODS

}
