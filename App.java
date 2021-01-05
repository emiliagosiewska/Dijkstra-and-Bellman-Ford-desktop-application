package pl.polsl.lab3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App an application class
 * Emilia Gosiewska
 * version 3.1
 */
public class App extends Application {

    /**
     * An empty contructor
     */
    public App()
    {
        
    }
 
    /**
     * an method to start the program, load FXML file and display content on stage
     * @param stage the stage
     * @throws IOException in case of wrong view
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("View.fxml"));
        stage.setTitle("Dijkstra and Bellman-Ford algorithms");
        stage.setScene(new Scene(parent,640, 480));
        stage.show();
    }

/**
 * calling the main
 * @param args starting arguments
 */
    public static void main(String[] args) {
        launch(args);
    }

}