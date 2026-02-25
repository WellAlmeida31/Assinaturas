package com.wellsoft.globo.assinaturas.infrastructure.scheduler.job;

import com.wellsoft.globo.assinaturas.application.usecase.ExecuteSubscriptionSuspension;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@NoArgsConstructor
public class CancelSignatureJob implements Job {

    @Autowired
    ExecuteSubscriptionSuspension executeSubscriptionSuspension;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Execute Cancel Signature Job");

        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();

        String userIdentifier = dataMap.getString("userIdentifier");

        executeSubscriptionSuspension.apply(userIdentifier);
    }
}
