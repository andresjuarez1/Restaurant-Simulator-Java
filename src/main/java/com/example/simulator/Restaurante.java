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
}
