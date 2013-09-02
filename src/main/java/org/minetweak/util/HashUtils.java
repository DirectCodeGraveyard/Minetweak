package org.minetweak.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class HashUtils {
    public static String getMD5(File file) {
        DigestInputStream input;
        String md5;
        try {
            input = new DigestInputStream(new FileInputStream(file), MessageDigest.getInstance("MD5"));
            while (input.read() != -1) ;
            md5 = byteArray2Hex(input.getMessageDigest().digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return md5;
    }

    private static String byteArray2Hex(byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    /**
     * Does a SHA1 Checksum Check on the File
     *
     * @param file             file
     * @param expectedChecksum the checksum the file should have
     * @return if the file is valid
     */
    public static boolean validateSHA1(File file, String expectedChecksum) {
        try {
            return DigestUtils.sha1Hex(new FileInputStream(file)).equals(expectedChecksum);
        } catch (IOException e) {
            return false;
        }
    }
}
