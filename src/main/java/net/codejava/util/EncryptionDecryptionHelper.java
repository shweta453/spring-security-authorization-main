package net.codejava.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;

public class EncryptionDecryptionHelper {

    public static String KEY;
    public static String INIT_VECTOR;
    private static int iterations = 65536;
    private static int keySize = 256;
    private static final String characterEncoding = "UTF-8";
    private static final String cipherTransformation = "AES/CBC/PKCS5Padding";
    private static final String aesEncryptionAlgorithm = "AES";
    private static final String secretKeyFactoryMode = "PBKDF2WithHmacSHA256";

    public static String encrypt256(String key, String initVector, String value) {
        try {
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance(secretKeyFactoryMode);
            KeySpec spec = new PBEKeySpec(key.toCharArray(), initVector.getBytes(), iterations, keySize);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), aesEncryptionAlgorithm);

            Cipher cipher = Cipher.getInstance(cipherTransformation);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            return java.util.Base64.getMimeEncoder().encodeToString(cipher.doFinal(value.getBytes(characterEncoding)));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt256(String key, String initVector, String value) {
        try {
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance(secretKeyFactoryMode);
            KeySpec spec = new PBEKeySpec(key.toCharArray(), initVector.getBytes(), iterations, keySize);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), aesEncryptionAlgorithm);

            Cipher cipher = Cipher.getInstance(cipherTransformation);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            return new String(cipher.doFinal(java.util.Base64.getMimeDecoder().decode(value)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    public static String encryptUtilities(String enc) {
        enc = enc.replace("<", "-3C");
        enc = enc.replace(">", "-3E");
        enc = enc.replace("#", "-23");
        enc = enc.replace("%", "-25");
        enc = enc.replace("{", "-7B");
        enc = enc.replace("}", "-7D");
        enc = enc.replace("|", "-7C");
        enc = enc.replace("\\", "-5C");
        enc = enc.replace("^", "-5E");
        enc = enc.replace("~", "-7E");
        enc = enc.replace("[", "-5B");
        enc = enc.replace("]", "-5D");
        enc = enc.replace("'", "-60");
        enc = enc.replace(";", "-3B");
        enc = enc.replace("/", "-2F");
        enc = enc.replace("?", "-3F");
        enc = enc.replace(":", "-3A");
        enc = enc.replace("@", "-40");
        enc = enc.replace("=", "-3D");
        enc = enc.replace("&", "-26");
        enc = enc.replace("$", "-24");
        enc = enc.replace("+", "-2B");
        enc = enc.replace("\"", "-22");
        enc = enc.replace(" ", "-20");

        return enc;
    }

    public static String decryptUtilities(String enc) {
        enc = enc.replace("-3C", "<");
        enc = enc.replace("-3E", ">");
        enc = enc.replace("-23", "#");
        enc = enc.replace("-25", "%");
        enc = enc.replace("-7B", "{");
        enc = enc.replace("-7D", "}");
        enc = enc.replace("-7C", "|");
        enc = enc.replace("-5C", "\\");
        enc = enc.replace("-5E", "^");
        enc = enc.replace("-7E", "~");
        enc = enc.replace("-5B", "[");
        enc = enc.replace("-5D", "]");
        enc = enc.replace("-60", "'");
        enc = enc.replace("-3B", ";");
        enc = enc.replace("-2F", "/");
        enc = enc.replace("-3F", "?");
        enc = enc.replace("-3A", ":");
        enc = enc.replace("-40", "@");
        enc = enc.replace("-3D", "=");
        enc = enc.replace("-26", "&");
        enc = enc.replace("-24", "$");
        enc = enc.replace("-2B", "+");
        enc = enc.replace("-22", "\"");
        enc = enc.replace("-20", " ");

        return enc;
    }

    public static void main(String[] args) {
        String key = "das$_prod";
        String initVector = "das$_prod";

        try {
            String encruptId1 = encryptUtilities(encrypt256(key, initVector, "DASDEV"));
            System.out.println("encrypted password == " + (encruptId1));
            //System.out.println("decrypt256 password == " + decrypt256(key, initVector, decryptUtilities("F4owdBYgCXHYmkx6hKSMIQ-3D-3D")));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
