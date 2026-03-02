package com.wellsoft.globo.assinaturas.infrastructure.client.response;

import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreatePaymentResponse {
    public String object;
    public String id;
    public String dateCreated;
    public String customer;
    public BigDecimal value;
    public BigDecimal netValue;
    public String billingType;
    public LocalDate confirmedDate;
    public Object creditCard;
    public PaymentStatus status;
    public String dueDate;
    public String originalDueDate;
    public LocalDate paymentDate;
    public String invoiceUrl;
    public String invoiceNumber;
    public String externalReference;
    public boolean deleted;
    public boolean anticipated;
    public boolean anticipable;
    public LocalDate creditDate;
    public LocalDate estimatedCreditDate;
    public boolean postalService;
}
