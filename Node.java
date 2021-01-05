
/**
 * A package node that contains Node class which contains the nodes of the graph
 */
package lab3.model;

/**
 * Class to represent a node in the graph
 *
 * @author Emilia Gosiewska
 * @version 3.1
 */
public class Node {

    private int node;
    private int cost;

    /**
     * An empty constructor
     */
    public Node() {
    }

    /**
     * A method that sets the node and weight
     *
     * @param node is a number of the node
     * @param cost is a weight of the node
     */
    public Node(int node, int cost) {
        this.node = node;
        this.cost = cost;

    }

    /**
     * Comparing two nodes
     *
     * @param node2 second node
     * @return returns nothing, just an exit
     */

    public int compare(Node node2) {
        if (this.cost < node2.cost) {
            return -1;
        }
        if (this.cost > node2.cost) {
            return 1;
        }
        return 0;
    }

    /**
     * A method to get the value of the node
     *
     * @return returns the node
     */
    public int getNode() {
        return node;
    }

    /**
     * A method to get the weight of the node
     *
     * @return returns the weight/cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * A method that sets a node
     *
     * @param newNode Is node variable
     */
    public void setNode(int newNode) {
        node = newNode;
    }

    /**
     * A method that sets a weight
     *
     * @param newCost Is weight variable
     */
    public void setCost(int newCost) {
        cost = newCost;
    }
}
