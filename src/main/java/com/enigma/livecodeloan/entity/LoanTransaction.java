package com.enigma.livecodeloan.entity;

import com.enigma.livecodeloan.util.enums.ApprovalStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trx_loan")
public class LoanTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "loan_type_id")
    private LoanType loanType;
    @ManyToOne
    @JoinColumn(name = "installment_type_id")
    private InstalmentType instalmentType;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @Column(columnDefinition = "bigint check (nominal > 0)")
    private Double nominal;
    private Long approvedAt;
    private String approvedBy;
    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus; // enum
    @OneToMany(mappedBy = "loanTransaction", cascade = CascadeType.ALL)
    private List<LoanTransactionDetail> loanTransactionDetails;
    private Long createdAt;
    private Long updatedAt;
}
