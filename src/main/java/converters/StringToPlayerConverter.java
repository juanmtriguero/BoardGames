package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.PlayerRepository;
import domain.Player;

@Component
@Transactional
public class StringToPlayerConverter implements Converter<String, Player> {
	
	@Autowired
	public PlayerRepository playerRepository;

	@Override
	public Player convert(String text) {
		Player res;
		try {
			if (StringUtils.isEmpty(text)) {
				res = null;
			} else {
				int id = Integer.valueOf(text);
				res = playerRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
