
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CosaXRepository;
import domain.Actor;
import domain.Administrator;
import domain.CosaX;
import domain.Newspaper;

@Service
@Transactional
public class CosaXService {

	//Managed Repository ----
	@Autowired
	private CosaXRepository		cosaXRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ConfigService		configService;

	@Autowired
	private NewspaperService	newspaperService;


	//Constructors
	public CosaXService() {
		super();
	}

	public CosaX create(final int newspaperId) {
		CosaX result;
		Actor actor;
		Newspaper newspaper;

		actor = this.actorService.findByPrincipal();
		newspaper = this.newspaperService.findOne(newspaperId);
		result = new CosaX();
		result.setAdministrator((Administrator) actor);
		result.setNewspaper(newspaper);

		return result;
	}

	public Collection<CosaX> findAll() {
		Collection<CosaX> result;

		result = this.cosaXRepository.findAll();

		return result;
	}

	public void delete(final CosaX cosaX) {
		Actor actor;

		actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor instanceof Administrator);
		Assert.isTrue(cosaX.getAdministrator().getId() == actor.getId());
		Assert.isTrue(cosaX.isDraftMode());

		this.cosaXRepository.delete(cosaX);

	}

	public CosaX save(final CosaX cosaX) {
		CosaX result;
		Actor actor;
		Date date;

		date = new Date();
		actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor instanceof Administrator);
		Assert.isTrue(cosaX.getPublicationDate() == null || cosaX.getPublicationDate().after(date));
		if (cosaX.getId() != 0) {
			Assert.isTrue(cosaX.getAdministrator().getId() == actor.getId());
			Assert.isTrue(cosaX.isDraftMode());
		}
		cosaX.setTicker(this.configService.generateTicker());
		result = this.cosaXRepository.save(cosaX);

		return result;
	}

	public CosaX findOne(final int cosaX) {
		CosaX result;

		result = this.cosaXRepository.findOne(cosaX);

		return result;
	}

	public void flush() {
		this.cosaXRepository.flush();
	}

	public Collection<CosaX> findCosasX(final int newspaperId) {
		return this.cosaXRepository.findCosasX(newspaperId);
	}
}
