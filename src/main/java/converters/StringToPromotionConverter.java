package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.PromotionRepository;
import domain.Promotion;

@Component
@Transactional
public class StringToPromotionConverter implements Converter<String, Promotion> {
	
	@Autowired
	public PromotionRepository promotionRepository;

	@Override
	public Promotion convert(String text) {
		Promotion res;
		try {
			if (StringUtils.isEmpty(text)) {
				res = null;
			} else {
				int id = Integer.valueOf(text);
				res = promotionRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
