package com.example.simulator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.application.Platform;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class HelloController {
    @FXML
    private Label chefLabel;

    @FXML
    private Label meseroLabel;

    @FXML
    private Label comensalLabel;

    @FXML
    private Label recepcionistaLabel;

    @FXML
    private TextArea consoleTextArea;

    public void updateConsole(String message) {
        Platform.runLater(() -> {
            consoleTextArea.appendText(message + "\n");

        });
    }

    public void updateChefStatus(String status) {
        Platform.runLater(() -> chefLabel.setText("Chef: " + status));
    }

    public void updateMeseroStatus(String status) {
        Platform.runLater(() -> meseroLabel.setText("Mesero: " + status));
    }

    public void updateComensalStatus(String status) {
        Platform.runLater(() -> comensalLabel.setText("Comensal: " + status));
    }
    public void updateRecepcionistaStatus(String status) {
        Platform.runLater(() -> recepcionistaLabel.setText("Recepcionista: " + status));
    }

    public void redirectSystemOutput() {
        OutputStream out = new OutputStream() {
            private StringBuilder buffer = new StringBuilder();

            @Override
            public void write(int b) throws IOException {
                if (b == '\n') {
                    updateConsole(buffer.toString());
                    buffer.setLength(0); // Limpia el buffer después de cada nueva línea
                } else {
                    buffer.append((char) b);
                }
            }
        };
        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
    }

}

