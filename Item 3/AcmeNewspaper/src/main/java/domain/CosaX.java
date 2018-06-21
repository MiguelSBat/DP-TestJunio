
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "containsTaboo")
})
public class CosaX extends DomainEntity {

	private String	ticket;
	private String	title;
	private String	description;
	private Integer	gauge;
	private Date	publicationDate;


	@NotBlank
	public String getTicket() {
		return this.ticket;
	}

	public void setTicket(final String ticket) {
		this.ticket = ticket;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Range(min = 1, max = 3)
	public Integer getGauge() {
		return this.gauge;
	}

	public void setGauge(final Integer gauge) {
		this.gauge = gauge;
	}

	public Date getPublicationDate() {
		return this.publicationDate;
	}

	public void setPublicationDate(final Date publicationDate) {
		this.publicationDate = publicationDate;
	}


	//Relationships

	private Administrator	administrator;
	private Newspaper		newspaper;


	@ManyToOne(optional = false)
	public Administrator getAdministrator() {
		return this.administrator;
	}

	public void setAdministrator(final Administrator administrator) {
		this.administrator = administrator;
	}

	@ManyToOne(optional = false)
	public Newspaper getNewspaper() {
		return this.newspaper;
	}

	public void setNewspaper(final Newspaper newspaper) {
		this.newspaper = newspaper;
	}

}
