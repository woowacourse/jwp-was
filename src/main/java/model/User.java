package model;

import java.util.Objects;

public final class User {
    private String id;
    private String password;
    private String name;
    private String email;

    public User(String id, String password, String name, String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return String.format("User(%s, %s, %s, %s)", this.id, this.password, this.name, this.email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        final User rhs = (User) o;
        return this.id.equals(rhs.id) &&
                this.password.equals(rhs.password) &&
                this.name.equals(rhs.name) &&
                this.email.equals(rhs.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.password, this.name, this.email);
    }
}