
package services;

import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

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

	@Autowired
	private ConfigService	configService;


	@Test
	public void createAndSaveDriver() {
		final Object[][] testingData = {
			{ // Creación en draft mode, luego lo edita y guarda y luego lo pone en final mode
				"admin", "title", "text", 3, null, false, null
			}, { // Creación en draft mode, luego lo cambia a final mode, luego intenta editarlo
				"admin", "title", "text", 3, null, true, IllegalArgumentException.class
			}, { // Creación sin título, falla
				"admin", null, "text", 3, null, false, ConstraintViolationException.class
			}, { // Creación sin descripción, falla
				"admin", "title", null, 3, null, false, ConstraintViolationException.class
			}, { // Creación con gauce >3, falla
				"admin", "title", "text", 4, null, false, ConstraintViolationException.class
			}, { // Creación gauge <0, falla
				"admin", "title", "text", 0, null, false, ConstraintViolationException.class
			}, { // Creación fecha pasada, falla
				"admin", "title", "text", 3, new Date(17, 02, 20), false, IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.createAndEditTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Integer) testingData[i][3], (Date) testingData[i][4], (Boolean) testingData[i][5], (Class<?>) testingData[i][6]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}
	@Test
	public void editAndDeleteDriver() {
		final Object[][] testingData = {
			{ // Editado propio
				"admin2", "cosaX4", false, false, null
			}, { // Borrado propio
				"admin2", "cosaX4", false, true, null
			}, { // Borrar en final
				"admin2", "cosaX4", true, true, IllegalArgumentException.class
			}, { // Editar lo de otro
				"admin1", "cosaX4", false, false, IllegalArgumentException.class
			}, { // Borrar lo de otro
				"admin1", "cosaX4", true, false, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.editAndDeleteTemplate((String) testingData[i][0], (String) testingData[i][1], (Boolean) testingData[i][2], (Boolean) testingData[i][3], (Class<?>) testingData[i][4]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}
	protected void editAndDeleteTemplate(final String admin, final String cosaXBean, final Boolean draft, final Boolean delete, final Class<?> expected) {
		Class<?> caught;
		int cosaXId;
		CosaX cosaX;

		caught = null;
		try {
			this.authenticate(admin);
			cosaXId = this.getEntityId(cosaXBean);
			cosaX = this.cosaXService.findOne(cosaXId);
			if (draft)
				cosaX = this.cosaXService.setFinalMode(cosaX);
			cosaX = this.cosaXService.save(cosaX);
			if (delete)
				this.cosaXService.delete(cosaX);

			this.cosaXService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	protected void createAndEditTemplate(final String admin, final String title, final String description, final Integer gauge, final Date publication, final Boolean draftMode, final Class<?> expected) {
		Class<?> caught;
		int newspaperId;
		CosaX cosaX;

		caught = null;
		try {
			this.authenticate(admin);
			newspaperId = super.getEntityId("newspaper1");
			cosaX = this.cosaXService.create(newspaperId);
			cosaX.setDescription(description);
			cosaX.setDraftMode(true);
			cosaX.setGauge(gauge);
			cosaX.setPublicationDate(publication);
			cosaX.setTitle(title);
			cosaX.setTicker(this.configService.generateTicker());
			cosaX = this.cosaXService.save(cosaX);

			if (draftMode)
				cosaX = this.cosaXService.setFinalMode(cosaX);

			cosaX.setDescription(description);
			cosaX.setGauge(gauge);
			cosaX.setTitle(title);
			cosaX = this.cosaXService.save(cosaX);

			cosaX = this.cosaXService.setFinalMode(cosaX);

			this.cosaXService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
