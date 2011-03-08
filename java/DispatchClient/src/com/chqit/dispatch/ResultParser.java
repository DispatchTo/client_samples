package com.chqit.dispatch;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.stream.JsonReader;

public class ResultParser {
	public List<Result> readJsonStream(InputStream in) throws IOException {
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		return readResults(reader);
	}
	
	public List<Result> readResults(JsonReader reader) throws IOException {
		List<Result> results = null;
		
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("results")) {
				results = readResultsArray(reader);
			}
		}
		reader.endObject();
		
		return results;
	}
	
	public List<Result> readResultsArray(JsonReader reader) throws IOException {
		List<Result> results = new ArrayList<Result>();
		
		reader.beginArray();
		while (reader.hasNext()) {
			results.add(readResult(reader));
		}
		reader.endArray();
		
		return results;
	}
	
	public Result readResult(JsonReader reader) throws IOException {
		String email = null;
		List<Error> errors = null;
		Result.ResultEnum result = null;
		
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("email")) {
				email = reader.nextString();
			} else if (name.equals("result")) {
				String resultStr = reader.nextString();
				if (resultStr.equals("valid")) {
					result = Result.ResultEnum.VALID;
				} else {
					result = Result.ResultEnum.INVALID;
				}
			} else if (name.equals("errors")) {
				errors = readErrorsArray(reader);
			}
		}
		reader.endObject();
		
		return new Result(email, result, errors);
	}
	
	public List<Error> readErrorsArray(JsonReader reader) throws IOException {
		List<Error> errors = new ArrayList<Error>();
		
		reader.beginArray();
		while (reader.hasNext()) {
			errors.add(readError(reader));
		}
		reader.endArray();
		
		return errors;
	}
	
	public Error readError(JsonReader reader) throws IOException {
		int errorCode = 0;
		String errorMessage = null;
		
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("error_code")) {
				errorCode = reader.nextInt();
			} else if (name.equals("error_message")) {
				errorMessage = reader.nextString();
			}
		}
		reader.endObject();
		
		return new Error(errorCode, errorMessage);
	}
}
