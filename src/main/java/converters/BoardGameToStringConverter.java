package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.BoardGame;

@Component
@Transactional
public class BoardGameToStringConverter implements Converter<BoardGame, String> {

	@Override
	public String convert(BoardGame boardGame) {
		String res;
		if (boardGame==null) {
			res = null;
		} else {
			res = String.valueOf(boardGame.getId());
		}
		return res;
	}

}
