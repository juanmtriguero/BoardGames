package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.EventRepository;
import domain.Event;

@Component
@Transactional
public class StringToEventConverter implements Converter<String, Event> {
	
	@Autowired
	public EventRepository eventRepository;

	@Override
	public Event convert(String text) {
		Event res;
		try {
			if (StringUtils.isEmpty(text)) {
				res = null;
			} else {
				int id = Integer.valueOf(text);
				res = eventRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
