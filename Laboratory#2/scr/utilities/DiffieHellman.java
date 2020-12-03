package utilities;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class DiffieHellman {

    private static SecretKeySpec secretKey;
    private static byte[] key;


    public static String generatePublicKey() {
        Random random = new Random(40);
        for(int i = 0; i < 5; i++) {
        } return String.valueOf(random.nextInt(100));
    }

    public static void setKey(String myKey) {
        MessageDigest sha;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String encryptString, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(encryptString.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Exception: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String decryptString, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(decryptString)));
        } catch (Exception e) {
            System.out.println("Exception: " + e.toString());
        }
        return null;
    }


}