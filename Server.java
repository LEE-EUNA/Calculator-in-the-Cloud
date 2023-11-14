import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Server {

    public static void main(String[] args) throws IOException {
        // Create a server socket to listen for incoming connections on port 7891
        ServerSocket listener = new ServerSocket(7891);
        System.out.println("The Calculator server is running...");
        
        // Create a thread pool with a fixed number of threads
        ExecutorService pool = Executors.newFixedThreadPool(20);

        while (true) {
            // Accept a connection from a client
            Socket socket = listener.accept();

            // Execute a new Calculator thread to handle the client's request
            pool.execute(new Calculator(socket));
        }
    }

    private static class Calculator implements Runnable {

        private Socket socket;

        Calculator(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("Connected: " + socket);

            BufferedReader in = null;
            BufferedWriter out = null;

            try {
                // Set up input and output streams for communication with the client
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                while (true) {
                    // Read the input message from the client
                    String inputMessage = in.readLine();

                    String result = "";

                    // Tokenize the input message to extract operator and operands
                    StringTokenizer st = new StringTokenizer(inputMessage, " ");
                    if (st.countTokens() > 3) {
                        throw new ManyArgumentException();
                    }

                    String opcode = st.nextToken().toUpperCase();
                    double op1 = Double.parseDouble(st.nextToken());
                    double op2 = Double.parseDouble(st.nextToken());

                    // Perform the requested operation based on the operator
                    switch (opcode) {
                        case "ADD":
                            opcode = "+";
                            result = Double.toString(op1 + op2);
                            break;
                        case "MIN":
                            opcode = "-";
                            result = Double.toString(op1 - op2);
                            break;
                        case "MUL":
                            opcode = "*";
                            result = Double.toString(op1 * op2);
                            break;
                        case "DIV":
                            opcode = "/";
                            if (op2 == 0) {
                                throw new DivideByZeroException();
                            } else
                                result = Double.toString(op1 / op2);
                            break;
                        default:
                            result = "error";
                    }
                    
                    // Display the calculation result
                    System.out.println(op1 + " " + opcode + " " + op2 + " = " + result);

                    // Send the result back to the client
                    out.write(result + "\n");
                    out.flush();
                }
            } catch (DivideByZeroException e) {
                System.out.println("Incorrect: " + e.getMessage());

                try {
                    // Send an error message back to the client in case of divide by zero
                    out.write("Error message: " + e.getMessage());
                    out.flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace(); // Handle the IOException
                }

            } catch (ManyArgumentException e) {
                System.out.println("Incorrect: " + e.getMessage());

                try {
                    // Send an error message back to the client in case of too many arguments
                    out.write("Error message: " + e.getMessage());
                    out.flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace(); // Handle the IOException
                }
            } catch (Exception e) {
                System.out.println("Error: " + socket);
            } finally {
                try {
                    // Close the socket when the communication is finished
                    socket.close();
                } catch (IOException e) {
                }
                System.out.println("Closed: " + socket);
            }
        }
    }
}
