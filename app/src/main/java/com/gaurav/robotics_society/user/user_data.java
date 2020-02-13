package com.gaurav.robotics_society.user;

public class user_data {

    public String name;
    public String email;
    public String role;
    public String roll;
    public String token;

    public user_data(String name, String email, String role, String roll, String token) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.roll = roll;
        this.token = token;
    }

    public user_data() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
