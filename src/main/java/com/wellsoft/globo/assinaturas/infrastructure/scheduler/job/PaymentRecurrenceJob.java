package com.wellsoft.globo.assinaturas.infrastructure.scheduler.job;

import com.wellsoft.globo.assinaturas.application.usecase.ExecutePaymentRecurrenceSignature;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@NoArgsConstructor
public class PaymentRecurrenceJob implements Job {

    @Autowired
    ExecutePaymentRecurrenceSignature executePaymentRecurrenceSignature;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Execute Payment Recurrence Job");

        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();

        String userIdentifier = dataMap.getString("userIdentifier");
        BigDecimal value = new BigDecimal(dataMap.getString("value"));

        executePaymentRecurrenceSignature.apply(userIdentifier, value);
    }

}
