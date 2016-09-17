package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.FollowingRepository;
import domain.Following;

@Component
@Transactional
public class StringToFollowingConverter implements Converter<String, Following> {
	
	@Autowired
	public FollowingRepository followingRepository;

	@Override
	public Following convert(String text) {
		Following res;
		try {
			if (StringUtils.isEmpty(text)) {
				res = null;
			} else {
				int id = Integer.valueOf(text);
				res = followingRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
