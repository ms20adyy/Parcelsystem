import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringJoiner;

public class Log {
    private static Log instance;
    private StringBuilder logBuffer;
    
    // Private constructor to initialize logBuffer
    private Log() {
        logBuffer = new StringBuilder();
    }

    // Singleton method to get the single instance of Log
    public static synchronized Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }

    // Method to log an event with a timestamp
    public void logEvent(String event) {
        String timestamp = java.time.LocalDateTime.now().toString();
        logBuffer.append(timestamp).append(" - ").append(event).append("\n");
    }

    // Method to write the log data to a file
    public void writeLogToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePath)))) {
            writer.write(logBuffer.toString());
            System.out.println("Log written to file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to get the current log as a string
    public String getLog() {
        return logBuffer.toString();
    }
}
