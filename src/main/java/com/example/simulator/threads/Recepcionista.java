package com.example.simulator.threads;

import com.example.simulator.threads.Comensal;
import com.example.simulator.HelloController;
import com.example.simulator.Orden;
import com.example.simulator.Restaurante;

public class Recepcionista implements Runnable {
    private Restaurante restaurante;
    private HelloController controller;
    private int sum = 0;

    public Recepcionista(Restaurante restaurante, HelloController controller) {
        this.restaurante = restaurante;
        this.controller = controller;
    }

    public void entrarComensal() throws InterruptedException {
        Orden orden = new Orden(restaurante.comensalesEnRestaurante);

        // Agregar la orden al buffer de órdenes
        synchronized (restaurante) {
            restaurante.bufferOrdenes.offer(orden);
        }

        // Agregar el comensal a la cola de espera
        synchronized (restaurante) {
            restaurante.colaEspera.offer(new Comensal(restaurante, controller, this));
        }

        synchronized (restaurante) {
            while (restaurante.comensalesEnRestaurante >= Restaurante.CAPACIDAD_MAXIMA ||
                    restaurante.mesasOcupadas >= Restaurante.CAPACIDAD_MAXIMA) {
                System.out.println("Restaurante lleno. Comensal esperando afuera.");
                restaurante.wait();
            }

            restaurante.comensalesEnRestaurante++;
            sum = sum + 1;
            restaurante.mesasOcupadas++;
            controller.updateComensalStatus("COMENSAL " + sum);
            System.out.println("Comensal entra al restaurante. Comensales en el restaurante: " + restaurante.comensalesEnRestaurante +
                    ". Mesas ocupadas: " + restaurante.mesasOcupadas);

            if (restaurante.comensalesEnRestaurante + 1 < Restaurante.CAPACIDAD_MAXIMA &&
                    restaurante.mesasOcupadas + 1 < Restaurante.CAPACIDAD_MAXIMA) {
                restaurante.bufferDeComidaListo = true;
                restaurante.notify();
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Simular tiempo de espera del recepcionista
                Thread.sleep(1000);
                System.out.println("Recepcionista desbloquea la entrada.");
                entrarComensal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
