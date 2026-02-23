package com.wellsoft.globo.assinaturas.infrastructure.client.response;

import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreatePaymentResponse {
    public String object;
    public String id;
    public String dateCreated;
    public String customer;
    public BigDecimal value;
    public BigDecimal netValue;
    public String billingType;
    public Object confirmedDate;
    public Object creditCard;
    public PaymentStatus status;
    public String dueDate;
    public String originalDueDate;
    public Object paymentDate;
    public String invoiceUrl;
    public String invoiceNumber;
    public Object externalReference;
    public boolean deleted;
    public boolean anticipated;
    public boolean anticipable;
    public Object creditDate;
    public Object estimatedCreditDate;
    public boolean postalService;
}
