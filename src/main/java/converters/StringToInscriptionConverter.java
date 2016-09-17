package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.InscriptionRepository;
import domain.Inscription;

@Component
@Transactional
public class StringToInscriptionConverter implements Converter<String, Inscription> {
	
	@Autowired
	public InscriptionRepository inscriptionRepository;

	@Override
	public Inscription convert(String text) {
		Inscription res;
		try {
			if (StringUtils.isEmpty(text)) {
				res = null;
			} else {
				int id = Integer.valueOf(text);
				res = inscriptionRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
