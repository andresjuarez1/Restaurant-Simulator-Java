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


