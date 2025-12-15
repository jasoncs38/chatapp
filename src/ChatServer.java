import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static List<Socket> clients = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(1235);
        System.out.println("Server started on port 1235...");

        while (true) {
            Socket socket = ss.accept();
            clients.add(socket);
            System.out.println("New user connected!");


            //creating thread
            new ClientHandler(socket).start();
        }
    }


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
