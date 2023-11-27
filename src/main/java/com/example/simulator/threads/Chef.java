package com.example.simulator.threads;

import com.example.simulator.Restaurante;
import com.example.simulator.Orden;
import com.example.simulator.Comida;
import com.example.simulator.HelloController;
import javafx.application.Platform;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Chef implements Runnable {
    private Restaurante restaurante;
    private HelloController controller;
    public Lock lock2 = new ReentrantLock();
    private volatile boolean running = true;

    public Chef(Restaurante restaurante, HelloController controller) {
        this.restaurante = restaurante;
        this.controller = controller;
    }

    public void cocinar() throws InterruptedException {
        restaurante.lock.lock();
        try {
            while (restaurante.bufferOrdenes.isEmpty()) {
                restaurante.bufferVacio.await();
            }

            if (restaurante.bufferComidas.size() == 5) {
                restaurante.bufferVacio.signal();  // Agrega esta línea para notificar al mesero
            } else if (restaurante.bufferComidas.isEmpty()) {
                Thread.sleep(4000);

                for (int i = 0; i < Restaurante.TAMANO_BUFFER_COMIDA; i++) {
                    Orden orden = restaurante.bufferOrdenes.poll();
                    Comida comida = new Comida(orden);
                    restaurante.bufferComidas.offer(comida);
                }

                System.out.println("Chef ha cocinado la orden. Comida en el buffer: " + restaurante.bufferComidas.size());
            }
        } finally {
            restaurante.lock.unlock();
        }
    }


    @Override
    public void run() {
        while (running) {
            try {
                cocinar();
                Platform.runLater(() -> {
                    if (this.controller != null) {
                        this.controller.updateChefStatus("Cocinando");
                    }
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }


    public void stopRunning() {
        running = false;
    }
}