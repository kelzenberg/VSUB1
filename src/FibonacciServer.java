import java.io.*;
import java.net.*;

public class FibonacciServer {
    public static void main(String argv[]) throws Exception {
        String clientSentence;
        String capitalizedSentence;
        ServerSocket welcomeSocket = new ServerSocket(6789);

        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient =
                    new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            while (true) {
                clientSentence = inFromClient.readLine();
                System.out.println("Received: " + clientSentence);


                long input = Long.parseLong(clientSentence, 10);
                long result = fibonacci(input);

                String response = String.valueOf(result);

                outToClient.writeBytes(response);
            }
        }
    }

    private static long fibonacci(long val) {
        if (val == 1 || val == 0) {
            return val;
        } else {
            return fibonacci(val - 1) + fibonacci(val - 2);
        }
    }
}