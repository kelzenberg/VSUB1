public class FibonacciCalc {
    /**
     * @author
     */
    private FibonacciCalc() {
    }

    public static long calculate(int num) {

        if (num <= 0)
            return 0;
        else if (num == 1)  //n=1
            return 1;
        else if (num == 2)  //n=2
            return 1;
        else {              //n=3
            int i = 3;
            long n1 = i - 1;   //n-1
            long n2 = i - 2;   //n-2
            long out = 1;

            while (i <= num) {   //von n=3 bis num
                out = n1 + n2;
                n2 = n1;
                n1 = out;
                
                System.out.println(out);
                
                i++;
            }
            return out;
        }
    }

}