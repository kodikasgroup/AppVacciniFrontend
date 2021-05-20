package com.kodikasgroup.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestMaker {

    private static final String URL = "http://localhost:8080/";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = Logger.getLogger(RequestMaker.class.getName());

    private RequestMaker(){}

    public static void sendDELETE(String endpoint) throws IOException {
        HttpURLConnection con = getConnection(endpoint, "DELETE");

        int responseCode = con.getResponseCode();
        logger.log(Level.ALL, () -> "DELETE Response Code :: " + responseCode);
    }

    public static String sendGET(String endpoint) throws IOException {
        HttpURLConnection con = getConnection(endpoint, "GET");

        int responseCode = con.getResponseCode();
        logger.log(Level.ALL, () -> "GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            return getResponse(con);
        } else {
            logger.log(Level.ALL, () -> "GET request not worked, endpoint: " + endpoint);
            return "{}";
        }
    }

    public static String sendPUT(String endpoint, Object payload) throws IOException {
        HttpURLConnection con = getConnection(endpoint, "PUT");
        if (payload != null) {
            var jsonString = getJson(payload);
            setPayload(jsonString, con);
        }
        int responseCode = con.getResponseCode();
        logger.log(Level.ALL, () -> "PUT Response Code :: " + responseCode);
        return con.getResponseMessage();
    }

    public static void sendPOST(String endpoint, Object payload) throws IOException {
        HttpURLConnection con = getConnection(endpoint, "POST");
        var jsonString = getJson(payload);
        // remove ids
        jsonString = jsonString.replaceAll("\"id\":\\w+,", "");
        setPayload(jsonString, con);

        int responseCode = con.getResponseCode();
        logger.log(Level.ALL, () -> "POST Response Code :: " + responseCode);
    }

    private static void setPayload(String payload, HttpURLConnection con) throws IOException {
        con.setDoOutput(true);
        OutputStream outputStream = con.getOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        outputStreamWriter.write(payload);
        outputStreamWriter.flush();
        outputStreamWriter.close();
        outputStream.close();
    }

    private static HttpURLConnection getConnection(String endpoint, String method) throws IOException {
        URL url = new URL(URL + endpoint);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method);
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type", "application/json");
        return con;
    }

    private static String getResponse(HttpURLConnection con) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    //Converting the Object to JSONString
    private static String getJson(Object object) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(object);
        var nameValuePattern = "\\{\"name\":\"\",\"value\":";
        var validPattern = ",\"valid\":\\w+}";
        var helperPattern = "\"helper\":\\{\"observable\":\\{}}},";
        json = json.replace(nameValuePattern, "");
        json = json.replaceAll(validPattern, "");
        json = json.replace(helperPattern, "");
        return json;
    }

    public static void initializeRequestMaker() {
        objectMapper.registerModule(new JavaTimeModule());
    }
}
