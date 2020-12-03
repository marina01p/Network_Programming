package client;

import utilities.DiffieHellman;
import utilities.ErrorChecker;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {

    final static int CLIENT_PORT = 7777;
    final static int BUFFER_SIZE = 65535;

    final static int TIMEOUT = 1000;
    private static final int RETRANSMISSION = 3;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public void newClient(){


        final String secretKey = DiffieHellman.generatePublicKey();
        try{

            DatagramSocket datagramSocket = new DatagramSocket();
            InetAddress address = InetAddress.getLocalHost();
            datagramSocket.setSoTimeout(TIMEOUT);

            Scanner scanner = new Scanner(System.in);
            String echoString;


            int tries = 0;
            boolean receivedResponse = false;

//            System.out.println(ANSI_BLUE+ "Pss. Want some money? Welcome to this ATM machine!" + ANSI_RESET);
//            System.out.println("---------------------------------------------------");
//            Thread.sleep(2000);
//            System.out.println("...Insert your card...");
//            Thread.sleep(2000);
//            System.out.println("...Processing...");
//            Thread.sleep(2000);
//            System.out.println("----------------");
//            System.out.println("Enter your pin. I won't look!");

            do {
                System.out.println(ANSI_BLUE + "\n--- Enter your message: " + ANSI_RESET);
                echoString = scanner.nextLine();


                String encryptedData = DiffieHellman.encrypt(echoString, secretKey);
                DatagramPacket sendEncryptedData = new DatagramPacket(encryptedData.getBytes(), encryptedData.getBytes().length, address, CLIENT_PORT);


                ErrorChecker.getCRC32Checksum(encryptedData.getBytes());
                datagramSocket.send(sendEncryptedData);
//                int length = BUFFER_SIZE;
//                byte[] serverDataReceived = new byte[length];

//                DatagramPacket serverResponse = new DatagramPacket(serverDataReceived, serverDataReceived.length);

//                datagramSocket.receive(sendEncryptedData);

//                InetAddress serverIPAddress = sendEncryptedData.getAddress();
//                int serverPort = serverResponse.getPort();
//                String serverMessage = new String(serverResponse.getData());

                byte[] buffer = new byte[BUFFER_SIZE];
                sendEncryptedData = new DatagramPacket(buffer, buffer.length);


//                System.out.println("\n--- Check encryption for equality");
//                System.out.println(encryptedData);
                try {

                    System.out.println("\n--- Receiving the message to server");
                    datagramSocket.receive(sendEncryptedData);

                    System.out.println(ANSI_GREEN + "--- Message received" + ANSI_RESET);

                    if (!sendEncryptedData.getAddress().equals(address)) {
                        throw new IOException("Received packet from an unknown source");
                    }

                    receivedResponse = true;
                } catch (InterruptedIOException e) {
                    tries += 1;
                    System.out.println(ANSI_RED + "--- Running out of time, " + (RETRANSMISSION - tries) + " tries left" + ANSI_RESET);
                } catch (IOException e) {
                    System.out.println("Client error: " + e.getMessage());
                }
            } while (!echoString.equals("exit") && (tries < RETRANSMISSION ));

            if (receivedResponse) {
                System.out.println(ANSI_BLUE + "\nSession closed successfully" + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "\nNo response. Sorry!" + ANSI_RESET);
            }
            datagramSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
