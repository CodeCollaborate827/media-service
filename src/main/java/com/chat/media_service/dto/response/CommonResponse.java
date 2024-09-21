package com.chat.media_service.dto.response;

import com.chat.media_service.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonResponse {
    private ErrorCode errorCode;
    private String message;
    private String requestId;
    private Object data;
}
