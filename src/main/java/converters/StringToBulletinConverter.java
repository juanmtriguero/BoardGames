package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.BulletinRepository;
import domain.Bulletin;

@Component
@Transactional
public class StringToBulletinConverter implements Converter<String, Bulletin> {
	
	@Autowired
	public BulletinRepository bulletinRepository;

	@Override
	public Bulletin convert(String text) {
		Bulletin res;
		try {
			if (StringUtils.isEmpty(text)) {
				res = null;
			} else {
				int id = Integer.valueOf(text);
				res = bulletinRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
