import java.io.*;

// The ServerConfiguration class represents a configuration reader for server settings.
// It reads server configuration details from a specified file or uses default values if there is an issue.
 
public class ServerConfiguration {
    private String serverIP;
    private int serverPort;

    
    // Constructs a ServerConfiguration object with default values and attempts to read configuration from a file.
    public ServerConfiguration(String filePath) {
        // Initialize with default values
        serverIP = "localhost";
        serverPort = 1234;

        // Try to read configuration from the specified file
        readConfigurationFromFile(filePath);
    }

    
    // Reads server configuration details from the specified file and updates the object's properties.
    private void readConfigurationFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    if (key.equalsIgnoreCase("serverIP")) {
                        serverIP = value;
                    } else if (key.equalsIgnoreCase("serverPort")) {
                        try {
                            serverPort = Integer.parseInt(value);
                        } catch (NumberFormatException e) {
                            System.err.println("Error parsing server port. Using default port.");
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading configuration file. Using default values.");
        }
    }

    // Gets the server IP address from the configuration.
    public String getServerIP() {
        return serverIP;
    }

    // Gets the server port number from the configuration.
    public int getServerPort() {
        return serverPort;
    }
}
