package pl.polsl.lab3;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import static lab3.model.Algorithm.Algo.AVERAGE;
import static lab3.model.Algorithm.Algo.BAD;
import static lab3.model.Algorithm.Algo.GOOD;
import static lab3.model.Algorithm.Algo.VERY_BAD;
import static lab3.model.Algorithm.Algo.VERY_GOOD;
import lab3.model.Model;
import lab3.model.NegativeNodeException;
import lab3.model.Node;

/**
 * A class that stores all controllers
 *
 * @author Emilia Gosiewska version 3.1
 */
public class ViewController {

    /**
     * An empty contructor
     */
    public ViewController() {

    }

    /**
     * label to print text
     */
    @FXML
    private Label give;

    /**
     * text field to enter the level of satisfaction
     */
    @FXML
    private TextField enumHappy;
    /**
     * the help menu
     */
    @FXML
    private Menu helpMe;

    /**
     * a label to print text
     */
    @FXML
    private Label choose;
    /**
     * button to choose performing dijkstra algorithm
     */
    @FXML
    private Button buttonDijkstra;
    /**
     * a first page with the algorithm, choosing size, node and file and enum
     */
    @FXML
    private Tab tabProg;
    /**
     * a second page with the history
     */
    @FXML
    private Tab tabHis;
    /**
     * button to choose performing Bellman-Ford algorithm
     */
    @FXML
    private Button buttonBellman;
    /**
     * menu bar
     */
    @FXML
    private MenuBar menuBar;
    /**
     * a text field to write the source node
     */
    @FXML
    private TextField sourceField;
    /**
     * a button to save the data and put it into the table
     */
    @FXML
    private Button buttonSave;
    /**
     * a menu exit
     */
    @FXML
    private Menu exitFil;
    /**
     * an anchor pane
     */
    @FXML
    private AnchorPane anchor;
    /**
     * a place that the result of the algorithms will be put
     */
    @FXML
    private TextArea text;
    /**
     * a text place
     */
    @FXML
    private Label give1;
    /**
     * a field to place the file
     */
    @FXML
    private TextField open;

    /**
     * a button to perform both dijkstra and bellman ford algorithms
     */
    @FXML
    private Button buttonAll;
    /**
     * a field to write the size of the graph
     */
    @FXML
    private TextField inputSize;
    /**
     * a list view to print the history of algorithms
     */
    @FXML
    private ListView<String> listView;

    /**
     * a method that takes the source node
     *
     * @param event an event
     * @return returns the source node
     */
    @FXML
    int sourceOnAction(ActionEvent event) {
        String sourceNode = sourceField.getText();
        Integer sourceNodeInt = Integer.parseInt(sourceNode);
        return sourceNodeInt;

    }

    /**
     * a method that takes the size of the graph, remember to put the proper
     * size otherwise you will see the MAX_INTEGER that will fill the empty
     * space
     *
     * @param event an event
     * @return returns the size of the graph
     */
    @FXML
    int inputSizeOnAction(ActionEvent event) {
        String inputS = inputSize.getText();
        Integer inputSizeInt = Integer.parseInt(inputS);
        return inputSizeInt;
    }

    /**
     * a method to parse file
     *
     * @param file given file
     * @param event event
     * @return returns the adjacency list
     * @throws FileNotFoundException if wrong file returns an exception
     */
    @FXML
    public List<List<Node>> parseFileAsGraph(String file, ActionEvent event) throws FileNotFoundException {

        FileReader fil = null;

        int size = inputSizeOnAction(event);

        try {
            fil = new FileReader(file);
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Cannot open file! ");
            alert.showAndWait();

        }
        Scanner scanner = new Scanner(fil);

        List<List<Node>> adjList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            adjList.add(new ArrayList<>());
        }

        int x = 0;
        int y = 0;
        int weight = 0;
        while (scanner.hasNext()) {
            try {
                x = scanner.nextInt();
                y = scanner.nextInt();
                weight = scanner.nextInt();
            } catch (InputMismatchException ex) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");                                   //Alert code taken from https://code.makery.ch/blog/javafx-dialogs-official/
                alert.setHeaderText("Invalid values");
                alert.setContentText("Enter the correct values");
                alert.showAndWait();

                scanner.nextLine();
            }
            if (x < 0) {
                x = scanner.nextInt();
            }
            if (y < 0) {
                y = scanner.nextInt();
            }

