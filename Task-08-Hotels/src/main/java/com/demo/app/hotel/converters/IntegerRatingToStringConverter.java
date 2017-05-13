package com.demo.app.hotel.converters;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;

/**
IntegerRatingConverter.java,
convert integer to string presentation
 */ 

@SuppressWarnings("serial")
public class IntegerRatingToStringConverter implements Converter<String, Integer> {

	@Override
	public Result<Integer> convertToModel(String value, ValueContext context) {
		if (value == null || value.length() == 0) {
			Result.ok(null);
		}
		try {
			return Result.ok(Integer.valueOf(value));
		} catch (NumberFormatException e) {
			return Result.ok(new Integer(0));
		}
	}

	@Override
	public String convertToPresentation(Integer value, ValueContext context) {
		if (value == null) {
			return "";
		}
		return String.valueOf(value);
	}
}
