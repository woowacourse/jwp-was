package model;

public class User {

    private String userId;
    private String password;
    private String name;
    private String email;

    public User() {
    }

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public static class Builder {

        User user = new User();

        public Builder() {
        }

        public Builder userId(String userId) {
            this.user.userId = userId;
            return this;
        }

        public Builder password(String password) {
            this.user.password = password;
            return this;
        }

        public Builder name(String name) {
            this.user.name = name;
            return this;
        }

        public Builder email(String email) {
            this.user.email = email;
            return this;
        }

        public User build() {
            return user;
        }
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email="
            + email + "]";
    }
}
