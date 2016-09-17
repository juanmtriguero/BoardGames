package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.UserVote;

@Component
@Transactional
public class UserVoteToStringConverter implements Converter<UserVote, String> {

	@Override
	public String convert(UserVote userVote) {
		String res;
		if (userVote==null) {
			res = null;
		} else {
			res = String.valueOf(userVote.getId());
		}
		return res;
	}

}
