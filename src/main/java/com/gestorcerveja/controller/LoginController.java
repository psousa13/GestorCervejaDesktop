package com.gestorcerveja.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.gestorcerveja.model.Usuario;
import com.gestorcerveja.service.UsuarioService;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    private UsuarioService usuarioService = new UsuarioService();

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            Usuario user = usuarioService.login(username, password);
            messageLabel.setText("Login successful: " + user.getNome());
        } catch (Exception e) {
            messageLabel.setText("Invalid login");
        }
    }
}