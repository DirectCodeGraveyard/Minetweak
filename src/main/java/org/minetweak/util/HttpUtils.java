package org.minetweak.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Methods that have to do with HTTP
 */
public class HttpUtils {

    /**
     * Downloads a file to a path
     *
     * @param file file to download to
     * @param url  url to download
     */
    public static boolean downloadFile(File file, String url) {
        if (file.exists()) {
            file.delete();
        }
        try {
            FileUtils.copyURLToFile(new URL(url), file);
        } catch (IOException e) {
            return false;
        }
        return file.exists();
    }

    public static boolean downloadFile(String path, String url) {
        return downloadFile(new File(path), url);
    }

}