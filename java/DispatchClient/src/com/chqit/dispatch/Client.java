/**
 * 
 */
package com.chqit.dispatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author jd
 *
 */
public class Client {
	public static final String DISPATCH_URL = "http://v1.dispatch.to/";
	public static final String API_KEY = "0123456789abcdef";
	
	/**
	 * @throws IOException 
	 * @throws JSONException 
	 * 
	 */
	public static List<Result> performQuery(String[] emails) throws IOException {
		StringBuilder data = new StringBuilder();
		
		/* Build our POST data */
		for (String email : emails) {
			data.append("email=" + URLEncoder.encode(email, "UTF-8") + "&");
		}
		
		/* Remove trailing '&' */
		if (data.length() > 0) {
			data.deleteCharAt(data.length() - 1);
		}
		
		String postData = data.toString();
		
		URL dispatchUrl = new URL(Client.DISPATCH_URL);
		URLConnection dispatchConn = dispatchUrl.openConnection();
		dispatchConn.setDoOutput(true);
		
		OutputStreamWriter out = new OutputStreamWriter(dispatchConn.getOutputStream());

		out.write(postData);
		
		out.close();

		ResultParser jsonParser = new ResultParser();
		
		return jsonParser.readJsonStream(dispatchConn.getInputStream());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("usage: java -jar DispatchClient.jar <email> [email] ...");
			System.exit(1);
		}
		
		try {
			List<Result> results = Client.performQuery(args);
			
			for (Result result : results) {
				System.out.println(result.getEmail() + ": " + result.getResult().toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
