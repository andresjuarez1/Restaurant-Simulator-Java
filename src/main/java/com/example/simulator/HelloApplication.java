package com.example.simulator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.example.simulator.threads.Chef;
import com.example.simulator.threads.Mesero;
import com.example.simulator.threads.Comensal;
import com.example.simulator.threads.Recepcionista;

public class HelloApplication extends Application {
    private HelloController controller;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        HelloController controller = fxmlLoader.getController();


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

        Thread[] comensalThreads = new Thread[4];
        for (int i = 0; i < comensalThreads.length; i++) {
            comensalThreads[i] = new Thread(new Comensal(restaurante, app.controller));
        }

        chefThread.start();
        meseroThread.start();
        recepcionistaThread.start();

        for (Thread comensalThread : comensalThreads) {
            comensalThread.start();
        }
        launch();
    }
}
