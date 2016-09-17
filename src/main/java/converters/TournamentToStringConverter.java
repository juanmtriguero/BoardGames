package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Tournament;

@Component
@Transactional
public class TournamentToStringConverter implements Converter<Tournament, String> {

	@Override
	public String convert(Tournament tournament) {
		String res;
		if (tournament==null) {
			res = null;
		} else {
			res = String.valueOf(tournament.getId());
		}
		return res;
	}

}
