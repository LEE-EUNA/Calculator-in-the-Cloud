import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    public static void main(String[] args) throws Exception{

        // Specify the file path for the server configuration
        String configFilePath = "server_info.dat";

        // Create an instance of ServerConfiguration to read server information from the specified file
        ServerConfiguration serverConfig = new ServerConfiguration(configFilePath);

        // Retrieve server IP address and port number from the configuration
        String serverIP = serverConfig.getServerIP();
        int serverPort = serverConfig.getServerPort();

        // Display the server information that the client will use
        System.out.println("Server IP: " + serverIP);
        System.out.println("Server Port: " + serverPort);
        System.out.println("");

        // Create a socket to establish a connection with the server
        var socket = new Socket(serverIP, serverPort);
        System.out.println("Enter a protocol-based statement.");
        System.out.println("To terminate the connection, press Ctrl+D and enter");

        // Set up scanners to read from the console and the socket's input stream, and a PrintWriter for output
        Scanner scanner = new Scanner(System.in);
        Scanner in = new Scanner(socket.getInputStream());
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Continuously read input from the console, send it to the server, and display the server's response
        while(scanner.hasNextLine()){
            out.println(scanner.nextLine());
            System.out.println(in.nextLine());
        }
    }
}
