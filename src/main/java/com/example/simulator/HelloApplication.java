package com.example.simulator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.example.simulator.threads.Chef;
import com.example.simulator.threads.Mesero;
//import com.example.simulator.threads.Comensal;
import com.example.simulator.threads.Recepcionista;

public class HelloApplication extends Application {
    private HelloController controller;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 440);

        HelloController controller = fxmlLoader.getController();
        controller.redirectSystemOutput(); // Llama al método para redirigir la salida de la consola

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        Restaurante restaurante = new Restaurante();

        HelloApplication app = new HelloApplication();

        Thread chefThread = new Thread(new Chef(restaurante, app.controller));
        Thread meseroThread = new Thread(new Mesero(restaurante, app.controller));
        Thread recepcionistaThread = new Thread(new Recepcionista(restaurante, app.controller));

        int numMeseros = (int) (Restaurante.CAPACIDAD_MAXIMA * 0.3);
        Thread[] meserosThreads = new Thread[numMeseros];
        for (int i = 0; i < meserosThreads.length; i++) {
            meserosThreads[i] = new Thread(new Mesero(restaurante, app.controller));
        }

        chefThread.start();
        meseroThread.start();
        recepcionistaThread.start();

        for (Thread meseroIndividualThread : meserosThreads) {
            meseroIndividualThread.start();
        }
        launch();
    }
}
