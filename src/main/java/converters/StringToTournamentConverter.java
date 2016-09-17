package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.TournamentRepository;
import domain.Tournament;

@Component
@Transactional
public class StringToTournamentConverter implements Converter<String, Tournament> {
	
	@Autowired
	public TournamentRepository tournamentRepository;

	@Override
	public Tournament convert(String text) {
		Tournament res;
		try {
			if (StringUtils.isEmpty(text)) {
				res = null;
			} else {
				int id = Integer.valueOf(text);
				res = tournamentRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
