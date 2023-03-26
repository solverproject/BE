package com.solver.solver_be.domain.user.entity;

public enum UserRoleEnum {
    GUEST(Authority.GUEST),
    ADMIN(Authority.ADMIN);

    private final String authority;

    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String GUEST = "ROLE_GUEST";
        public static final String ADMIN = "ROLE_ADMIN";
    }

}