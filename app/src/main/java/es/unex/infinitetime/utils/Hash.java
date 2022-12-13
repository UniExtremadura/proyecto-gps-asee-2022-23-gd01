package es.unex.infinitetime.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class Hash {

    public final static String ALGORITHM = "SHA-256";
	
	public static final int SALT_LENGTH = 32;

    private MessageDigest hash;
    private SecureRandom random;

    public Hash(){
        try {
            hash = MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        random = new SecureRandom();
    }

    public byte[] getHash(byte[] salt, String password){
        hash.update(salt);
        hash.update(password.getBytes());
        return hash.digest();
    }

    public boolean compareHash(byte[] salt, String password, byte[] hashCode){
        byte[] resume = getHash(salt, password);
        if(resume == null) {
            return false;
        }
        return Arrays.equals(hashCode, resume);
    }
    
    public byte[] getSalt() {
    	byte[] salt = new byte[SALT_LENGTH];
    	random.nextBytes(salt);
    	return salt;
    }

    public static String arrayByteToString(byte[] array) {
        StringBuilder sb = new StringBuilder();
        for (byte b : array) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    public static byte[] stringToArrayByte(String string) {
    	byte[] array = new byte[string.length() / 2];
    	for (int i = 0; i < array.length; i++) {
    		int index = i * 2;
    		int j = Integer.parseInt(string.substring(index, index + 2), 16);
    		array[i] = (byte) j;
    	}
    	return array;
    }
}
