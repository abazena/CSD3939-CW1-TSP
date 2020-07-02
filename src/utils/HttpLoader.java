package utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class HttpLoader {


    public HttpLoader() {

    }

    public String makeRequest(String url, String charset) {
        URLConnection connection = null;

        try {
            connection = (new URL(url)).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            InputStream response = (new URL(url)).openStream();
            Scanner scanner = new Scanner(response);
            Throwable var4 = null;

            String var6;
            try {
                String responseBody = scanner.useDelimiter("\\A").next();
                System.out.println("Data-set loaded");
                var6 = responseBody;
            } catch (Throwable var16) {
                var4 = var16;
                throw var16;
            } finally {
                if (scanner != null) {
                    if (var4 != null) {
                        try {
                            scanner.close();
                        } catch (Throwable var15) {
                            var4.addSuppressed(var15);
                        }
                    } else {
                        scanner.close();
                    }
                }

            }

            return var6;
        } catch (IOException var18) {
            var18.printStackTrace();
            return null;
        }
    }
}
