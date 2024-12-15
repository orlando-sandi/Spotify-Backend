package org.oasumainline.SpringStarter.externals;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SpotifyTokenResponse(String accessToken, String tokenType, Long expiresIn) {

}
