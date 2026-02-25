package com.wellsoft.globo.assinaturas.domain.service;

import com.wellsoft.globo.assinaturas.application.dto.CancelSignatureDto;
import com.wellsoft.globo.assinaturas.application.dto.CreateRecurrenceDto;
import com.wellsoft.globo.assinaturas.application.port.output.CancelSignatureOutput;
import com.wellsoft.globo.assinaturas.application.port.output.RecurrenceOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecurrenceService {

    private final RecurrenceOutput recurrenceOutput;
    private final CancelSignatureOutput cancelSignatureOutput;

    public void sendCreatePaymentRecurrence(CreateRecurrenceDto createRecurrenceDto){
        recurrenceOutput.createRecurrence(createRecurrenceDto);
    }

    public void sendCancelPaymentRecurrenceAndSignature(CancelSignatureDto cancelSignatureDto){
        cancelSignatureOutput.sendCancelPaymentRecurrenceAndSignature(cancelSignatureDto);
    }
}
