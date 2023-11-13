package edu.homework6;

import java.io.IOException;
import java.net.*;

public class task6 {

    public static void main(String[] args) {
        String[] protocols = {"TCP", "UDP"};
        int[] ports = {0, 49151};

        for (String protocol : protocols) {
            for (int port = ports[0]; port <= ports[1]; port++) {
                try {
                    if (protocol.equals("TCP")) {
                        ServerSocket serverSocket = new ServerSocket(port);
                        serverSocket.close();
                    } else if (protocol.equals("UDP")) {
                        DatagramSocket datagramSocket = new DatagramSocket(port);
                        datagramSocket.close();
                    }

                    String service = getService(protocol, port);
                    System.out.printf("%7s  %6s  %s%n", protocol, port, service);
                } catch (IOException e) {
                    // порт используется
                }
            }
        }
    }

    private static String getService(String protocol, int port) {
        // получение портов??
        return "";
    }
}
