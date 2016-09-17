package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Promotion;

@Component
@Transactional
public class PromotionToStringConverter implements Converter<Promotion, String> {

	@Override
	public String convert(Promotion promotion) {
		String res;
		if (promotion==null) {
			res = null;
		} else {
			res = String.valueOf(promotion.getId());
		}
		return res;
	}

}
