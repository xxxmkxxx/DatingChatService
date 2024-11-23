package com.dating.chat.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseData<T> {
    private boolean success;
    private String description;
    private T data;
}
