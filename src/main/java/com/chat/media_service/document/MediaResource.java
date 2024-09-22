package com.chat.media_service.document;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaResource {
  @Id private String id;
  private String fileName;
  private Integer bytes;
  private String format;
  private String resourceType;
  private String url;
  private String secureUrl;
  private LocalDateTime createdAt;
  private Integer version;
  private String accessMode;
  private String operation;
}
