package com.enigma.livecodeloan.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_loan_type")
public class LoanType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String type;
    @Column(name = "max_loan")
    private Double maxLoan;
    @OneToMany(mappedBy = "loanType")
    private List<LoanTransaction> loanTransactions;
}
