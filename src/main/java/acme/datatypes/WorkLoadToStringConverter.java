package acme.datatypes;

import org.springframework.core.convert.converter.Converter;


public class WorkLoadToStringConverter implements Converter<WorkLoad, String> {

	@Override
	public String convert(final WorkLoad source) {
		return source.toString();
	}

}
