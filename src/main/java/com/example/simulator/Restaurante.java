package com.example.simulator;

import java.util.concurrent.locks.Condition;
import com.example.simulator.threads.Comensal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.LinkedList;
import java.util.Queue;

public class Restaurante {
    public static final int CAPACIDAD_MAXIMA = 20;
    public int mesasOcupadas = 0;
    public int comensalesEnRestaurante = 0;
    public Lock lock = new ReentrantLock();
    public Condition bufferVacio = lock.newCondition();
    public Condition bufferLleno = lock.newCondition();
    public boolean bufferDeComidaListo = false;
    public Lock recepcionistaLock = new ReentrantLock();
    public Condition recepcionistaCondition = recepcionistaLock.newCondition();
    public int numCocineros;
    public Queue<Orden> bufferOrdenes = new LinkedList<>();
    public Queue<Comida> bufferComidas = new LinkedList<>();
    public Queue<Comensal> colaEspera = new LinkedList<>();

    public Restaurante() {
        this.numCocineros = (int) (CAPACIDAD_MAXIMA * 0.1);
    }

    public void cocinarOrden() throws InterruptedException {
        lock.lock();
        try {
            // Espera hasta que haya una orden en el buffer de órdenes
            while (bufferOrdenes.isEmpty()) {
                bufferVacio.await();
            }

            // Simular tiempo de cocina
            Thread.sleep(4000);

            // Tomar la orden del buffer y cocinarla
            Orden orden = bufferOrdenes.poll();
            Comida comida = new Comida(orden);

            // Agregar la comida al buffer de comidas
            bufferComidas.offer(comida);
            System.out.println("Cocinero ha preparado la orden.");

            // Notificar a los meseros que la comida está lista
            bufferLleno.signal();
        } finally {
            lock.unlock();
        }
    }

    public void verificarOrdenLista() throws InterruptedException {
        lock.lock();
        try {
            // Espera hasta que haya comida en el buffer de comidas
            while (bufferComidas.isEmpty()) {
                bufferVacio.await();
            }

            // Simular tiempo de verificar la orden
            Thread.sleep(1000);

            // Tomar la comida del buffer y notificar a los meseros que la orden está lista
            Comida comida = bufferComidas.poll();
            System.out.println("Mesero, la orden está lista para ser servida.");

            // Notificar a los meseros que la comida está lista
            bufferLleno.signal();
        } finally {
            lock.unlock();
        }
    }
}


