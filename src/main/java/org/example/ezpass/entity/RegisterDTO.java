package org.example.ezpass.entity;

public record RegisterDTO(String login, String password, UserRole role) {
}