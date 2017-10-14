/**
 * FibonacciCalc
 * Calculates n-th fibonacci number.
 *
 * @author
 * Jannis Rieger
 * Steffen Ansorge
 * Nicolai Brandt
 *
 * @version 1.0
 */

class FibonacciCalc {

    private FibonacciCalc() {
    }

    /**
     *
     * @param num the desired fibonacci number.
     * @return calculated fibonacci number.
     */
    static long calculate(int num) {

        if (num <= 0)
            return 0;
        else if (num == 1)
            return 1;
        else if (num == 2)
            return 1;
        else {
            int i = 3;
            long n1 = i - 1;
            long n2 = i - 2;
            long out = 1;

            while (i < num) {
                out = n1 + n2;
                n2 = n1;
                n1 = out;
                
                // System.out.println(out);
                
                i++;
            }
            return out;
        }
    }
}