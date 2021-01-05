
package lab3.test;
import java.io.FileNotFoundException;
import java.io.FileReader;
import lab3.model.Model;
import lab3.model.Node;
import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import lab3.model.Algorithm;
import lab3.model.NegativeNodeException;



/**
 * A class to test methods from package model
 * @author Emilia Gosiewska
 * version 3.1
 */
public class ModelTest {
     /**
     * creating instance of the class
     */
    private Model model;
     /**
     * An empty constructor
     */
    public ModelTest()
    {   
    }
    
// I don't use this enum anymore
///**
// * A test to test the enums using asserEquals
// */    
// @Test
//public void testEnumEquals(){
//Algorithm.Algo algo = Algorithm.Algo.DIJKSTRA;
//System.out.println("Algorithm enum inside the class is set a value: "+ algo);
//assertEquals(Algorithm.Algo.valueOf("DIJKSTRA"), algo);
//Algorithm.Algo algo1 = Algorithm.Algo.BELLMAN_FORD;
//System.out.println("Algorithm enum inside the class is set a value: "+ algo1);
//assertEquals(Algorithm.Algo.valueOf("BELLMANFORD"), algo1);
//Algorithm.Algo algo2 = Algorithm.Algo.ALL;
//System.out.println("Algorithm enum inside the class is set a value: "+ algo1 + algo);
//assertEquals(Algorithm.Algo.valueOf("BELLMANFORD"), algo1);
//assertEquals(Algorithm.Algo.valueOf("DIJKSTRA"), algo);
//}
//
///**
// * A test to test the enums using assertNotNull and asserNotSame 
// */
//@Test
//public void testEnumNot(){
//Algorithm.Algo algo = Algorithm.Algo.DIJKSTRA;
//Algorithm.Algo algo1 = Algorithm.Algo.BELLMAN_FORD;
//assertNotNull(algo, "Variable is null!");
//assertNotNull(algo1, "Variable is null!");
//assertNotSame(algo, algo1, "References indicates the same variable!");
//}

    
    
/**
 * A method that parses the file with the distances 
 * @param outputsF the input file
 * @return returns the list with the data from the file
 * @throws FileNotFoundException  throws exception in case of not finding the file
 */
public ArrayList<Integer> parseNodes(String outputsF) throws FileNotFoundException
{
    FileReader fil;
    try{
      fil = new FileReader(outputsF);
    } catch(FileNotFoundException e){
        throw new FileNotFoundException();
    }
    Scanner scanner = new Scanner(fil);
    ArrayList<Integer> data = new ArrayList<>();
    while(scanner.hasNext())
    {
        Integer data1 = scanner.nextInt();
        data.add(data1);
    }
    scanner.close();
    return data;
}

public List<List<Node>> parseFileAsGraph(String file) throws FileNotFoundException {
        FileReader fil;

        try {
            fil = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Cannot open file");
        }
        Scanner scanner = new Scanner(fil);
        int size = 0; //the size of the graph, the user gives the size as well as it is also in the file
        try {
            if (scanner.hasNext()) {
                size = scanner.nextInt();
            }
        }catch (InputMismatchException ex) {
            scanner.nextLine();
         }



        List<List<Node>> adjList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            adjList.add(new ArrayList<>());
        }

        int x = 0; //first node
        int y = 0; //second node
        int weight = 0; //the weight of the nodes
        while (scanner.hasNext()) {
            try {
                x = scanner.nextInt();
                y = scanner.nextInt();
                weight = scanner.nextInt();
            } catch (InputMismatchException ex) {
                scanner.nextLine();
            }
            
            Node node = new Node(y, weight);
            adjList.get(x).add(node);
        }
       
        return adjList;
    }
 /**
  * A paramterized test that takes two inputs one of them is the file with nodes that will be sorted using both Dijkstra and Bellman-Ford algorithms,
  *  while the second is the result of finding the shortest path, both should be equal to expected value - the content of the outputsF
  * @param inputsF input file with the graph that will be sorted
  * @param outputsF input file with the expected result
  */       
@ParameterizedTest
@CsvSource({ "graph2.txt , output.txt"
})
public void DijkstraBellmanTestsCorrect(String inputsF, String outputsF)
{
    
    List<List<Node>> inputs = new ArrayList<>();
    List<Integer> outputs = new ArrayList<>();
    System.out.println(inputsF);
    System.out.println(outputsF);
    
    try{
        inputs = parseFileAsGraph(inputsF);
        outputs = parseNodes(outputsF);
    } catch (FileNotFoundException e) {
        throw new RuntimeException("Opening the file or parsing the file ended unsuccesfully!");
    }
    
    Model model = new Model(inputs);
    List<Integer> distanceBellman; 
    try{
        distanceBellman = model.bellmanFord(0);
    } catch(NegativeNodeException ex) {
        throw new RuntimeException("The given graph contains negative cycle");
    }
        List<Integer> distanceDijkstra;
        try{
        distanceDijkstra = model.algoDijkstra(0);
        } catch(NegativeNodeException ex) {
        throw new RuntimeException("The given graph contains negative cycle");
    }
        assertArrayEquals(outputs.toArray(), distanceBellman.toArray());
        assertArrayEquals(outputs.toArray(), distanceDijkstra.toArray());
    }

/**
 * A test that checks the negative cycle in the algorithm, creates the graph that creates negative cycles, the expected result is the message that the negative cycle has occured
 */
@Test
public void negativeCycleBellmanFord()
{
    List<List<Node>> adjList = new ArrayList<>();
    

    adjList.add(List.of(new Node(1,1)));


    adjList.add(List.of(new Node(2,-1)));


    adjList.add(List.of(new Node(3,-1)));


    adjList.add(List.of(new Node(0,-1)));

    
    Model model = new Model(adjList);
    
    Exception ex = assertThrows(NegativeNodeException.class, () -> {
        model.bellmanFord(0);
    });
    String messageExpected = "The given graph contains negative cycle";
    String messageGiven = ex.getMessage();
    assertEquals(messageExpected,messageGiven);
}

/**
 * A test that compares two nodes the expected result is the node1 that equals node2
 */
@Test
public void compareNodes()
{
    Node node1 = new Node(5, 6);
    Node node2 = new Node(7, 6);
    assertEquals(node1.compare(node2), 0);
}

/**
 * A test that compares two nodes, the expected result is -1 when node1 is less than node2 and 1 when node2 is less than node1
 */
@Test
public void compareNodes2()
{
    Node node1 = new Node(2, 4);
    Node node2 = new Node(3, 7);
    assertEquals(node1.compare(node2), -1);
    assertEquals(node2.compare(node1), 1);
    assertFalse(node1.compare(node2) == 0);
}
}
