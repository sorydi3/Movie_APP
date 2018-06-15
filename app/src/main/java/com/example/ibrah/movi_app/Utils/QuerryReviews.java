package com.example.ibrah.movi_app.Utils;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QuerryReviews {
    private static String LOG_TAG = "QuerryReviews.class";

    /**
     * Query the USGS dataset and return a list of {} objects.
     */
    public static List<Review> fetchData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);
        Log.i(LOG_TAG, " THE URL IS : " + url);
        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<Review> reviews = extractFeatureFromJson(jsonResponse);
        // Log.i(LOG_TAG,"Object Earquaquake is : "+ Books.get(1).getMagnitud());
        // Return the list of {@link Earthquake}s
        return reviews;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                Log.i(LOG_TAG, "show input stream" + inputStream);
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving  JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<Review> extractFeatureFromJson(String MovieJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(MovieJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding Books to
        List<Review> reviews= new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(MovieJSON);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or Books).
            JSONArray baseJsonResponseJSONArray = baseJsonResponse.getJSONArray("results");

            // For each earthquake in the earthquakeArray, create an {@link Earthquake} object
            for (int i = 0; i < baseJsonResponseJSONArray.length(); i++) {
                Log.i(LOG_TAG, " INSIDE THE FOIR LOOP : " + i);

                // Get a single book at position i within the list of Books
                JSONObject currentObjectInJasonArray = baseJsonResponseJSONArray.getJSONObject(i);



                String autor = currentObjectInJasonArray.getString("author");
                String url = null;
                if (currentObjectInJasonArray.has("url")) {

                    url = currentObjectInJasonArray.getString("url");

                }
                Log.i(LOG_TAG, " LINK OF THE IMAGE IS---------------- : " + i + ":  " + url);
                ///////////////
                String content = null;
                if (currentObjectInJasonArray.has("content")) {
                    // Extract the value for the key called "infoLink"
                   content = currentObjectInJasonArray.getString("content");
                }

                // Add the new {@link Earthquake} to the list of Books.
                Log.i(LOG_TAG, autor + "" + url + "" + content);

                reviews.add(new Review(autor,content,url));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing  JSON results", e);
        }

        // Return the list of Books
        return reviews;
    }
}
