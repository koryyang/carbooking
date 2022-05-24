package com.koryyang.carbooking.utils;

import com.koryyang.carbooking.exception.SystemException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.security.*;

/**
 * @author yanglingyu
 * @date 2022/5/23
 */
@Slf4j
public class PasswordUtil {

    /**
     * private key of rsa
     */
    private static final PrivateKey PRIVATE_KEY;

    /**
     * public key of rsa
     */
    private static final PublicKey PUBLIC_KEY;

    static {
        KeyPairGenerator kpGen = null;
        try {
            kpGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert kpGen != null;
        kpGen.initialize(1024);
        KeyPair kp = kpGen.generateKeyPair();
        PRIVATE_KEY = kp.getPrivate();
        PUBLIC_KEY = kp.getPublic();
    }

    /**
     * get rsa public key to encrypt password
     * @return rsa public key
     */
    public static String getPublicKey() {
        byte[] encoded = PUBLIC_KEY.getEncoded();
        return Base64Utils.encodeToString(encoded);
    }

    /**
     * RSA decrypted
     * @param encryptedPassword encrypted password
     * @return decrypted passowrd
     */
    @SneakyThrows
    public static String rsaDecrypted(String encryptedPassword) {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, PRIVATE_KEY);
        try {
            byte[] encryptedPasswordBytes = Base64Utils.decodeFromString(encryptedPassword);
            byte[] decryptedPasswordBytes = cipher.doFinal(encryptedPasswordBytes);
            return new String(decryptedPasswordBytes);
        } catch (Exception e) {
            throw new SystemException("password invalid");
        }
    }

    /**
     * hash
     * @param password password
     * @return password after hash
     */
    public static String hashPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    /**
     * check password
     * @param encryptedPassword encrypted password
     * @param databasePassword database password
     * @return check if password match
     */
    public static boolean checkPassword(String encryptedPassword, String databasePassword) {
        // step1:decrypt password
        String decryptedPassword = rsaDecrypted(encryptedPassword);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // step2:check if password match
        return encoder.matches(decryptedPassword, databasePassword);
    }

}
