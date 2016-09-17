package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Business;

@Component
@Transactional
public class BusinessToStringConverter implements Converter<Business, String> {

	@Override
	public String convert(Business business) {
		String res;
		if (business==null) {
			res = null;
		} else {
			res = String.valueOf(business.getId());
		}
		return res;
	}

}
