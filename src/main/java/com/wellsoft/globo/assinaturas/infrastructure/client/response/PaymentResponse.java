package com.wellsoft.globo.assinaturas.infrastructure.client.response;

import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.BillingType;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentResponse {
    public String object;
    public String id;
    public String dateCreated;
    public String customer;
    public BigDecimal value;
    public BigDecimal netValue;
    public Object originalValue;
    public BillingType billingType;
    public String confirmedDate;
    public CreditCardResponse creditCard;
    public PaymentStatus status;
    public String dueDate;
    public String originalDueDate;
    public Object paymentDate;
    public String clientPaymentDate;
    public String invoiceUrl;
    public String invoiceNumber;
    public Object externalReference;
    public boolean deleted;
    public boolean anticipated;
    public boolean anticipable;
    public String creditDate;
    public String estimatedCreditDate;
    public String transactionReceiptUrl;
    public LocalDate lastInvoiceViewedDate;
}
