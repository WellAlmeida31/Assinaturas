package com.wellsoft.globo.assinaturas.application.port.output;

import com.wellsoft.globo.assinaturas.application.dto.CancelSignatureDto;

import java.time.LocalDateTime;

public interface CancelSignatureOutput {
    void sendCancelPaymentRecurrenceAndSignature(CancelSignatureDto cancelSignatureDto);
}
