package syed.noteapp.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by shoiab on 2017-10-21.
 */

public class NetworkUtil {
    private static final String BASE_URL = "https://timesheet-1172.appspot.com/695d85c4/notes";
    private static final String LOG_TAG = NetworkUtil.class.getSimpleName();

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {

            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static String sendNewNoteDatatoServer(URL url, String title, String desc, String request) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod(request);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
            JSONObject postDataParams = new JSONObject();
            try {
                postDataParams.put("title", title);
                postDataParams.put("description", desc);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            writer.write(postDataParams.toString());
            writer.flush();
            int responsecode = urlConnection.getResponseCode();
            if (responsecode == HttpsURLConnection.HTTP_OK) {

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    return scanner.next();
                } else {
                    return null;
                }
            } else {
                return new String("false : " + responsecode);
            }
        } finally {
            urlConnection.disconnect();
        }

    }


    public static URL createUrl(String id) {
        String stringUrl = BASE_URL + id;
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    public static int deleteDatatoServer(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        int responsecode = 0;
        try {

            urlConnection.setRequestMethod("DELETE");

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            responsecode = urlConnection.getResponseCode();

        } finally {
            urlConnection.disconnect();
        }
        return responsecode;
    }
}
