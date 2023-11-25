package com.example.simulator.threads;

import com.example.simulator.HelloController;
import com.example.simulator.Restaurante;

public class Comensal implements Runnable {
    private Restaurante restaurante;

    private HelloController controller;

    public Comensal(Restaurante restaurante, HelloController controller) {
        this.restaurante = restaurante;
        this.controller = controller;
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
