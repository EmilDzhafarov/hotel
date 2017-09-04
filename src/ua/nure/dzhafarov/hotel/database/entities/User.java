package ua.nure.dzhafarov.hotel.database.entities;

import ua.nure.dzhafarov.hotel.database.enums.Role;

import java.time.LocalDateTime;

/**
 * User entities in database
 */
public class User extends AbstractEntity {

    private static final long serialVersionUID = -6889036256149495388L;

    private String firstName;

    private String lastName;

    private String login;

    private String password;

    private String email;

    private Role role;

    private LocalDateTime registrationTime;

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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


    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(LocalDateTime registrationTime) {
        this.registrationTime = registrationTime;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", registrationTime=" + registrationTime +
                ", role=" + role +
                '}';
    }
}
