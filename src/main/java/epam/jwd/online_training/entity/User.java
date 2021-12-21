package epam.jwd.online_training.entity;

import java.util.Objects;

public class User implements BaseEntity {

    private static final long serialVersionUID = 8921669293185159841L;
    private Integer id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private UserRole role;
    private boolean statusIsDeleted;

    public User() {
    }

    public User(Integer id, String email, String password, String firstName, String lastName, UserRole role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean getStatus() { return statusIsDeleted; }

    public void setStatus(boolean statusIsDeleted) { this.statusIsDeleted = statusIsDeleted; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id)
                && Objects.equals(email, user.email)
                && Objects.equals(password, user.password)
                && Objects.equals(firstName, user.firstName)
                && Objects.equals(lastName, user.lastName)
                && role == user.role
                && statusIsDeleted == user.statusIsDeleted;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, firstName, lastName, role, statusIsDeleted);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", statusIsDeleted=" + statusIsDeleted +
                '}';
    }
}
