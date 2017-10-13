import java.io.*;
import java.net.*;

public class FibonacciServer {
    public static void main(String argv[]) throws Exception {
        String clientSentence;
        String capitalizedSentence;
        ServerSocket welcomeSocket = new ServerSocket(6789);
        FibonacciCalc calc = new FibonacciCalc();

        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient =
                    new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            while (true) {
                clientSentence = inFromClient.readLine();
                System.out.println("Received: " + clientSentence);


                int input = Integer.parseInt(clientSentence, 10);
                long result = calc.calculate(input);

                String response = String.valueOf(result);

                System.out.println("Calculated: " + response);

                outToClient.writeBytes(response + "\n");
            }
        }
    }
}