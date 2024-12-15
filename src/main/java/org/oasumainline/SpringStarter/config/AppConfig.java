package org.oasumainline.SpringStarter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
@Configuration
@PropertySource(value = "classpath:secrets.properties")
public class AppConfig {
}
