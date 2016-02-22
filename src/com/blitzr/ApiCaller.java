package com.blitzr;

import com.blitzr.exceptions.BlitzrException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApiCaller {
    /**
     * @param in : buffer with the php result
     * @param bufSize : size of the buffer
     * @return : the string corresponding to the buffer
     */
    public static String InputStreamToString (InputStream in, int bufSize) {
        final StringBuilder out = new StringBuilder();
        final byte[] buffer = new byte[bufSize];
        try {
            for (int ctr; (ctr = in.read(buffer)) != -1;) {
                out.append(new String(buffer, 0, ctr));
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot convert stream to string", e);
        }
        // On retourne la chaine contenant les donnees de l'InputStream
        return out.toString();
    }

    /**
     * @param in : buffer with the php result
     * @return : the string corresponding to the buffer
     */
    public static String InputStreamToString (InputStream in) {
        // On appelle la methode precedente avec une taille de buffer par defaut
        return InputStreamToString(in, 1024);
    }

    public static void checkStatusCode (int statusCode) {
        if (statusCode > 400) {
            throw new BlitzrException(statusCode);
        }
    }

    public static void checkStatusCode (int statusCode, String message) {
        if (statusCode > 400) {
            throw new BlitzrException(statusCode, message);
        }
    }

    private static String parametersFromMap(HashMap<String, Object> map){
        ArrayList<String> couples = new ArrayList<String>();

        for(HashMap.Entry<String, Object> entry: map.entrySet()){

            try {
                if (entry.getValue() != null) {
                    couples.add(String.format("%s=%s", URLEncoder.encode(entry.getKey(), "UTF-8"), URLEncoder.encode(entry.getValue().toString(), "UTF-8")));
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


        }
        couples.add(String.format("%s=%s", "key", Client.getApiKey()));
        return String.join("&", couples);
    }

    public static <T> T getApi (String urlStr, Class<T> T, HashMap<String, Object> params) {
        urlStr = String.format("%s%s?%s", Client.getApiUrl(), urlStr, ApiCaller.parametersFromMap(params));
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        T object;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            ApiCaller.checkStatusCode(connection.getResponseCode());
            InputStream inputStream = connection.getInputStream();
            String result = ApiCaller.InputStreamToString(inputStream);
            object = mapper.readValue(result, T);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof UnknownHostException) {
                throw new BlitzrException(600);
            } else {
                throw new BlitzrException(601);
            }
        }
        return object;
    }

    public static <T> List<T> getApiList (String urlStr, Class<T> T, HashMap<String, Object> params) {
        urlStr = String.format("%s%s?%s", Client.getApiUrl(), urlStr, ApiCaller.parametersFromMap(params));
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<T> object = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            ApiCaller.checkStatusCode(connection.getResponseCode());
            InputStream inputStream = connection.getInputStream();
            String result = ApiCaller.InputStreamToString(inputStream);
            object = mapper.readValue(result, mapper.getTypeFactory().constructCollectionType(List.class, T));
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof UnknownHostException) {
                throw new BlitzrException(600);
            } else {
                throw new BlitzrException(601);
            }
        }
        return object;
    }

    public static <T, V> HashMap<T, V> getApiHashMap (String urlStr, Class<T> T, Class<V> V, HashMap<String, Object> params) {
        urlStr = String.format("%s%s?%s", Client.getApiUrl(), urlStr, ApiCaller.parametersFromMap(params));
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        HashMap<T, V> object = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            ApiCaller.checkStatusCode(connection.getResponseCode());
            InputStream inputStream = connection.getInputStream();
            String result = ApiCaller.InputStreamToString(inputStream);
            object = mapper.readValue(result, mapper.getTypeFactory().constructMapType(HashMap.class, T, V));
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof UnknownHostException) {
                throw new BlitzrException(600);
            } else {
                throw new BlitzrException(601);
            }
        }
        return object;
    }

    public static <T, V> T getApiParametricType (String urlStr, Class<T> T, Class<V> V, HashMap<String, Object> params) {
        urlStr = String.format("%s%s?%s", Client.getApiUrl(), urlStr, ApiCaller.parametersFromMap(params));
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        T object = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            ApiCaller.checkStatusCode(connection.getResponseCode());
            InputStream inputStream = connection.getInputStream();
            String result = ApiCaller.InputStreamToString(inputStream);
            object = mapper.readValue(result, mapper.getTypeFactory().constructParametricType(T, V));
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof UnknownHostException) {
                throw new BlitzrException(600);
            } else {
                throw new BlitzrException(601);
            }
        }
        return object;
    }
}
