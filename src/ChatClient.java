import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) throws Exception {


            Socket socket = new Socket("localhost", 5000);
            System.out.println("Connected to chat server...");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            // Asking for username
            System.out.print("What is your name: ");
            String username = reader.readLine();
            output.println(username + " joined the chat!");

        // Thread for receiving messages
        new Thread(() -> {
            try {
                String msg;
                while ((msg = input.readLine()) != null) {
                    System.out.println(msg);
                }
            } catch (Exception e) {
                System.out.println("Disconnected from server.");
            }
        }).start();

        // Sending messages with username
        while (true) {
            String message = reader.readLine();
            output.println(username + ": " + message);
        }
    }
}
