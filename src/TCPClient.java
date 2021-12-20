import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class TCPClient {

    public static String askServer(String hostname, int port, String ToServer) throws IOException {
        byte[] userQuery = ToServer.getBytes(StandardCharsets.UTF_8);
        Socket socket = new Socket(hostname, port);
        socket.getOutputStream().write(userQuery);
        byte[] serverResponse = socket.getInputStream().readAllBytes();
        String serverResponseStr = new String(serverResponse, StandardCharsets.UTF_8);
        socket.close();
        return serverResponseStr;
    }

    public static String askServer(String hostname, int port) throws IOException {
        Socket socket = new Socket(hostname, port);
        byte[] serverResponse = socket.getInputStream().readAllBytes();
        String serverResponseStr = new String(serverResponse, StandardCharsets.UTF_8);
        socket.close();
        return serverResponseStr;
    }
}

