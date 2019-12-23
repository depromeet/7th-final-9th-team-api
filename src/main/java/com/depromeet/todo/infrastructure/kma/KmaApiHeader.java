package com.depromeet.todo.infrastructure.kma;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class KmaApiHeader {
    private String resultCode;
    @JsonProperty("resultMsg")
    private String resultMessage;

    public static KmaApiHeader success() {
        return new KmaApiHeader("0000", "OK");
    }
}
