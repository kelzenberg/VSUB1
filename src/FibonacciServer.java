import java.io.*;
import java.net.*;

/**
 * FibonacciServer receives Input from FibonacciClient and uses
 * FibonacciCalc to calculate the ordered Fibonacci number, then
 * sends it back to the Client.
 * <p>
 * Kill the process to stop the Server.
 *
 * @author Jannis Rieger
 * Steffen Ansorge
 * Nicolai Brandt
 */
public class FibonacciServer {

    public static void main(String argv[]) throws Exception {
        String clientSentence;
        String capitalizedSentence;
        ServerSocket welcomeSocket = new ServerSocket(6789);

        // this loop waits for (more) Clients to connect when Server has no Client (any longer)
        while (true) {

            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient =
                    new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            // runs as long as Client is connected to the Server
            while (true) {

                // reads input from Client and displays it
                clientSentence = inFromClient.readLine();
                System.out.println("Received: " + clientSentence);

                String response = "";

                try {

                    // parses input of Client & sends it to FibonacciCalc for calculation
                    int input = Integer.parseInt(clientSentence, 10);
                    long result = FibonacciCalc.calculate(input);

                    // outputs calculated result & verifies output
                    System.out.println("Calculated: " + result);
                    response = "200|" + String.valueOf(result);

                } catch (NumberFormatException e) {
                    response = "401|NaN";
                }

                try {

                    // displays the appropriate response to the result
                    outToClient.writeBytes(response + "\n");

                } catch (IOException e) {
                    System.out.println("Client disconnected!");
                    break;
                }
            }
        }
    }
}