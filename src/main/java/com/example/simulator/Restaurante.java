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
    public Queue<Comensal> colaEspera = new LinkedList<>();
    public static final int TAMANO_BUFFER_COMIDA = 5;
    public Queue<Comida> bufferComidas = new LinkedList<>();

    public Restaurante() {
        this.numCocineros = (int) (CAPACIDAD_MAXIMA * 0.1);
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


