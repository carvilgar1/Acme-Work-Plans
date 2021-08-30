package acme.utilities;

import org.springframework.core.convert.converter.Converter;

import acme.datatypes.WorkLoad;


public class WorkLoadToStringConverter implements Converter<WorkLoad, String> {

	@Override
	public String convert(final WorkLoad source) {
		return source.toString();
	}

}
