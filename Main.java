/**
 * A main package
 */
package pl.polsl.lab3;
import pl.polsl.lab3.ViewController;
import java.util.*;
import java.io.*;
import java.lang.ModuleLayer.Controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

/**
 * A main class of the program that is responsible for handling the launch and start
 * @author Emilia Gosiewska
 * @version 3.1
 */
public class Main extends Application{
//    ViewController viewController = new ViewController() {
//        @Override
//        public void initialize(URL arg0, ResourceBundle arg1) {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
//    };
    /**
     * An empty contructor
     */
    public Main() {
    }


    /**
     * A method that is responsible for launching
     * @param args the arguments provided to the program
     */
    public static void main(String[] args)  {
        launch();
       

    

    }
  /**
   * a start method to load our view
   * @param stage the stage
   * @throws IOException throws exception in case of wrong view
   */
    @Override
     public void start(Stage stage) throws IOException {
        Scene scene = new Scene(loadFXML("/fxml/View"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }
       private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}


