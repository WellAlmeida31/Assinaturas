package com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "app_user",
        indexes = {
                @Index(name = "idx_user_identifier", columnList = "user_identifier", unique = true),
                @Index(name = "idx_cpf", columnList = "cpf", unique = true)
        })
public class UserDbo {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "app_user_seq"
    )
    @SequenceGenerator(
            name = "app_user_seq",
            sequenceName = "app_user_sequence",
            allocationSize = 1000
    )
    private Long id;

    @EqualsAndHashCode.Include
    @Column(name = "user_identifier", nullable = false)
    private String userIdentifier;

    @Column(name = "name", nullable = false)
    private String name;

    @EqualsAndHashCode.Include
    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @Column(name = "address_number")
    private String addressNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "province")
    private String province;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "client_customer_id")
    private String clientCustomerId;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Setter
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_card_id", referencedColumnName = "id")
    private CreditCardDbo creditCardDbo;

    @Setter
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "signature_id", referencedColumnName = "id")
    private SignatureDbo signatureDbo;

}
