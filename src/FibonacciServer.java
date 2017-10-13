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
                
                String response = "";
                
                try {
                    int input = Integer.parseInt(clientSentence, 10);
                    long result = FibonacciCalc.calculate(input);
                    System.out.println("Calculated: " + result);
                    response = "200|" + String.valueOf(result);
                } catch(NumberFormatException e) {
                    response = "401|NaN";
                }
                
                try {
                    outToClient.writeBytes(response + "\n");
                } catch(IOException e) {
                    System.out.println("Client disconnected!");
                    break;
                }
            }
        }
    }
}