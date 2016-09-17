package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.BoardGameRepository;

import domain.BoardGame;

@Component
@Transactional
public class StringToBoardGameConverter implements Converter<String, BoardGame> {
	
	@Autowired
	public BoardGameRepository boardGameRepository;

	@Override
	public BoardGame convert(String text) {
		BoardGame res;
		try {
			if (StringUtils.isEmpty(text)) {
				res = null;
			} else {
				int id = Integer.valueOf(text);
				res = boardGameRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
