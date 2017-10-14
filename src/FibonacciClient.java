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
    private static int port;
    private static InetAddress address;

    public static void main(String argv[]) throws Exception {
        port = 8080;
        address = InetAddress.getByName("127.0.0.1");
        parseArguments(argv);
        String sentence;
        String result;
        Socket clientSocket;
        try {
            clientSocket = new Socket(address, port);
        } catch (ConnectException e) {
            System.out.println("Server not reachable.");
            return;
        }
        System.out.println("Connection established.");

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        while (true) {
            System.out.print("Enter a number: ");
            sentence = inFromUser.readLine();
            if (sentence.equals("exit")) {
                break;
            }
            
            try {
                outToServer.writeBytes(sentence + '\n');
                result = inFromServer.readLine();
            } catch(IOException e) {
                System.out.println("Connection closed.");
                result = ""; // needed
                System.exit(1);
            }
            result = parseResponse(result);
            
            System.out.println(result);
        }
        clientSocket.close();
    }
    
    private static String parseResponse(String response) {
        if(response == null){
            System.out.println("Connection closed.");
            System.exit(1);
        }
        String[] data;
        data = response.split(";");
        switch(data[0]){
            case "200":
                return "Result: " + data[1];
            case "401":
                return "Invalid input.";
            case "402":
                return "Number too low.";
            case "501":
                return "The number is too large for the server.";
            default:
                return "The server didn't responded correctly.";
        }
    }
    
    // parses the command line arguments
    private static void parseArguments(String argv[]) {
        for (int i = 0;i < argv.length;i++) {
            switch(argv[i]) {
                // argument to set the port
                case "-p":
                    i++;
                    // check if a next argument exists
                    if(i == argv.length){
                        System.out.println("No port provided.");
                    } else {
                        // try to parse the argument as a number
                        try {
                            int number = Integer.parseInt(argv[i]);
                            // check if the number is a valid port
                            if(number >= 0 && number <= 65535){
                                port = number;
                            } else {
                                System.out.println("Invalid port number. (" + number + ")");
                            }
                        } catch(NumberFormatException e) {
                            System.out.println("Invalid port number.");
                        }
                    }
                break;
                //argument to set the address to listen on
                case "-a":
                    i++;
                    // check if a next argument exists
                    if(i == argv.length){
                        System.out.println("No address provided.");
                    } else {
                        try {
                            address = InetAddress.getByName(argv[i]);
                        } catch(UnknownHostException e) {
                            System.out.println("Invalid address. (" + argv[i] + ")");
                        }
                    }
                break;
                // display help info
                case "-h":
                    System.out.println("FibonacciClient");
                    System.out.println("  -h            displays this help");
                    System.out.println("  -p [port]     sets the port to run the server on");
                    System.out.println("  -a [address]  sets the address to run the server on");
                    // exit
                    System.exit(0);
                break;
                default:
                    System.out.println("Unknown command line argument '" + argv[i] + "'");
            }
        }
    }
}
