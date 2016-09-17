package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.CategorizationRepository;
import domain.Categorization;

@Component
@Transactional
public class StringToCategorizationConverter implements Converter<String, Categorization> {
	
	@Autowired
	public CategorizationRepository categorizationRepository;

	@Override
	public Categorization convert(String text) {
		Categorization res;
		try {
			if (StringUtils.isEmpty(text)) {
				res = null;
			} else {
				int id = Integer.valueOf(text);
				res = categorizationRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
