package com.example.simulator.threads;

import com.example.simulator.HelloController;
import com.example.simulator.Restaurante;

public class Mesero implements Runnable {
    public Restaurante restaurante;
    private HelloController controller;

    public Mesero(Restaurante restaurante, HelloController controller) {
        this.restaurante = restaurante;
        this.controller = controller;
    }

    public void verificarOrdenLista() throws InterruptedException {
        restaurante.verificarOrdenLista();
    }

    public void servirComida() throws InterruptedException {
        restaurante.lock.lock();
        try {
            // Esperar a que haya comida lista y al menos un comensal
            while (!restaurante.bufferDeComidaListo || restaurante.comensalesEnRestaurante <= 0) {
                if (restaurante.comensalesEnRestaurante == 0) {
                    // Si no hay comensales, los meseros descansan
                    System.out.println("Mesero descansando...");
                    restaurante.bufferVacio.await();
                } else {
                    restaurante.bufferVacio.await();
                }
            }

            // Simular tiempo de llevar la comida al comensal
            Thread.sleep(2000);

            System.out.println("Mesero lleva la comida al comensal.");

            // Comensal come
            Thread.sleep(3000);

            System.out.println("Comensal ha terminado de comer.");

            // Liberar espacio en el restaurante
            restaurante.comensalesEnRestaurante--;
            restaurante.mesasOcupadas--;

            System.out.println("Comensal sale del restaurante. Comensales en el restaurante: " +
                    restaurante.comensalesEnRestaurante + ". Mesas disponibles en el restaurante: " +
                    restaurante.mesasOcupadas);

            // Notificar al chef que hay espacio para cocinar más
            restaurante.bufferDeComidaListo = false;
            restaurante.bufferLleno.signal();
        } finally {
            restaurante.lock.unlock();
        }
    }


    @Override
    public void run() {
        while (true) {
            try {
                servirComida();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

