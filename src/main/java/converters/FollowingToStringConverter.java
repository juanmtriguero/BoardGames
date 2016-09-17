package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Following;

@Component
@Transactional
public class FollowingToStringConverter implements Converter<Following, String> {

	@Override
	public String convert(Following following) {
		String res;
		if (following==null) {
			res = null;
		} else {
			res = String.valueOf(following.getId());
		}
		return res;
	}

}
