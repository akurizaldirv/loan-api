package com.enigma.livecodeloan.entity;

import com.enigma.livecodeloan.util.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trx_loan_detail")
public class LoanTransactionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "transaction_date")
    private Long transactionDate;
    @Column(nullable = false, columnDefinition = "bigint check (nominal > 0)")
    private Double nominal;
    @ManyToOne
    @JoinColumn(name = "trx_loan_id")
    private LoanTransaction loanTransaction;
    @Column(name = "loan_status")
    private LoanStatus loanStatus; // enum
    @Column(name = "created_at")
    private Long createdAt;
    @Column(name = "updated_at")
    private Long updatedAt;
}
