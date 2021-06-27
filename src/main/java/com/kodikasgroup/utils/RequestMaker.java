package com.kodikasgroup.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class RequestMaker {

	private static final String URL = "http://localhost:8080/";
	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final Logger logger = Logger.getLogger(RequestMaker.class.getName());

	private RequestMaker() {
	}

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

	public static String sendGET(String endpoint, Object payload) throws IOException {
		var jsonString = getJson(payload);
		HttpURLConnection con = getConnection(endpoint+URLEncoder.encode(jsonString, "UTF-8"), "GET");
		int responseCode = con.getResponseCode();
		logger.log(Level.ALL, () -> "GET Response Code :: " + responseCode + "\n endpoint :"+endpoint);
		return getResponse(con);
	}

	public static String sendPUT(String endpoint, Object payload) throws IOException {
		HttpURLConnection con = getConnection(endpoint, "PUT");
		if (payload != null) {
			var jsonString = getJson(payload);
			setPayload(jsonString, con);
		}
		int responseCode = con.getResponseCode();
		logger.log(Level.ALL, () -> "PUT Response Code :: " + responseCode);
		return getResponse(con);
	}

	public static String sendPUT(String endpoint) throws IOException {
		HttpURLConnection con = getConnection(endpoint, "PUT");
		int responseCode = con.getResponseCode();
		logger.log(Level.ALL, () -> "PUT Response Code :: " + responseCode);
		return getResponse(con);
	}

	public static String sendPOST(String endpoint, Object payload) throws IOException {
		HttpURLConnection con = getConnection(endpoint, "POST");
		var jsonString = getJson(payload);
		setPayload(jsonString, con);
		 System.out.println(jsonString);
		int responseCode = con.getResponseCode();
		logger.log(Level.ALL, () -> "POST Response Code :: " + responseCode);
		return getResponse(con);
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
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuilder response = new StringBuilder();

		while ((inputLine = bufferedReader.readLine()) != null) {
			response.append(inputLine);
		}
		bufferedReader.close();

		return response.toString();
	}

	//Converting the Object to JSONString
	private static String getJson(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}

	public static void initializeRequestMaker() {
		Handler handlerObj = new ConsoleHandler();
		handlerObj.setLevel(Level.ALL);
		logger.addHandler(handlerObj);
		logger.setLevel(Level.ALL);
		logger.setUseParentHandlers(false);
		objectMapper.registerModule(new JavaTimeModule());
	}
}
