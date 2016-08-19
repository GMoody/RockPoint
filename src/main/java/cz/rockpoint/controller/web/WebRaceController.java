package cz.rockpoint.controller.web;

import cz.rockpoint.controller.IMapping;
import cz.rockpoint.model.RaceEntity;
import cz.rockpoint.service.RaceService;
import cz.rockpoint.utils.IParser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Inject;
import javax.validation.Valid;

import static cz.rockpoint.controller.IMapping.RACE;
import static cz.rockpoint.controller.IMapping.WEB;

@Controller
@RequestMapping(value = WEB + RACE)
public class WebRaceController implements IMapping {

    @Inject
    RaceService raceService;

    @Inject
    private IParser iParser;

    // RRF - Race Registration Form
    // Opens the race registration form
    @RequestMapping(value = REGISTRATION_FORM, method= RequestMethod.GET)
    public ModelAndView getRRF(RaceEntity raceEntity) {
        return new ModelAndView("RRF/registration_form");
    }

    // Validates registration form data, saves new race and redirects to all races
    @RequestMapping(value = REGISTRATION_FORM, method = RequestMethod.POST)
    public RedirectView checkRRFData(@Valid RaceEntity raceEntity, BindingResult bindingResult) {
        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);

        if (bindingResult.hasErrors())
            redirectView.setUrl("/web/race/registration_form");
        else {
            redirectView.setUrl("/web/race/all");
            RaceEntity temp = raceService.findAll().stream().filter(r -> r.getRace_id() == raceEntity.getRace_id()).findFirst().orElse(null);

            if(temp == null) raceService.saveRace(raceEntity);
            else {
                temp = raceEntity;
                raceService.saveRace(temp);
            }
            iParser.updateURL(raceEntity.getRace_url());
            iParser.updateDataFromAllURLs();
        }
        return redirectView;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView editRaceById(@PathVariable("id") String id) {
        RaceEntity newRace = raceService.findAll().stream().filter(r -> r.getRace_id() == Integer.valueOf(id)).findFirst().orElse(null);

        if(newRace != null){
            ModelAndView modelAndView = new ModelAndView("RRF/registration_form");
            modelAndView.addObject("raceEntity", newRace);
            return modelAndView;
        }else return new ModelAndView("races");
    }

    //region Redirects
    @RequestMapping(value = LONG)
    public String redirectFromHomeLong() {
        return "redirect:/web/competitions/long";
    }

    @RequestMapping(value = HALF)
    public String redirectFromHomeHalf() {
        return "redirect:/web/competitions/half";
    }

    @RequestMapping(value = SHORT)
    public String redirectFromHomeShort() {
        return "redirect:/web/competitions/short";
    }

    @RequestMapping(value = HOME)
    public String redirectFromHome() {
        return "redirect:/web/race/all";
    }

    // Method for redirection to all races
    @RequestMapping(value = ALL, method=RequestMethod.GET)
    public ModelAndView allRaces(ModelAndView modelAndView){
        modelAndView = new ModelAndView("RRF/races");
        modelAndView.addObject("races", raceService.findAll());
        return modelAndView;
    }
    //endregion
}
