package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Player;

@Component
@Transactional
public class PlayerToStringConverter implements Converter<Player, String> {

	@Override
	public String convert(Player player) {
		String res;
		if (player==null) {
			res = null;
		} else {
			res = String.valueOf(player.getId());
		}
		return res;
	}

}
