package com.example.simulator.threads;

import com.example.simulator.Restaurante;
import com.example.simulator.HelloController;
import javafx.application.Platform;


public class Chef implements Runnable {
    private Restaurante restaurante;
    private HelloController controller;
    private volatile boolean running = true;

    public Chef(Restaurante restaurante, HelloController controller) {
        this.restaurante = restaurante;
        this.controller = controller;
    }

    public void cocinar() throws InterruptedException {
        restaurante.lock.lock();
        try {
            // Si ya hay comida lista, espera a que se consuma antes de cocinar más
            while (restaurante.bufferDeComidaListo) {
                restaurante.bufferLleno.await();
            }
            Thread.sleep(8000);
            System.out.println("Chef ha cocinado la comida.");

            restaurante.bufferDeComidaListo = true;
            restaurante.bufferVacio.signal();
        } finally {
            restaurante.lock.unlock();
        }
    }

    @Override
    public void run() {
        while (running) {
            try {
                cocinar();
                // Actualizar la interfaz gráfica en el hilo de la aplicación de JavaFX
                Platform.runLater(() -> {
                    if (this.controller != null) {
                        this.controller.updateChefStatus("Cocinando");
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopRunning() {
        running = false;
    }
}

