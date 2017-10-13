public class FibonacciCalc {
    /**
     * @author
     */
    public FibonacciCalc() {
    }

    public static int calculate(int num) {

        if (num <= 0)
            return 0;
        else if (num == 1)  //n=1
            return 1;
        else if (num == 2)  //n=2
            return 1;
        else {              //n=3
            int i = 3;
            int n1 = i - 1;   //n-1
            int n2 = i - 2;   //n-2
            int out = 0;

            while (i <= num) {   //von n=3 bis num
                n1 = i - 1;
                n2 = i - 2;
                out = n1 + n2;
                System.out.println(out);
                i++;
            }
            return out;
        }
    }

    public static void main(String[] args) {
        System.out.println(calculate(10));
    }

}