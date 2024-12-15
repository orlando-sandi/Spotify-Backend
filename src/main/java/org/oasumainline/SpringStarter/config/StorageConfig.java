package org.oasumainline.SpringStarter.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties("storage")
@Getter
public class StorageConfig {

    private String rootDir = "uploads";
    private String[] permittedExtensions = {"jpg", "jpeg", "png"};
}
