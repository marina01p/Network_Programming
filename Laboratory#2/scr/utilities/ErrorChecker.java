package utilities;

import junit.framework.Assert;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;

public class ErrorChecker {

    public static long getCRC32Checksum(byte[] bytes) {
        Checksum crc32 = new CRC32();
        crc32.update(bytes, 0, bytes.length);
        return crc32.getValue();
    }

}

//package utilities;
//
//        import junit.framework.Assert;
//        import org.junit.Test;
//
//        import javax.crypto.*;
//        import javax.crypto.spec.IvParameterSpec;
//        import javax.crypto.spec.PBEKeySpec;
//        import javax.crypto.spec.SecretKeySpec;
//        import java.io.*;
//        import java.security.InvalidAlgorithmParameterException;
//        import java.security.InvalidKeyException;
//        import java.security.NoSuchAlgorithmException;
//        import java.security.SecureRandom;
//        import java.security.spec.InvalidKeySpecException;
//        import java.security.spec.KeySpec;
//        import java.util.Base64;
//
//public class DiffieHellman {
//
//    private DiffieHellman AESUtil;
//    private Assert Assertions;
//
//    // generate a secret key
//    public static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        keyGenerator.init(n);
//        SecretKey key = keyGenerator.generateKey();
//        return key;
//    }
//
//    // generate an random IV (pseudo-random value which
//    // has the same size as the block that is encrypted)
//
//    public static IvParameterSpec generateIv() {
//        byte[] iv = new byte[16];
//        new SecureRandom().nextBytes(iv);
//        return new IvParameterSpec(iv);
//    }
//
//    // Encryption and Decryption
//
//    // encryption
//    // We create an instance from the cipher class by using the getInstance() method
//    // we configure a cipher instance using the init() method with a secret key, IV, and encryption mode
//    // Finally, we encrypt the input string by invoking the doFinal() method.
//    // This method gets bytes of input and returns ciphertext in bytes
//
//    public static String encrypt(String algorithm, String input, SecretKey key,
//                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
//            InvalidAlgorithmParameterException, InvalidKeyException,
//            BadPaddingException, IllegalBlockSizeException {
//        System.out.println("\nData before encr: " + input);
//        String encryptedData = "";
//        try{
//            Cipher cipher = Cipher.getInstance(algorithm);
//            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
//            byte[] cipherText = cipher.doFinal(input.getBytes());
//            encryptedData = Base64.getEncoder().encodeToString(cipherText);
//            System.out.println("\nEncrypted Data: " + encryptedData);
//
//        } catch (NoSuchPaddingException e) {
//            e.getMessage();
//        } return encryptedData;
//    }
//
//    // for decrypting an input string we can initialize out cipher using the DECRYPT_MODE to decrypt the content
//
//    public static String decrypt(String algorithm, byte[] cipherText, SecretKey key,
//                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
//            InvalidAlgorithmParameterException, InvalidKeyException,
//            BadPaddingException, IllegalBlockSizeException {
////        System.out.println("\ndecrypt sout: " + cipherText.toString());
//        byte[] decryptedData;
//        String decData = "";
//        try{
//            Cipher cipher = Cipher.getInstance(algorithm);
//            cipher.init(Cipher.DECRYPT_MODE, key, iv);
//            decryptedData = cipher.doFinal(Base64.getDecoder().decode(cipherText));
//            decData = new String(decryptedData);
//        } catch(NoSuchPaddingException e) {
//            e.getMessage();
//        } return decData;
//    }
//
//
//
////    public static void main(String[] args) throws BadPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException {
////       DiffieHellman test = new DiffieHellman();
////        test.givenString_whenEncrypt_thenSuccess();
////    }
//
//}

