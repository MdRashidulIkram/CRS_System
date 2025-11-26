/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crs.models;

/**
 *
 * @author Bismi
 */
public class User {
    private String username;
    private String passwordHash;
    private UserRole role;
    private boolean active;
    private String lastLoginBinary;
    private String lastLogoutBinary;

    public User(String username, String passwordHash, UserRole role, boolean active) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.active = active;
    }

    // Getters & Setters...
}
