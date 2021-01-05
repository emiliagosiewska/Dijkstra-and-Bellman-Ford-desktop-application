
package lab3.model;

/**
 * Exception class for negative cycle
 * @author Emilia Gosiewska
 * version 3.1
 */
  public class NegativeNodeException extends Exception {

    /**
     * Non-parameter constructor
     */
    public NegativeNodeException() {
    }

    /**
     * Exception class constructor
     *
     * @param message display message
     */
    public NegativeNodeException(String message) {
        super(message);
    }
  }