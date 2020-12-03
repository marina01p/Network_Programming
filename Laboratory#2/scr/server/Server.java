package server;

import utilities.DiffieHellman;
import utilities.ErrorChecker;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server {
    final static int SERVER_PORT = 7777;
    final static int BUFFER_SIZE = 65535;

    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";

    final String secretKey = DiffieHellman.generatePublicKey();

    public void newServer() {
        System.out.println("--- Server is listening");
        try {

            DatagramSocket socket = new DatagramSocket(SERVER_PORT);
            byte[] buffer = new byte[BUFFER_SIZE];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            try {

                while (true) {
                    socket.receive(packet);
//                    System.out.println("Encrypted: " + new String(buffer, 0, packet.getLength()));

                    String returnString = new String(buffer, 0, packet.getLength());

                    byte[] buffer2 = returnString.getBytes();


                    String decryptedString = DiffieHellman.decrypt(returnString, secretKey);
                    ErrorChecker.getCRC32Checksum(decryptedString.getBytes());
                    System.out.println(ANSI_BLUE + "\n--- Received data: " + ANSI_RESET);
                    System.out.println(decryptedString);
//                    String reqResponse;
//                    if (decryptedString.equals("1234")){
//                        int dataLength = 100;
//                        byte[] clientData = new byte[BUFFER_SIZE];
//                        String ack = "Client you message has been received.";
//                        clientData = ack.getBytes();
//                        DatagramPacket sendAck = new DatagramPacket(clientData, clientData.length, address, SERVER_PORT);
//                        socket.send(sendAck);
//                    } else {
//                        reqResponse = "Wrong Password!";
//                    }
                    InetAddress address = packet.getAddress();
                    int port = packet.getPort();
                    packet = new DatagramPacket(buffer2, buffer2.length, address, port);
                    socket.send(packet);
                }


            } catch (SocketException e) {
                System.out.println("SocketException: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
