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

public class QuerryMovies {

    private static String LOG_TAG = "QuerryUtils.class";

    /**
     * Query the USGS dataset and return a list of {} objects.
     */
    public static List<Movie> fetchData(String requestUrl) {
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
        List<Movie> Books = extractFeatureFromJson(jsonResponse);
        // Log.i(LOG_TAG,"Object Earquaquake is : "+ Books.get(1).getMagnitud());
        // Return the list of {@link Earthquake}s
        return Books;
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
    private static List<Movie> extractFeatureFromJson(String MovieJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(MovieJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding Books to
        List<Movie> movie = new ArrayList<>();

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



                int _id = currentObjectInJasonArray.getInt("id");
                String thumbmail = null;
                if (currentObjectInJasonArray.has("poster_path")) {

                        thumbmail = currentObjectInJasonArray.getString("poster_path");

                }
                Log.i(LOG_TAG, " LINK OF THE IMAGE IS---------------- : " + i + ":  " + thumbmail);
                ///////////////
                String title = null;
                if (currentObjectInJasonArray.has("title")) {
                    // Extract the value for the key called "infoLink"
                    title = currentObjectInJasonArray.getString("title");
                }
                //////////
                int rating = 0;
                if (currentObjectInJasonArray.has("vote_average")) {
                    rating = currentObjectInJasonArray.getInt("vote_average");
                }

                String date = null;
                if (currentObjectInJasonArray.has("release_date")) {
                    date =currentObjectInJasonArray.getString("release_date");
                }


                // Add the new {@link Earthquake} to the list of Books.
                String mOverview = null;
                if (currentObjectInJasonArray.has("overview")) {
                    mOverview = currentObjectInJasonArray.getString("overview");
                }

                Log.i(LOG_TAG, title + "------" + date + "-------" + rating + "--------" + thumbmail + "---- " + mOverview);
                movie.add(new Movie(_id, title, date, rating, thumbmail, -1, mOverview));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing  JSON results", e);
        }

        // Return the list of Books
        return movie;
    }
}
