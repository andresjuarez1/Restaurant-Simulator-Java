package com.example.simulator.threads;

import com.example.simulator.Restaurante;

public class Comensal implements Runnable {
    private Restaurante restaurante;

    public Comensal(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    @Override
    public void run() {
        while (true) {
            try {
                restaurante.entrarComensal();
                // Simular tiempo dentro del restaurante
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}