package com.example.simulator.threads;

import com.example.simulator.Restaurante;

public class Mesero implements Runnable {
    public Restaurante restaurante;

    public Mesero(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    @Override
    public void run() {
        while (true) {
            try {
                restaurante.servirComida();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

