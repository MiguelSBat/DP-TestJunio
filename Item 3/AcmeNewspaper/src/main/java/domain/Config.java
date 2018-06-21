
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Config extends DomainEntity {

	private Collection<String>	tabooWords;
	private Integer				nextTicker;
	private Date				currentDay;


	@NotNull
	@ElementCollection
	public Collection<String> getTabooWords() {
		return this.tabooWords;
	}

	public void setTabooWords(final Collection<String> tabooWords) {
		this.tabooWords = tabooWords;
	}

	@NotNull
	public Integer getNextTicker() {
		return this.nextTicker;
	}

	public void setNextTicker(final Integer nextTicker) {
		this.nextTicker = nextTicker;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getCurrentDay() {
		return this.currentDay;
	}

	public void setCurrentDay(final Date currentDay) {
		this.currentDay = currentDay;
	}

	//Relationships

}
