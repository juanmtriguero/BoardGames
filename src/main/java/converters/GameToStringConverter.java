package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Game;

@Component
@Transactional
public class GameToStringConverter implements Converter<Game, String> {

	@Override
	public String convert(Game game) {
		String res;
		if (game==null) {
			res = null;
		} else {
			res = String.valueOf(game.getId());
		}
		return res;
	}

}
