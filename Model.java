
/**
 * A package model that contains class Model which implements the Dijkstra and Bellman Ford algorithms 
 */
package lab3.model;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * The class that is responsible for the algorithms
 *
 * @author Emilia Gosiewska
 * @version 3.1
 */
public class Model {
/**
 * creating new linked list 
 */
    List<List<Node>> adjList = new LinkedList<>();

    /**
     * The constructor that takes al as a parameter which is signed to adjList
     *
     * @param al is a adjacency list
     */
    public Model(List<List<Node>> al) {
        adjList = al;
    }

//Bellman-Ford algorithm
    /**
     * This is a method which is responsible for performing Bellman Ford algorithm
     * @param src is a source point
     * @return returns the distans between nodes
     * @throws NegativeNodeException throws an exception in case of the negative
     * cycle is found
     */
    public List<Integer> bellmanFord(int src) throws NegativeNodeException {
        var numberOfVertices = adjList.size();
        List<Integer> distanceArray = new ArrayList<>(numberOfVertices);
        for (int i = 0; i < numberOfVertices; i++) 
            {
            distanceArray.add(Integer.MAX_VALUE);
            }
        // initialize distance of source as 0 
        distanceArray.set(src, 0);
        for (int i = 0; i < numberOfVertices - 1; i++) {
            if (adjList.get(i).isEmpty()) {
                continue;
            }
            for (int j = 0; j < numberOfVertices; j++) {
                for (Node edge : adjList.get(j)) {
                    int sum = distanceArray.get(j) + edge.getCost();
                    if (distanceArray.get(j) != Integer.MAX_VALUE && sum < distanceArray.get(edge.getNode())) {
                        distanceArray.set(edge.getNode(), sum);
                    }

                }
            }
        }

// check for negative-weight cycles. 
        for (int j = 0; j < numberOfVertices; j++) {
            for (Node edge : adjList.get(j)) {
                if (distanceArray.get(j) != Integer.MAX_VALUE && distanceArray.get(j) + edge.getCost() < distanceArray.get(edge.getNode())) {
                    throw new NegativeNodeException("The given graph contains negative cycle");

                }

            }
        }
        return distanceArray;
    }

    /**
     * This is a method which is responsible for performing Dijkstra algorithm
     *
     * @param source is a source point where we start
     * @return returns the distance between nodes
     * @throws NegativeNodeException negative exception in case of negative cycle
     */
    public List<Integer> algoDijkstra(int source) throws NegativeNodeException {    
       
        int verticesNumbers = adjList.size();
        List<Integer> distanceArray = new ArrayList<>(verticesNumbers);
        for (int i = 0; i < verticesNumbers; i++) 
            {
            distanceArray.add(Integer.MAX_VALUE);
            }

        //using lambda expression to compare nodes
        PriorityQueue<Node> pqueue = new PriorityQueue<>(verticesNumbers, (n1, n2) -> n1.compare(n2));
//        List<Integer> history = new LinkedList<>();
        pqueue.add(new Node(source, 0));

        while (!pqueue.isEmpty()) {
            Node node = pqueue.remove();

            if (node.getCost() >= distanceArray.get(node.getNode())) {
                continue;
            }

            distanceArray.set(node.getNode(), node.getCost());

            var edges = adjList.get(node.getNode());

            for (int i = 0; i < edges.size(); i++) {
                int dist = distanceArray.get(node.getNode()) + edges.get(i).getCost();
                if(dist > 0)
                {
                if (dist < distanceArray.get(edges.get(i).getNode())) {
                    Node candidate = new Node(edges.get(i).getNode(), dist);
                    //part of code presented below is using list history, as you advised me, unfortunately something wasn't working and I was always getting a negative cycle
                    // so I figured out a different solution by simply checking the distance, because using Dijkstra algorithm we cannot obtain negative distance
                    // when the negative distance occurs then I throw an exception in other case the node 
                    //is added to the priority queue, and finnaly it works for both negative cycle and positive :)
                    
//                    if(history.contains(candidate)){
//                    throw new NegativeNodeException("Negative cycle has occured!");
//                    }
//                    else 
//                    {
//                    history.add(candidate.getNode());
                    pqueue.add(candidate);
                    }
                }
                else 
                {
                    throw new NegativeNodeException("Negative cycle has occured!");
                }
            }   
        }
        return distanceArray;
    
}
}
