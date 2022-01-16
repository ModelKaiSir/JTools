package com.karys.jtools.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "jTools.config.project")
@Data
public class AppConfig {

    private List<String > items;
    private List<String > packages;
}
