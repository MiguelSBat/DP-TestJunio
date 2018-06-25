
package services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ConfigRepository;
import domain.Actor;
import domain.Administrator;
import domain.Config;

@Service
@Transactional
public class ConfigService {

	//Managed Repository ----
	@Autowired
	private ConfigRepository	configRepository;

	@Autowired
	private ActorService		actorService;


	//Constructors
	public ConfigService() {
		super();
	}

	public Config create() {
		Config result;

		result = new Config();

		return result;
	}

	public Collection<Config> findAll() {
		Collection<Config> result;

		result = this.configRepository.findAll();

		return result;
	}

	public void delete(final Config config) {

		this.configRepository.delete(config);

	}

	public Config save(final Config config) {
		Config result;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Administrator);
		Assert.isTrue(config.getId() != 0, "config.double");

		result = this.configRepository.save(config);
		return result;
	}
	public Config addTabooWord(final String tabooWord) {
		Config config;
		Config result;
		Set<String> tabooWords;

		config = this.findConfiguration();
		tabooWords = new HashSet<String>(config.getTabooWords());
		Assert.isTrue(tabooWord != null);
		tabooWords.add(tabooWord);
		config.setTabooWords(tabooWords);
		result = this.save(config);

		return result;
	}
	public Config removeTabooWord(final String tabooWord) {
		Config config;
		Config result;
		Collection<String> tabooWords;

		config = this.findConfiguration();
		tabooWords = config.getTabooWords();
		tabooWords.remove(tabooWord);
		config.setTabooWords(tabooWords);
		result = this.save(config);

		return result;
	}
	public Config findOne(final int configId) {
		Config result;

		result = this.configRepository.findOne(configId);
		Assert.notNull(result);

		return result;
	}

	public Boolean isTaboo(final String string) {
		Boolean result;
		Collection<String> tabooWords;

		tabooWords = this.findConfiguration().getTabooWords();
		result = false;
		for (final String tabooWord : tabooWords)
			if (string.contains(tabooWord))
				result = true;

		return result;
	}
	public Config findConfiguration() {
		Config result;
		result = this.configRepository.findAll().get(0);
		return result;
	}

	public void flush() {
		this.configRepository.flush();
	}

	public String generateTicker() {
		String result, month, year, day, letters;
		Config config;
		LocalDate confDate, todayLocalDate, today;
		Integer tickerNumber;

		config = this.findConfiguration();
		today = new LocalDate();

		year = String.valueOf(today.getYear() - 2000);
		if (today.getMonthOfYear() < 10)
			month = "0" + String.valueOf(today.getMonthOfYear());
		else
			month = String.valueOf(today.getMonthOfYear());
		if (today.getDayOfMonth() < 10)
			day = "0" + String.valueOf(today.getDayOfMonth());
		else
			day = String.valueOf(today.getDayOfMonth());

		//Generate letters
		confDate = new LocalDate(config.getCurrentDay());
		todayLocalDate = new LocalDate();

		if (confDate.equals(todayLocalDate)) {
			tickerNumber = config.getNextTicker();
			letters = this.toAlphabeticRadix(tickerNumber);
			config.setNextTicker(tickerNumber + 1);
		} else {
			config.setCurrentDay(today.toDate());
			config.setNextTicker(1);
			letters = this.toAlphabeticRadix(0);
		}

		result = year + month + day + "-" + letters;
		this.save(config);
		return result;
	}

	private String toAlphabeticRadix(final int num) {
		String s;

		final char[] str = Integer.toString(num, 26).toCharArray();
		for (int i = 0; i < str.length; i++)
			str[i] += str[i] > '9' ? 10 : 49;

		s = String.valueOf(str);
		while (s.length() < 4)
			s = 'A' + s;
		s = s.toUpperCase();
		return s;
	}
}
