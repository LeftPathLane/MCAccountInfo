package net.leftpath.lane.oginfo;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class OGInfo {
	public static void main(String[] args) throws IOException {
		if (args.length >= 2) {
			String accesstoken = getAccessToken(args[0], args[1]);
			if (accesstoken != null) {
				getAccInfo(accesstoken);
			} else {
				System.out.println("ERR: Incorrect username/password");
			}

		} else {
			System.out.println("Usage: java -jar OGInfo.jar USERNAME PASSWORD");
		}
	}

	public static String getAccessToken(String username, String password) throws IOException {
		URL auth = new URL("https://authserver.mojang.com/authenticate");
		String accessToken = null;

		HttpURLConnection con = (HttpURLConnection) auth.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; charset=utf-8");

		String payload = "{\"agent\":{\"name\":\"Minecraft\",\"version\":1},\"username\":\""+username+"\",\"password\":\""+password+
				"\",\"clientToken\":\""+UUID.randomUUID().toString()+"\",\"requestUser\": true}";
		byte[] bytes = payload.getBytes("UTF-8");
		con.setRequestProperty("Content-Length", String.valueOf(bytes.length));
		con.setDoInput(true);
		con.setDoOutput(true);
		OutputStream out = con.getOutputStream();
		out.write(bytes);
		out.close();
		InputStream in;
		boolean err = false;
		if (con.getResponseCode() == 200) {
			in = con.getInputStream();
		} else {
			in = con.getErrorStream();
			err = true;
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder builder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			builder.append(line).append("\n");
		}
		if (err) {
			System.out.println(builder.toString());
			return null;
		}
		accessToken = new JSONObject(builder.toString()).getString("accessToken");
		return accessToken;
	}

	public static void getAccInfo(String accessToken) throws IOException {
		URL info = new URL("https://api.mojang.com/user");

		HttpURLConnection con = (HttpURLConnection) info.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Authorization", "Bearer " + accessToken);

		InputStream in;
		if (con.getResponseCode() == 200) {
			in = con.getInputStream();
		} else {
			in = con.getErrorStream();
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder builder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			builder.append(line).append("\n");
		}
		System.out.println(new JSONObject(builder.toString()).toString(2));
	}
}
