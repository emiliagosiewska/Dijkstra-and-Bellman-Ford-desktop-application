package lab3.model;

//import static lab3.model.Algorithm.Algo.*;

/**
 * A class that stores the enum that user chooses related to his satisfaction of the program
 *
 * @author Emilia Gosiewska version 3.1
 */
public class Algorithm {

    /**
     * An empty contructor
     */
    public Algorithm() {
    }

    /**
     * Enum method that takes 5 parameters and the user will be asked to choose one
     * 
     */
    public enum Algo {
        /**
         * an enum VERY_BAD if the satisfaction is very low
         */
        VERY_BAD(1),
        /**
         * an enum BAD if the satisfaction is low
         */
            BAD(2),
            /**
             * an enum AVERAGE if the satisfaction is on average level
             */
            AVERAGE(3), 
            /**
             * an enum GOOD if the satisfaction is on high level
             */
            GOOD(4), 
            /**
             * an enum VERY_GOOD if the satsifaction is on very high level
             */
            VERY_GOOD(5);
            /**
             *  the value that is assigned to the particular enum
             */
        private int value;

        /**
         * A method that sets the value of the enum
         *
         * @param value value of the particular enum
         */
        Algo(int value) {
            this.value = value;
        }

        /**
         * A method that gets the value of the enum
         *
         * @return returns the value of the particular enum
         */
        public int getValue() {
            return value;
        }
    }
}
