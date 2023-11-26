package com.example.simulator.threads;

import com.example.simulator.Restaurante;
import com.example.simulator.Orden;
import com.example.simulator.Comida;
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
            // Espera hasta que haya una orden en el buffer de órdenes
            while (restaurante.bufferOrdenes.isEmpty()) {
                restaurante.bufferVacio.await();
            }

            // Simular tiempo de cocina
            Thread.sleep(4000);

            for (int i = 0; i < Restaurante.TAMANO_BUFFER_COMIDA; i++) {
                Orden orden = restaurante.bufferOrdenes.poll();
                Comida comida = new Comida(orden);
                restaurante.bufferComidas.offer(comida);
            }

            System.out.println("Chef ha cocinado la orden. Comida en el buffer: " + restaurante.bufferComidas.size());

            restaurante.bufferLleno.signal();
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
                Thread.currentThread().interrupt(); // Restaura la bandera interrupted
                e.printStackTrace();
            }
        }
    }


    public void stopRunning() {
        running = false;
    }
}

