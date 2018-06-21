
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.CosaX;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CosaXTest extends AbstractTest {

	@Autowired
	private CosaXService	cosaXService;


	@Test
	public void createAndSaveDriver() {
		final Object[][] testingData = {
			{ // 
				"admin", "title", "text", 3, null, false, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.createAndSaveTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Integer) testingData[i][3], (Date) testingData[i][4], (Boolean) testingData[i][5], (Class<?>) testingData[i][6]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}
	protected void createAndSaveTemplate(final String admin, final String title, final String description, final Integer gauge, final Date publication, final Boolean draftMode, final Class<?> expected) {
		Class<?> caught;
		int newspaperId;
		CosaX cosaX;

		caught = null;
		try {
			this.authenticate(admin);
			newspaperId = super.getEntityId("newspaper1");
			cosaX = this.cosaXService.create(newspaperId);
			cosaX.setDescription(description);
			cosaX.setDraftMode(draftMode);
			cosaX.setGauge(gauge);
			cosaX.setPublicationDate(publication);
			cosaX.setTitle(title);
			cosaX.setTicker("000000-AAAA");
			cosaX = this.cosaXService.save(cosaX);

			this.cosaXService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
