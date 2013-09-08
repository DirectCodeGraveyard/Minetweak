package org.minetweak.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class HashUtils {
    /**
     * Does a MD5 Checksum Check on the File
     *
     * @param file             file
     * @param expectedChecksum the checksum the file should have
     * @return if the file is valid
     */
    public static boolean validateMD5(File file, String expectedChecksum) {
        try {
            return DigestUtils.md5Hex(new FileInputStream(file)).equals(expectedChecksum);
        } catch (IOException e) {
            return false;
        }
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
