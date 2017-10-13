import java.io.*;
import java.net.*;

/**
 * FibonacciClient sends Input to FibonacciServer and waits for the
 * calculated answer. Then displays the received answer.
 *
 * Console parameters available: input an integral number, press Enter to send it to the Server
 * Wait for the calculated answer.
 * Kill the process to stop the Client.
 *
 * @author
 * Jannis Rieger
 * Steffen Ansorge
 * Nicolai Brandt
 */
class FibonacciClient {
    
    public static void main(String argv[]) throws Exception {
        String sentence;
        String modifiedSentence;
        Socket clientSocket;
        try {
            clientSocket = new Socket("localhost", 6789);
        } catch (ConnectException e) {
            System.out.println("Server not reachable.");
            return;
        }

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        while (true) {
            sentence = inFromUser.readLine();
            if (sentence.equals("exit")) {
                break;
            }
            outToServer.writeBytes(sentence + '\n');
            modifiedSentence = inFromServer.readLine();
            System.out.println("FROM SERVER: " + modifiedSentence);
        }
        clientSocket.close();
    }
}