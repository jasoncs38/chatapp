import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static List<Socket> clients = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server started on port 5000...");

        while (true) {
            Socket socket = serverSocket.accept();
            clients.add(socket);
            System.out.println("New client connected!");

            // Create a thread for each client
            new ClientHandler(socket).start();
        }
    }

    // Thread to handle each client
    static class ClientHandler extends Thread {
        private Socket socket;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );

                String message;

                while ((message = input.readLine()) != null) {
                    broadcast(message);
                }

            } catch (Exception e) {
                System.out.println("Client disconnected");
            }
        }

        // Send message to all clients
        private void broadcast(String message) throws IOException {
            for (Socket client : clients) {
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                out.println(message);
            }
        }

    }
}
