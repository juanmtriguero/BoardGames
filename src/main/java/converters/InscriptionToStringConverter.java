package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Inscription;

@Component
@Transactional
public class InscriptionToStringConverter implements Converter<Inscription, String> {

	@Override
	public String convert(Inscription inscription) {
		String res;
		if (inscription==null) {
			res = null;
		} else {
			res = String.valueOf(inscription.getId());
		}
		return res;
	}

}
