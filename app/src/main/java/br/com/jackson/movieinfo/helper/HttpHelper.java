package br.com.jackson.movieinfo.helper;

import android.util.Log;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by jackson on 10/12/16.
 */

public final class HttpHelper {
    private HttpHelper() {
    }

    public static String doGet(String urlString) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();

        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException(String.format("Erro to request url %s", url));
        }

        InputStreamReader inputStreamReader = new InputStreamReader(con.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        try {
            StringBuffer response = new StringBuffer();

            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        } finally {
            closeQuietly(inputStreamReader);
            closeQuietly(bufferedReader);
        }
    }


    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Log.d(HttpHelper.class.getSimpleName(), "Erro to close a object", e);
            }
        }
    }
}