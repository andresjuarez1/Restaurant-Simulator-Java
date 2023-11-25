package com.example.simulator;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

}
