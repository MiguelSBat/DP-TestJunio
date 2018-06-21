
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.CosaX;

@Component
@Transactional
public class CosaXToStringConverter implements Converter<CosaX, String> {

	@Override
	public String convert(final CosaX cosaX) {
		String result;

		if (cosaX == null)
			result = null;
		else
			result = String.valueOf(cosaX.getId());

		return result;
	}

}
