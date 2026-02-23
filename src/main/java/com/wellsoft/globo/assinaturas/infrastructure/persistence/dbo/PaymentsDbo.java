package com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "payments")
public class PaymentsDbo {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "payments_seq"
    )
    @SequenceGenerator(
            name = "payments_seq",
            sequenceName = "payments_sequence",
            allocationSize = 1000
    )
    private Long id;

    @Column(name = "billing_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private BillingType billingType;

    @EqualsAndHashCode.Include
    @Column(name = "client_customer_id")
    private String clientCustomerId;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @EqualsAndHashCode.Include
    @Column(name = "payment_id", nullable = false)
    private String paymentId;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "confirmed_date")
    private LocalDateTime confirmedDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "retry_payment")
    private Integer retryPayment;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "transaction_receipt_url")
    private String transactionReceiptUrl;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "signature_id", nullable = false)
    private SignatureDbo signatureDbo;

}
