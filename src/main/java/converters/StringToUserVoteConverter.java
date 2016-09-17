package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.UserVoteRepository;
import domain.UserVote;

@Component
@Transactional
public class StringToUserVoteConverter implements Converter<String, UserVote> {
	
	@Autowired
	public UserVoteRepository userVoteRepository;

	@Override
	public UserVote convert(String text) {
		UserVote res;
		try {
			if (StringUtils.isEmpty(text)) {
				res = null;
			} else {
				int id = Integer.valueOf(text);
				res = userVoteRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
