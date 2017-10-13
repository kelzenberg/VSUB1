import java.io.*;
import java.net.*;
import java.lang.String;

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
        String result;
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
            System.out.print("Enter a number: ");
            sentence = inFromUser.readLine();
            if (sentence.equals("exit")) {
                break;
            }
            outToServer.writeBytes(sentence + '\n');
            result = inFromServer.readLine();
            result = parseResponse(result);
            
            System.out.println(result);
        }
        clientSocket.close();
    }
    
    private static String parseResponse(String response) {
        if(response == null){
            return "The server didn't responded correctly.";
        }
        String[] data;
        data = response.split(";");
        switch(data[0]){
            case "200":
                return "Result: " + data[1];
            case "401":
                return "Invalid input.";
            default:
                return "The server didn't responded correctly.";
        }
    }
}