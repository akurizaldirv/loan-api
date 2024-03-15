package com.enigma.livecodeloan.service;

import com.enigma.livecodeloan.model.entity.Role;
import com.enigma.livecodeloan.util.enums.ERole;

public interface RoleService {
    Role getOrSave(ERole role);
}
