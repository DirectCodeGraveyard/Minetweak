package org.minetweak.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Methods that have to do with HTTP
 */
public class HttpUtils {

    /**
     * Downloads a file to a path
     *
     * @param path the path to the file
     * @param url  url to download
     */
    public static boolean downloadFile(String path, String url) {
        File file = new File(path);
        try {
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    return false;
                }
            } else {
                if (!file.delete()) {
                    return false;
                }
            }
            PrintStream out = new PrintStream(file);
            URL urlC = new URL(url);
            URLConnection connection = urlC.openConnection();
            connection.setReadTimeout(1);
            connection.setUseCaches(false);
            connection.setConnectTimeout(1);
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                out.println(line);
            }
            reader.close();
            out.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
