import java.io.*;
import java.net.*;

class FibonacciClient {
    public static void main(String argv[]) throws Exception {
        String sentence;
        String modifiedSentence;
        Socket clientSocket = new Socket("localhost", 6789);

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        while (true) {
            sentence = inFromUser.readLine();
            //System.out.println(sentence);
            if(sentence.equals("exit")){
                break;
            }
            outToServer.writeBytes(sentence + '\n');
            modifiedSentence = inFromServer.readLine();
            System.out.println("FROM SERVER: " + modifiedSentence);
        }
        clientSocket.close();
    }
}