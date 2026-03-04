package com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "credit_card")
public class CreditCardDbo {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "credit_card_seq"
    )
    @SequenceGenerator(
            name = "credit_card_seq",
            sequenceName = "credit_card_sequence",
            allocationSize = 1000
    )
    private Long id;

    @Column(name = "holder_name", nullable = false)
    private String holderName;

    @EqualsAndHashCode.Include
    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "expiry_month", nullable = false)
    private String expiryMonth;

    @Column(name = "expiry_year", nullable = false)
    private String expiryYear;

    @Column(name = "ccv", nullable = false)
    private String ccv;

    @EqualsAndHashCode.Include
    @Column(name = "taxId", nullable = false)
    private String taxId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @Column(name = "address_holder_number", nullable = false)
    private String addressHolderNumber;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Setter
    @Column(name = "credit_card_token")
    private String creditCardToken;

    @Setter
    @Column(name = "credit_card_brand")
    private String creditCardBrand;

    @Setter
    @JsonBackReference
    @OneToOne(mappedBy = "creditCardDbo", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserDbo userDbo;
}
