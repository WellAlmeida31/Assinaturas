package com.wellsoft.globo.assinaturas.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelSignatureDto {
    private String userIdentifier;
    LocalDateTime expirationDate;
}
