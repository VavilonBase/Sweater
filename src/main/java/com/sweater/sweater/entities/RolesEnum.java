package com.sweater.sweater.entities;

import org.springframework.security.core.GrantedAuthority;

public enum RolesEnum implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
