package client;

import junit.framework.Assert;
import utilities.DiffieHellman;
import utilities.ErrorChecker;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {

    final static int CLIENT_PORT = 5200;
    final static int TIMEOUT = 1000;
    private static final int RETRANSMISSION = 10;


    public void newClient() throws IOException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {
        InetAddress address = InetAddress.getLocalHost();
        DatagramSocket datagramSocket = new DatagramSocket();

        Scanner scanner = new Scanner(System.in);
        String echoString;

        datagramSocket.setSoTimeout(TIMEOUT);
        int tries = 0;

        do {
            System.out.println("Enter string to be echoed: ");
            echoString = scanner.nextLine();
            byte[] buffer = echoString.getBytes();

            ErrorChecker.getCRC32Checksum(buffer);
            System.out.println("Checksum: " + ErrorChecker.getCRC32Checksum(buffer));



            SecretKey key = DiffieHellman.generateKey(128);
            IvParameterSpec ivParameterSpec = DiffieHellman.generateIv();


            String algorithm = "AES/CBC/PKCS5Padding";
            String cipherText = DiffieHellman.encrypt(algorithm, echoString, key, ivParameterSpec);

            byte[] testing = cipherText.getBytes();

            DatagramPacket testingthis = new DatagramPacket(testing, testing.length, address, CLIENT_PORT);



            System.out.println("Encrypted: " + cipherText);
            datagramSocket.send(testingthis);

            byte[] buffer3 = new byte[65535];
            testingthis = new DatagramPacket(buffer3, buffer3.length);

            try {
                System.out.println("Receiving message...");
                datagramSocket.receive(testingthis);
                System.out.println("Message received");
            } catch (InterruptedIOException e) {
                tries += 1;
                System.out.println("Timed out, " + (RETRANSMISSION - tries) + " more tries...");
            } catch (IOException e) {
                System.out.println("Client error: " + e.getMessage());
            }
        } while (!echoString.equals("exit") && (tries < RETRANSMISSION ));
        datagramSocket.close();
    }


    public static void main(String[] args) throws IOException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException {
        Main client = new Main();
        client.newClient();
    }
}
