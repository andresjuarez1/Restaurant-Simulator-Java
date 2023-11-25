package com.example.simulator;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Restaurante {
    public static final int CAPACIDAD_MAXIMA = 10;
    public int comensalesEnRestaurante = 0;

    public Lock lock = new ReentrantLock();
    public Condition bufferVacio = lock.newCondition();
    public Condition bufferLleno = lock.newCondition();

    public boolean bufferDeComidaListo = false;

    public void entrarComensal() throws InterruptedException {
        lock.lock();
        try {
            // Si el restaurante está lleno, el recepcionista bloquea la entrada
            while (comensalesEnRestaurante >= CAPACIDAD_MAXIMA) {
                System.out.println("Restaurante lleno. Comensal esperando afuera.");
                bufferLleno.await();
            }

            comensalesEnRestaurante++;
            System.out.println("Comensal entra al restaurante. Comensales en el restaurante: " + comensalesEnRestaurante);

            // Notificar al chef que hay un comensal nuevo
            bufferDeComidaListo = true;
            bufferVacio.signal();
        } finally {
            lock.unlock();
        }
    }

    public void cocinar() throws InterruptedException {
        lock.lock();
        try {
            // El chef espera hasta que haya un comensal en el restaurante
            while (!bufferDeComidaListo) {
                bufferVacio.await();
            }

            // Simular tiempo de cocina
            Thread.sleep(2000);

            System.out.println("Chef ha cocinado la comida.");

            // Notificar al mesero que la comida está lista
            bufferDeComidaListo = false;
            bufferLleno.signal();
        } finally {
            lock.unlock();
        }
    }

    public void servirComida() throws InterruptedException {
        lock.lock();
        try {
            // El mesero espera hasta que la comida esté lista y haya al menos un comensal
            while (!bufferDeComidaListo || comensalesEnRestaurante <= 0) {
                bufferVacio.await();
            }

            // Simular tiempo de llevar la comida al comensal
            Thread.sleep(1000);

            System.out.println("Mesero lleva la comida al comensal.");

            // Comensal come
            Thread.sleep(3000);

            System.out.println("Comensal ha terminado de comer.");

            // Liberar espacio en el restaurante
            comensalesEnRestaurante--;
            System.out.println("Comensal sale del restaurante. Comensales en el restaurante: " + comensalesEnRestaurante);

            // Notificar al recepcionista que hay un espacio libre
            bufferLleno.signal();
        } finally {
            lock.unlock();
        }
    }
}
