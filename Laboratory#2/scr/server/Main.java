package server;

import junit.framework.Assert;
import utilities.DiffieHellman;
import utilities.ErrorChecker;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Main {
    final static int SERVER_PORT = 5200;

    public void newServer() throws IOException, NoSuchAlgorithmException {
        DatagramSocket socket = new DatagramSocket(SERVER_PORT);
        byte[] buffer = new byte[65535];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        try {

            while(true) {

                socket.receive(packet);

                System.out.println("Encrypted: " + new String(buffer, 0, packet.getLength()));

                String returnString = new String(buffer, 0, packet.getLength());

                byte[] buffer2 = returnString.getBytes();

//                ErrorChecker checksum = new ErrorChecker();
                ErrorChecker.getCRC32Checksum(buffer);

                String input = returnString;
                SecretKey key = DiffieHellman.generateKey(128);
                IvParameterSpec ivParameterSpec = DiffieHellman.generateIv();
                String algorithm = "AES/CBC/PKCS5Padding";
                String returnString1 = DiffieHellman.encrypt(algorithm, input, key, ivParameterSpec);
                String plainText = DiffieHellman.decrypt(algorithm, returnString1, key, ivParameterSpec);
                Assert.assertEquals(input, plainText);
                System.out.println(plainText);

                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buffer2, buffer2.length, address, port);
                socket.send(packet);
            }

        } catch (SocketException e) {
            System.out.println("SocketException: " + e.getMessage());
        } catch(IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Main server = new Main();
        server.newServer();


    }
}
