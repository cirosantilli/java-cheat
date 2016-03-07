/*
ROT-1 server.

Java version of:
https://github.com/cirosantilli/cpp-cheat/tree/f31150c42331407fd434a7e912ca64642710fea2/posix/socket/inet
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketCheat {
    public static void main(String[] args) throws IOException {
        final int port = 12345;
        ServerSocket listener = new ServerSocket(port);
        System.out.printf("listening on port %d%n", port);
        while (true) {
            System.out.println("waiting for client");
            Socket socket = listener.accept();
            System.out.printf("client connected from: %s%n", socket.getRemoteSocketAddress().toString());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(socket.getOutputStream());
            for (String inputLine; (inputLine = in.readLine()) != null;) {
                System.out.println("received:");
                System.out.println(inputLine);
                StringBuilder outputStringBuilder = new StringBuilder("");
                char inputLineChars[] = inputLine.toCharArray();
                for (char c : inputLineChars)
                    outputStringBuilder.append(Character.toChars(c + 1));
                out.println(outputStringBuilder);
            }
            socket.close();
        }
        // Unreachable: user must Ctrl + C to exit.
        //listener.close();
    }
}
