
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CosaXService;
import controllers.AbstractController;
import domain.Actor;
import domain.CosaX;

@Controller
@RequestMapping("/administrator/cosaX")
public class CosaXAdministratorController extends AbstractController {

	//Service -----------------------------------------------------------------
	@Autowired
	private CosaXService	cosaXService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public CosaXAdministratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<CosaX> cosaXs;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		cosaXs = this.cosaXService.findByAdministrator(principal.getId());
		result = new ModelAndView("cosaX/list");
		result.addObject("cosaXs", cosaXs);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int newspaperId) {
		ModelAndView result;
		CosaX cosaX;

		cosaX = this.cosaXService.create(newspaperId);
		result = this.createEditModelAndView(cosaX);
		result.addObject("requestURI", "administrator/cosaX/edit.do?newspaperId=" + newspaperId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int cosaxId) {
		ModelAndView result;
		CosaX cosaX;

		cosaX = this.cosaXService.findOne(cosaxId);
		result = this.createEditModelAndView(cosaX);
		result.addObject("newspaperId", cosaX.getNewspaper().getId());
		result.addObject("requestURI", "administrator/cosaX/edit.do?cosaxId=" + cosaxId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute CosaX cosaX, final BindingResult binding) {
		ModelAndView result;

		cosaX = this.cosaXService.reconstruct(cosaX, binding);

		if (binding.hasErrors()){
			result = this.createEditModelAndView(cosaX);
		}else
			try {
				this.cosaXService.save(cosaX);
				result = new ModelAndView("redirect:/newspaper/display.do?newspaperId=" + cosaX.getNewspaper().getId());
			} catch (final Throwable oops) {
				String errorMessage = "cosaX.commit.error";
				if (oops.getMessage().contains("message.error")||oops.getMessage()=="cosaX.fechaPasada")
					errorMessage = oops.getMessage();
				result = this.createEditModelAndView(cosaX, errorMessage);
				result.addObject("requestURI", "administrator/cosaX/edit.do?cosaxId=" + cosaX.getId());
			}

		return result;
	}

	// Delete ---------------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int cosaxId) {
		ModelAndView result;
		CosaX cosaX;

		cosaX = this.cosaXService.findOne(cosaxId);
		this.cosaXService.delete(cosaX);
		result = new ModelAndView("redirect:/administrator/cosaX/list.do");

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final CosaX cosaX) {
		ModelAndView result;

		result = this.createEditModelAndView(cosaX, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CosaX cosaX, final String message) {
		ModelAndView result;

		result = new ModelAndView("cosaX/edit");
		result.addObject("cosaX", cosaX);
		result.addObject("message", message);

		return result;
	}
}