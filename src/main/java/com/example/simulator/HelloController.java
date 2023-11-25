package com.example.simulator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label chefLabel;

    @FXML
    private Label meseroLabel;

    @FXML
    private Label comensalLabel;

    @FXML
    private Label recepcionistaLabel;

    // Métodos para actualizar la interfaz gráfica
    public void updateChefStatus(String status) {
        chefLabel.setText("Chef: " + status);
    }

    public void updateMeseroStatus(String status) {
        meseroLabel.setText("Mesero: " + status);
    }

    public void updateComensalStatus(String status) {
        comensalLabel.setText("Comensal: " + status);
    }

    public void updateRecepcionistaStatus(String status) {
        recepcionistaLabel.setText("Recepcionista: " + status);
    }

    // Otros métodos del controlador
    // ...
}
