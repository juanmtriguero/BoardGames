package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Chronicle;

@Component
@Transactional
public class ChronicleToStringConverter implements Converter<Chronicle, String> {

	@Override
	public String convert(Chronicle chronicle) {
		String res;
		if (chronicle==null) {
			res = null;
		} else {
			res = String.valueOf(chronicle.getId());
		}
		return res;
	}

}
