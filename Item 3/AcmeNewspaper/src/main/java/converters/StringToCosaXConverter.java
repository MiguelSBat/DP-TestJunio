
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.CosaXRepository;
import domain.CosaX;

@Component
@Transactional
public class StringToCosaXConverter implements Converter<String, CosaX> {

	@Autowired
	private CosaXRepository	cosaXRepository;


	@Override
	public CosaX convert(final String text) {
		CosaX result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.cosaXRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
