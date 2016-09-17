package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.BusinessRepository;
import domain.Business;

@Component
@Transactional
public class StringToBusinessConverter implements Converter<String, Business> {
	
	@Autowired
	public BusinessRepository businessRepository;

	@Override
	public Business convert(String text) {
		Business res;
		try {
			if (StringUtils.isEmpty(text)) {
				res = null;
			} else {
				int id = Integer.valueOf(text);
				res = businessRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
