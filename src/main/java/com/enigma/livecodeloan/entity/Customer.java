package com.enigma.livecodeloan.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.User;

import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mst_customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;
    @Column(nullable = false, unique = true)
    private String phone;
    private String status;
    @OneToOne
    @JoinColumn(name = "user_id")
    private AppUser user;
    @OneToMany(mappedBy = "customer")
    private List<LoanTransaction> loanTransactions;
}
