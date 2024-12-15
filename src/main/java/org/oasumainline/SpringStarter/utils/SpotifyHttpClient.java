package org.oasumainline.SpringStarter.utils;

import lombok.Getter;
import org.oasumainline.SpringStarter.exceptions.SpotifyAuthenticationException;
import org.oasumainline.SpringStarter.externals.SpotifyTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Component
public class SpotifyHttpClient {
    @Getter
    private final RestClient restClient;

    private String accessToken;

    SpotifyHttpClient(
            @Value("${app.spotify.base-url}")
            String baseUrl,
            @Value("${app.spotify.token-url}")
            String tokenUrl,
            @Value("#{'${app.spotify.authorization}'.trim()}")
            String authorization
    ) {
        this.restClient = RestClient.builder().baseUrl(baseUrl).requestInterceptor((request, body, execution) -> {
            request.getHeaders().set(HttpHeaders.AUTHORIZATION, "Bearer " + this.accessToken);
            var response = execution.execute(request, body);
            if (response.getStatusCode().isSameCodeAs(HttpStatus.UNAUTHORIZED)) {
                MultiValueMap<String, Object> authBody = new LinkedMultiValueMap<>();
                authBody.add("grant_type", "client_credentials");
                SpotifyTokenResponse spotifyTokenResponse = RestClient
                        .create(tokenUrl)
                        .post()
                        .header("Authorization", "Basic " + authorization)
                        .body(authBody)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .retrieve().body(SpotifyTokenResponse.class);
                if (spotifyTokenResponse == null || spotifyTokenResponse.accessToken() == null) {
                    throw new SpotifyAuthenticationException("Unable to authenticate against the Spotify API");
                }
                this.accessToken = spotifyTokenResponse.accessToken();
                request.getHeaders().set(HttpHeaders.AUTHORIZATION, "Bearer " + this.accessToken);
                return execution.execute(request, body);
            }
            return response;
        }).build();
    }
}
