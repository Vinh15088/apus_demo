package com.apus.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    private String message;
    private String traceId;
    private T data;

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>("Thành công", java.util.UUID.randomUUID().toString(), data);
    }

    public static <T> BaseResponse<T> error(String message) {
        return new BaseResponse<>(message, java.util.UUID.randomUUID().toString(), null);
    }
} 