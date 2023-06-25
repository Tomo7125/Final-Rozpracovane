package sk.tomashrdy.entity;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public interface HashPassword {

    //Metoda na za�ifrovanie hesla ( za�ifrovanie je nevratn� nesk�r budem pri porovn�vani �ifrova� rovnako aj zadan� heslo a porovn�va� hesl� po �ifrovan� )
    static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    //V tejto metode len zariadim aby sa mi vracal po za�ifrovan� String ( obidve met�dy generovala UI )
    static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
