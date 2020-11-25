package net.slipp.presentation.dto;

public class JoinResponse {

    private final String principal;

    public JoinResponse(String principal) {
        this.principal = principal;
    }

    public String getPrincipal() {
        return principal;
    }
}
