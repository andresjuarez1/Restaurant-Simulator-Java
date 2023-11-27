package com.example.simulator.threads;

import com.example.simulator.HelloController;
import com.example.simulator.Restaurante;
import com.example.simulator.Comida;

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
            while (restaurante.comensalesEnRestaurante <= 0) {
                System.out.println("Mesero descansando...");
                restaurante.bufferVacio.await();
            }

            if (!restaurante.bufferComidas.isEmpty()) {
                Thread.sleep(2000);

                Comida comida = restaurante.bufferComidas.poll();
                System.out.println("Mesero lleva la comida al comensal. Comida en el buffer: " + restaurante.bufferComidas.size());

                Thread.sleep(3000);
                System.out.println("Comensal ha terminado de comer.");

                restaurante.mesasOcupadas--;
                restaurante.comensalesEnRestaurante--;

                System.out.println("Comensal sale del restaurante. Comensales en el restaurante: " +
                        restaurante.comensalesEnRestaurante + ". Mesas ocupadas en el restaurante: " +
                        restaurante.mesasOcupadas);

                restaurante.bufferVacio.signal();
            }
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
