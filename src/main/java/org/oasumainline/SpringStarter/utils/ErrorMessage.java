package org.oasumainline.SpringStarter.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorMessage {
    private final String message;
    private final int code;
}
