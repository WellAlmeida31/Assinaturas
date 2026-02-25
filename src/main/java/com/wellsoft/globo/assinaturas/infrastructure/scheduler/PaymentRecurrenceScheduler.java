package com.wellsoft.globo.assinaturas.infrastructure.scheduler;

import com.wellsoft.globo.assinaturas.application.dto.CreateRecurrenceDto;
import com.wellsoft.globo.assinaturas.infrastructure.scheduler.job.PaymentRecurrenceJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentRecurrenceScheduler {

    private final Scheduler scheduler;

    public void schedulePaymentRecurrence(CreateRecurrenceDto createRecurrenceDto) {
        try {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("userIdentifier", createRecurrenceDto.getUserIdentifier());
            jobDataMap.put("value", createRecurrenceDto.getValue().toPlainString());

            JobDetail jobDetail = JobBuilder.newJob(PaymentRecurrenceJob.class)
                    .withIdentity("payment-recurrence-job-" + createRecurrenceDto.getUserIdentifier())
                    .usingJobData(jobDataMap)
                    .storeDurably()
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .withIdentity("payment-recurrence-trigger-" + createRecurrenceDto.getUserIdentifier())
                    .startAt(Date.from(createRecurrenceDto.getRecurrenceDate()
                            .atZone(ZoneId.systemDefault())
                            .toInstant()))
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);

            log.info("The recurring payment has been scheduled for the user: {}", createRecurrenceDto.getUserIdentifier());

        } catch (SchedulerException e) {
            throw new RuntimeException("Erro ao agendar pagamento", e);
        }
    }
}
