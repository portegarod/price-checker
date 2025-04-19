package dev.pablo.pricechecker.infraestructure.in.web.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

  private final LocalDateTime timestamp = LocalDateTime.now();

  private String message;

  private String path;
}
