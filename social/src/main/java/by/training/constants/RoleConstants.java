package by.training.constants;

public enum RoleConstants {

    ROLE_USER("USER");

    private String role;

    private RoleConstants(final String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }

}
