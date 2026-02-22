package com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "signature")
public class SignatureDbo {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "signature_seq"
    )
    @SequenceGenerator(
            name = "signature_seq",
            sequenceName = "signature_sequence",
            allocationSize = 1000
    )
    private Long id;

    @Column(name = "user_identifier", nullable = false)
    private String userIdentifier;

    @Column(name = "plan", nullable = false)
    @Enumerated(EnumType.STRING)
    private Plan plan;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SignatureStatus status;

    @OneToOne(mappedBy = "signatureDbo", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserDbo userDbo;

    @OneToMany(mappedBy = "signatureDbo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PaymentsDbo> payments = new ArrayList<>();

}
