/**
 * FibonacciCalc
 * Calculates the n-th fibonacci number.
 *
 * @author Jannis Rieger
 * Steffen Ansorge
 * Nicolai Brandt
 */

class FibonacciCalc {

    private FibonacciCalc() {
    }

    /**
     * @param num Number n (n >= 0) in Fn of the fibonacci sequence
     * @return calculated fibonacci number of Fn
     */
    static long calculate(int num) {

        // check if num is inside the range of predefined results (0 to 2)
        if (num <= 0)
            return 0;   // predefined
        else if (num == 1)
            return 1;   // predefined
        else if (num == 2)
            return 1;   // predefined
        else {
            // set index to 3rd fibonacci number as standard
            int i = 3;
            // calculate previous fibonacci number to i
            long n1 = i - 1;
            // calculate pre-previous fibonacci number to i
            long n2 = i - 2;
            // initialize output
            long out = 1;
            // while index is less than the input num...
            while (i < num) {
                // ...calculate previous fibonacci numbers (iteratively)
                out = n1 + n2;
                // set new origins from calculated numbers
                n2 = n1;
                n1 = out;
                // add 1 to the index
                i++;
            }
            // return result when while ended
            return out;
        }
    }
}