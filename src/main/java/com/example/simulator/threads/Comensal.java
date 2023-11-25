package com.example.simulator.threads;

import com.example.simulator.HelloController;
import com.example.simulator.Restaurante;
import javafx.application.Platform;

public class Comensal implements Runnable {
    private Restaurante restaurante;
    private Recepcionista recepcionista;
    private HelloController controller;

    public Comensal(Restaurante restaurante, HelloController controller) {
        this.restaurante = restaurante;
        this.controller = controller;
    }

    @Override
    public void run() {
        while (true) {
            try {
                recepcionista.entrarComensal();
                if (controller != null) {
                    Platform.runLater(() -> controller.updateMeseroStatus("Llamando al mesero"));
                }
                // Simular tiempo dentro del restaurante
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
