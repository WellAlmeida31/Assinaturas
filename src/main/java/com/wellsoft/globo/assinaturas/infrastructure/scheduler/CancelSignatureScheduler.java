package com.wellsoft.globo.assinaturas.infrastructure.scheduler;

import com.wellsoft.globo.assinaturas.application.dto.CancelSignatureDto;
import com.wellsoft.globo.assinaturas.infrastructure.scheduler.job.CancelSignatureJob;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class CancelSignatureScheduler {

    private final Scheduler scheduler;

    public void cancelSignatureAndRecurrence(CancelSignatureDto cancelSignatureDto){
        var identifier = cancelSignatureDto.getUserIdentifier();
        analyzeRecurrenceJobAndCancelIfExist(identifier);
        try {

            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("userIdentifier", identifier);

            JobDetail jobDetail = JobBuilder.newJob(CancelSignatureJob.class)
                    .withIdentity("cancel-signature-job-" + identifier)
                    .usingJobData(jobDataMap)
                    .storeDurably()
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .withIdentity("cancel-signature-trigger-" + identifier)
                    .startAt(Date.from(cancelSignatureDto.getExpirationDate()
                            .atZone(ZoneId.systemDefault())
                            .toInstant()))
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);

            log.info("The recurring payment has been cancelled and the service expiration has been scheduled. User: {}", identifier);

        } catch (SchedulerException e) {
            throw new RuntimeException("Erro ao cancelar assinatura", e);
        }
    }

    @SneakyThrows
    private void analyzeRecurrenceJobAndCancelIfExist(String userIdentifier){
        log.info("Analyzing if there are any recurring jobs to be deleted");
        JobKey jobKey = JobKey.jobKey("payment-recurrence-job-" + userIdentifier);

        if (scheduler.checkExists(jobKey)) {
            log.info("Deleted Job for new Recurrence Scheduler");
            scheduler.deleteJob(jobKey);
        }
    }
}
