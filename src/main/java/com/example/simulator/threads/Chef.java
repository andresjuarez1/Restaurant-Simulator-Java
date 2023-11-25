package com.example.simulator.threads;

import com.example.simulator.Restaurante;

public class Chef implements Runnable {
    private Restaurante restaurante;

    public Chef(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    @Override
    public void run() {
        while (true) {
            try {
                restaurante.cocinar();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
