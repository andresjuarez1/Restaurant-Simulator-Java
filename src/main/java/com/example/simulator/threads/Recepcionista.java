package com.example.simulator.threads;

import com.example.simulator.HelloController;
import com.example.simulator.Restaurante;

public class Recepcionista implements Runnable {
    private Restaurante restaurante;
    private HelloController controller;

    public Recepcionista(Restaurante restaurante, HelloController controller) {
        this.restaurante = restaurante;
        this.controller = controller;
    }

    public void entrarComensal() throws InterruptedException {
        restaurante.lock.lock();
        restaurante.recepcionistaLock.lock();
        try {
            while (restaurante.comensalesEnRestaurante >= Restaurante.CAPACIDAD_MAXIMA ||
                    restaurante.mesasOcupadas >= Restaurante.CAPACIDAD_MAXIMA) {
                System.out.println("Restaurante lleno. Comensal esperando afuera.");
                restaurante.recepcionistaCondition.await();
            }

            restaurante.comensalesEnRestaurante++;
            restaurante.mesasOcupadas++;
            System.out.println("Comensal entra al restaurante. Comensales en el restaurante: " + restaurante.comensalesEnRestaurante +
                    ". Mesas ocupadas: " + restaurante.mesasOcupadas);

            //if (restaurante.comensalesEnRestaurante < Restaurante.CAPACIDAD_MAXIMA) {
            if (restaurante.comensalesEnRestaurante + 1 < Restaurante.CAPACIDAD_MAXIMA && restaurante.mesasOcupadas + 1 < Restaurante.CAPACIDAD_MAXIMA){
                restaurante.bufferDeComidaListo = true;
                restaurante.bufferVacio.signal();
            }
        } finally {
            restaurante.lock.unlock();
            restaurante.recepcionistaLock.unlock();
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



