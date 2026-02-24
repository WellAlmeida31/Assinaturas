package com.wellsoft.globo.assinaturas.application.port.output;

import com.wellsoft.globo.assinaturas.application.dto.CreateRecurrenceDto;

public interface RecurrenceOutput {
    void createRecurrence(CreateRecurrenceDto createRecurrenceDto);
}
