package sk.tomashrdy.entity;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public interface HashPassword {

    //Metoda na zaöifrovanie hesla ( zaöifrovanie je nevratnÈ neskÙr budem pri porovn·vani öifrovaù rovnako aj zadanÈ heslo a porovn·vaù hesl· po öifrovanÌ )
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

    //V tejto metode len zariadim aby sa mi vracal po zaöifrovanÌ String ( obidve metÛdy generovala UI )
    static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
