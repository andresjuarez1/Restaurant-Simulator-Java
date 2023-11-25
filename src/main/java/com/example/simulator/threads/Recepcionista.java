package com.example.simulator.threads;

import com.example.simulator.Restaurante;

public class Recepcionista implements Runnable {
    private Restaurante restaurante;

    public Recepcionista(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Simular tiempo de espera del recepcionista
                Thread.sleep(3000);
                System.out.println("Recepcionista desbloquea la entrada.");
                restaurante.entrarComensal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


