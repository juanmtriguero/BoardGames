package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Categorization;

@Component
@Transactional
public class CategorizationToStringConverter implements Converter<Categorization, String> {

	@Override
	public String convert(Categorization categorization) {
		String res;
		if (categorization==null) {
			res = null;
		} else {
			res = String.valueOf(categorization.getId());
		}
		return res;
	}

}
