package org.oasumainline.SpringStarter.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Component
public class WebCorsConfiguration implements CorsConfigurationSource {
    @Value("${app.cors.whitelist}")
    private List<String> whitelist;

    @Override public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(whitelist);
        cors.setAllowedMethods(List.of("GET", "POST"));
        cors.setAllowedHeaders(List.of("*"));
        return cors;

    }
}