            Node node = new Node(y, weight);
            adjList.get(x).add(node);
        }

        return adjList;

    }

    /**
     * a method that opens the file from the text field
     *
     * @param event an event
     */
    @FXML
    void openFile(ActionEvent event) {
        List<Integer> distance = new ArrayList<>();

        List<List<Node>> adjList;

        String fileName = open.getText();

        try {
            adjList = parseFileAsGraph(fileName, event);
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Wrong parsing the file");
            alert.showAndWait();
        }
    }

    /**
     * a method that exits the program
     *
     * @param event an event
     */
    @FXML
    void exitOnAction(ActionEvent event) {
        System.exit(0);
    }

    /**
     * a method that shows help in the alert window
     *
     * @param event an event
     */
    @FXML
    void helpOnAction(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("HELP");
        alert.setContentText("This program is to calculate Dijkstra and Bellman-Ford algorithms. Put source node and observe the results on the text area, ypu can see the history in next page");
        alert.showAndWait();
    }

    /**
     * a method to perform bellman ford algorithm, opening and parsing the file,
     * check exceptions
     *
     * @param event an event
     * @return returns null
     */
    @FXML
    String bellmanOnAction(ActionEvent event) {

        String inputS = inputSize.getText();
        try {
            Integer size = Integer.parseInt(inputS);

            FileReader fil = null;
            String file = open.getText();

            try {
                fil = new FileReader(file);

                Scanner scanner = new Scanner(fil);

                List<List<Node>> adjList = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    adjList.add(new ArrayList<>());
                }

                int x = 0;
                int y = 0;
                int weight = 0;

                while (scanner.hasNext()) {

                    try {
                        x = scanner.nextInt();
                        y = scanner.nextInt();
                        weight = scanner.nextInt();
                    } catch (InputMismatchException ex) {

                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("ERROR");
                        alert.setHeaderText("Invalid values");
                        alert.setContentText("Enter the correct values");
                        alert.showAndWait();
                    }

                    if (x < 0) {
                        x = scanner.nextInt();
                    }
                    if (y < 0) {
                        y = scanner.nextInt();
                    }

                    Node node = new Node(y, weight);
                    adjList.get(x).add(node);
                }

                List<Integer> distance = new ArrayList<>();

                String fileName = open.getText();

                Model model = new Model(adjList);
                String sourceNode = sourceField.getText();
                try {
                    Integer sourceNodeInt = Integer.parseInt(sourceNode);
                    if (sourceNodeInt >= 0) {

                        try {
                            distance = model.bellmanFord(sourceNodeInt);
                            String distanceString = distance.toString();
                            listView.getItems().addAll("Bellman-Ford" + distanceString.replaceAll("2147483647", ""));

                            this.text.setText(distanceString.replaceAll("2147483647", ""));

                        } catch (NegativeNodeException ex) {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("ERROR");
                            alert.setContentText("Negative cycle has occured! ");
                            alert.showAndWait();
                        }
                    } else {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("ERROR");
                        alert.setContentText("Negative source node, please give it again! ");
                        alert.showAndWait();
                    }
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("Source node should be a number! ");
                    alert.showAndWait();

                }
            } catch (FileNotFoundException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Cannot open file! ");
                alert.showAndWait();

            }
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid values");
            alert.setContentText("Size must be a number");
            alert.showAndWait();
        }
        return null;
    }

    /**
     * a method to perform Dijsktra algorithm, opening and parsing the file,
     * check exceptions
     *
     * @param event an event
     * @return returns null
     */
    @FXML
    String dijsktraOnAction(ActionEvent event) {

        String inputS = inputSize.getText();
        try {
            Integer size = Integer.parseInt(inputS);

            FileReader fil = null;

            String file = open.getText();

            try {
                fil = new FileReader(file);

                Scanner scanner = new Scanner(fil);

                List<List<Node>> adjList = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    adjList.add(new ArrayList<>());
                }

                int x = 0;
                int y = 0;
                int weight = 0;
                while (scanner.hasNext()) {
                    try {
                        x = scanner.nextInt();
                        y = scanner.nextInt();
                        weight = scanner.nextInt();
                    } catch (InputMismatchException ex) {

                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("ERROR");                                   //Alert code taken from https://code.makery.ch/blog/javafx-dialogs-official/
                        alert.setHeaderText("Invalid values");
                        alert.setContentText("Enter the correct values");
                        alert.showAndWait();

                        scanner.nextLine();
                    }
                    if (x < 0) {
                        x = scanner.nextInt();
                    }
                    if (y < 0) {
                        y = scanner.nextInt();
                    }

                    Node node = new Node(y, weight);
                    adjList.get(x).add(node);
                }

                List<Integer> distance = new ArrayList<>();

                String fileName = open.getText();

                try {
                    adjList = parseFileAsGraph(fileName, event);
                } catch (FileNotFoundException e) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("Wrong parsing the file");
                    alert.showAndWait();
                }

                Model model = new Model(adjList);

                String sourceNode = sourceField.getText();
                try {
                    Integer sourceNodeInt = Integer.parseInt(sourceNode);
                    if (sourceNodeInt >= 0) {
                        try {
                            distance = model.algoDijkstra(sourceNodeInt);
                            String distanceString = distance.toString();
                            this.text.setText(distanceString.replaceAll("2147483647", ""));
                            listView.getItems().addAll("Dijkstra" + distanceString.replaceAll("2147483647", ""));

                        } catch (NegativeNodeException ex) {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("ERROR");
                            alert.setContentText("Negative cycle has occured! ");
                            alert.showAndWait();
                        }
                    } else {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("ERROR");
                        alert.setContentText("Negative source node, please give it again! ");
                        alert.showAndWait();
                    }
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("Source node should be a number! ");
                    alert.showAndWait();

                }
            } catch (FileNotFoundException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Cannot open file! ");
                alert.showAndWait();

            }
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid values");
            alert.setContentText("Size must be a number");
            alert.showAndWait();
        }
        return null;

    }

    /**
     * a method to perform both Dijkstra and Bellman ford algorithm, opening and
     * parsing the file, check exceptions
     *
     * @param event an event
     * @return returns null
     */
    @FXML
    String allOnAction(ActionEvent event) {

        String inputS = inputSize.getText();
        try {
            Integer size = Integer.parseInt(inputS);

            FileReader fil = null;

            String file = open.getText();

            try {
                fil = new FileReader(file);

                Scanner scanner = new Scanner(fil);
                List<List<Node>> adjList = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    adjList.add(new ArrayList<>());
                }

                int x = 0;
                int y = 0;
                int weight = 0;
                while (scanner.hasNext()) {
                    try {
                        x = scanner.nextInt();
                        y = scanner.nextInt();
                        weight = scanner.nextInt();
                    } catch (InputMismatchException ex) {

                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("ERROR");                                   //Alert code taken from https://code.makery.ch/blog/javafx-dialogs-official/
                        alert.setHeaderText("Invalid values");
                        alert.setContentText("Enter the correct values");
                        alert.showAndWait();

                        scanner.nextLine();
                    }
                    if (x < 0) {
                        x = scanner.nextInt();
                    }
                    if (y < 0) {
                        y = scanner.nextInt();
                    }

                    Node node = new Node(y, weight);
                    adjList.get(x).add(node);
                }

                List<Integer> distance = new ArrayList<>();

                String fileName = open.getText();

                try {
                    adjList = parseFileAsGraph(fileName, event);
                } catch (FileNotFoundException e) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("Wrong parsing the file");
                    alert.showAndWait();
                }

                Model model = new Model(adjList);

                String sourceNode = sourceField.getText();
                try {
                    Integer sourceNodeInt = Integer.parseInt(sourceNode);
                    if (sourceNodeInt >= 0) {
                        try {
                            distance = model.bellmanFord(sourceNodeInt);
                            String distanceString = distance.toString();
                            distance = model.algoDijkstra(sourceNodeInt);
                            String dist = distance.toString();
                            listView.getItems().addAll("Bellman-Ford" + distanceString.replaceAll("2147483647", "") + "Dijkstra" + dist.replaceAll("2147483647", ""));

                            this.text.setText("Bellman-Ford" + distanceString.replaceAll("2147483647", "") + "Dijkstra" + dist.replaceAll("2147483647", ""));

                        } catch (NegativeNodeException ex) {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("ERROR");
                            alert.setContentText("Negative cycle has occured! ");
                            alert.showAndWait();
                        }
                    } else {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("ERROR");
                        alert.setContentText("Negative source node, please give it again! ");
                        alert.showAndWait();
                    }
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("Source node should be a number! ");
                    alert.showAndWait();

                }
            } catch (FileNotFoundException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Cannot open fff file! ");
                alert.showAndWait();

            }
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid values");
            alert.setContentText("Size must be a number");
            alert.showAndWait();
        }
        return null;
    }

    /**
     * A method that takes an enum from the user
     *
     * @param event an event
     */
    @FXML
    void enumOnAction(ActionEvent event) {
        String decision = enumHappy.getText();
        Integer decisionInt = Integer.parseInt(decision);
        if (decisionInt == VERY_BAD.getValue()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Satisfaction");
            alert.setContentText("I'm sorry, I did my best!");
            alert.showAndWait();
        }
        if (decisionInt == BAD.getValue()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Satisfaction");
            alert.setContentText("I'm sorry, I did my best!");
            alert.showAndWait();
        }
        if (decisionInt == AVERAGE.getValue()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Satisfaction");
            alert.setContentText("It could be worse!");
            alert.showAndWait();
        }
        if (decisionInt == GOOD.getValue()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Satisfaction");
            alert.setContentText("Thank you!");
            alert.showAndWait();
        }
        if (decisionInt == VERY_GOOD.getValue()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Satisfaction");
            alert.setContentText("Thank you for you support sweetie!");
            alert.showAndWait();
        }
    }

    /**
     * an empty action, I fill the list in the button calls
     *
     * @param event an event
     */
    @FXML
    void listViewOnAction(ActionEvent event) {

    }

}
