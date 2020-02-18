package com.dairyfarm.helper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class DateHandler extends StdDeserializer<Date> {
	
	private static final long serialVersionUID = 1L;

	public DateHandler() {
		this(null); 
	}
	
	public DateHandler(Class<?> clazz) { 
		super(clazz); 
	}

	@Override
	public Date deserialize(JsonParser jsonparser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		String date = jsonparser.getText();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); 
			return sdf.parse(date); 
		} catch (Exception e) { 
			return null; 
		}
	}
}