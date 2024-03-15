package com.enigma.livecodeloan.model.entity;

import com.enigma.livecodeloan.util.enums.ERole;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Enumerated(EnumType.STRING)
    private ERole role;
    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoles;
}
