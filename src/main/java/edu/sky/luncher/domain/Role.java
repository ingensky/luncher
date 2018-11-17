package edu.sky.luncher.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER, ROLE_ADMIN, ROLE_MANAGER
    ;

    @Override
    public String getAuthority() {
        return name();
    }
}
