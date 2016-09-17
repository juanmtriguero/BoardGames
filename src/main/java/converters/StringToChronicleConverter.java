package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ChronicleRepository;
import domain.Chronicle;

@Component
@Transactional
public class StringToChronicleConverter implements Converter<String, Chronicle> {
	
	@Autowired
	public ChronicleRepository chronicleRepository;

	@Override
	public Chronicle convert(String text) {
		Chronicle res;
		try {
			if (StringUtils.isEmpty(text)) {
				res = null;
			} else {
				int id = Integer.valueOf(text);
				res = chronicleRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
