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

