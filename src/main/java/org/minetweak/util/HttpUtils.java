package org.minetweak.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 * Methods that have to do with HTTP
 */
@SuppressWarnings("deprecation")
public class HttpUtils {

    /**
     * Downloads a file to the specified location
     *
     * @param file file to download to
     * @param url  url to download
     * @return if the file was downloaded
     */
    public static boolean downloadFile(File file, String url) {
        if (file.exists()) {
            if (!file.delete()) {
                return false;
            }
        }
        try {
            FileUtils.copyURLToFile(new URL(url), file);
        } catch (IOException e) {
            return false;
        }
        return file.exists();
    }

    /**
     * Downloads a file to the specified location
     *
     * @param path path to download to
     * @param url  url to download
     * @return if the file was downloaded
     */
    public static boolean downloadFile(String path, String url) {
        return downloadFile(new File(path), url);
    }

    /**
     * Gets the Content of the URL into a String
     *
     * @param url url to parse
     * @return URL Content
     */
    public static String getText(String url) {
        try {
            return IOUtils.toString(URI.create(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}