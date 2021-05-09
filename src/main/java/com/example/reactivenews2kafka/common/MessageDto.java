package com.example.reactivenews2kafka.common;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MessageDto {
    @JsonRawValue
    private String payload;
}
