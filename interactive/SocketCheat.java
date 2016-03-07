/*
nc like program to send lines.
*/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.PrintStream;

public class SocketCheat {
    public static void main(String[] args) throws Exception {
        String serverAddress = "localhost";
        int port = 12345;
        if (args.length > 1) {
            serverAddress = args[1];
            if (args.length > 2) {
                port = Integer.parseInt(args[2]);
            }
        }
        String userInput = null;
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        Socket socket = new Socket(serverAddress, port);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream out = new PrintStream(socket.getOutputStream());
        do {
            System.out.println("enter a line:");
            userInput = stdin.readLine();
            out.println(userInput);
            System.out.println("reply:");
            System.out.println(in.readLine());
        } while (!userInput.equals(""));
    }
}
