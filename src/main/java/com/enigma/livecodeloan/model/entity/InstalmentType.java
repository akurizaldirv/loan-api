package com.enigma.livecodeloan.model.entity;

import com.enigma.livecodeloan.util.enums.EInstalmentType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_instalment_type")
public class InstalmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Enumerated(EnumType.STRING)
    private EInstalmentType instalmentType;
    @OneToMany(mappedBy = "instalmentType")
    private List<LoanTransaction> loanTransactions;
}
