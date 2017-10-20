import java.io.*;
import java.net.*;
import java.lang.String;

/**
 * FibonacciClient sends Input to FibonacciServer and waits for the
 * calculated answer. Then displays the received answer.
 * <p>
 * Console parameters available: input an integral number, press Enter to send it to the Server
 * Wait for the calculated answer.
 * Kill the process to stop the Client.
 *
 * @author Jannis Rieger
 * Steffen Ansorge
 * Nicolai Brandt
 */
class FibonacciClient {
    private static int port;
    private static InetAddress address;

    public static void main(String argv[]) throws Exception {
        port = 8080;
        address = InetAddress.getByName("127.0.0.1");
        //Parses the command line arguments
        parseArguments(argv);
        String userInput; //input of the user
        String result; //answer of the server
        Socket clientSocket; //The socket
        try {
            //tries to connect to the server
            clientSocket = new Socket(address, port);
        } catch (ConnectException e) {
            System.out.println("Server not reachable.");
            return;
        }
        System.out.println("Connection established.");

        //Command line reader to read the user input
        BufferedReader clReader = new BufferedReader(new InputStreamReader(System.in));
        //Server ouput
        DataOutputStream serverWriter = new DataOutputStream(clientSocket.getOutputStream());
        //Server input
        BufferedReader serverReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        while (true) {
            //Reads the input from the user
            System.out.print("Enter a number: ");
            userInput = clReader.readLine();
            //if the user entered "exit/stop" the application exists
            if (userInput.equals("exit") || userInput.equals("stop")) {
                break;
            }

            try {
                // tries to send the entered value to the server
                serverWriter.writeBytes(userInput + '\n');
                // saves the result
                result = serverReader.readLine();
            } catch (IOException e) {
                //An error occoured while writing the value to the server or while reading the response
                System.out.println("Connection closed.");
                result = ""; // needed
                System.exit(1);
            }
            //parses the response of the server
            result = parseResponse(result);
            //Prints the result
            System.out.println(result);
        }
        clientSocket.close();
    }

    private static String parseResponse(String response) {
        if (response == null) {
            //The socket read null. The socket broke
            System.out.println("Connection closed.");
            System.exit(1);
        }
        String[] data;
        //Splits the response intro responseCode and payload
        data = response.split(";");
        switch (data[0]) {
            // 200 OK
            case "200":
                return "Result: " + data[1];
            // 4xx The request contained an error
            // The entered data was invalid
            case "401":
                return "Invalid input.";
            // The entered number was too low
            case "402":
                return "Number too low.";
            // 5xx The server ran into an error
            // The server is not able to execute the calculation on a number that large
            case "501":
                return "The number is too large for the server.";
            // The responseCode was not valid
            default:
                return "The server didn't respond correctly.";
        }
    }

    // parses the command line arguments
    private static void parseArguments(String argv[]) {
        for (int i = 0; i < argv.length; i++) {
            switch (argv[i]) {
                // argument to set the port
                case "-p":
                    i++;
                    // check if a next argument exists
                    if (i == argv.length) {
                        System.out.println("No port provided.");
                    } else {
                        // try to parse the argument as a number
                        try {
                            int number = Integer.parseInt(argv[i]);
                            // check if the number is a valid port
                            if (number >= 0 && number <= 65535) {
                                port = number;
                            } else {
                                System.out.println("Invalid port number. (" + number + ")");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid port number.");
                        }
                    }
                    break;
                //argument to set the address to listen on
                case "-a":
                    i++;
                    // check if a next argument exists
                    if (i == argv.length) {
                        System.out.println("No address provided.");
                    } else {
                        try {
                            address = InetAddress.getByName(argv[i]);
                        } catch (UnknownHostException e) {
                            System.out.println("Invalid address. (" + argv[i] + ")");
                        }
                    }
                    break;
                // display help info
                case "-h":
                    System.out.println("FibonacciClient");
                    System.out.println("  -h            displays this help");
                    System.out.println("  -p [port]     sets the port of the server");
                    System.out.println("  -a [address]  sets the address of the server");
                    // exit
                    System.exit(0);
                    break;
                default:
                    System.out.println("Unknown command line argument '" + argv[i] + "'");
            }
        }
    }
}
