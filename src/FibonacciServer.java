import java.io.*;
import java.net.*;
import java.net.InetAddress;

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
    private static int port;
    private static InetAddress address;

    public static void main(String argv[]) throws Exception {
        port = 8080;
        address = InetAddress.getByName("127.0.0.1");
        parseArguments(argv);
        String received;
        ServerSocket socket;
        // tries to bind to the given address and port
        try {
            socket = new ServerSocket(port, 0, address);
        } catch (IOException e) {
            System.out.println("Unable to bind to " + address.getHostAddress() + ":" + port + ".");
            System.out.println(e.getMessage());
            System.exit(1);
            return; // needed for exiting program if socket threw exception
        }


        System.out.println("Server is listening on " + address.getHostAddress() + ":" + port + ".");

        // this loop waits for (more) Clients to connect when Server has no Client (any longer)
        while (true) {

            Socket connectionSocket = socket.accept();
            BufferedReader inFromClient =
                    new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            System.out.println("Client connected.");

            // runs as long as Client is connected to the Server
            while (true) {

                // reads input from Client and displays it
                received = inFromClient.readLine();
                System.out.println("Received: " + received);

                String response = "";

                try {

                    // parses input of Client & sends it to FibonacciCalc for calculation
                    int input = Integer.parseInt(received, 10);

                    if (input < 0) {
                        response = "402;too low";
                    } else if (input > 91) {
                        response = "501;too large";
                    } else {
                        long result = FibonacciCalc.calculate(input);
                        // outputs calculated result & verifies output
                        System.out.println("Calculated: " + result);
                        response = "200;" + String.valueOf(result);
                    }
                } catch (NumberFormatException e) {
                    response = "401;NaN";
                }

                try {

                    // displays the appropriate response to the result (without parsing needed)
                    outToClient.writeBytes(response + "\n");

                } catch (IOException e) {
                    System.out.println("Client disconnected!");
                    break;
                }
            }
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
                    System.out.println("FibonacciServer");
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

