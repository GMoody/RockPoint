package cz.rockpoint.controller.web;

import cz.rockpoint.controller.IMainController;
import cz.rockpoint.controller.IMapping;
import cz.rockpoint.model.RaceEntity;
import cz.rockpoint.service.CompetitionService;
import cz.rockpoint.service.RaceService;
import cz.rockpoint.utils.IParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.List;

import static cz.rockpoint.controller.IMapping.COMPETITIONS;
import static cz.rockpoint.controller.IMapping.WEB;

@Controller
@RequestMapping(value = WEB + COMPETITIONS)
public class WebCompetitionController implements IMapping, IMainController {

    @Inject
    private CompetitionService competitionService;

    @Inject
    private RaceService raceService;

    @Inject
    IParser iParser;

    // Method for getting all competitions with type Long
    @RequestMapping(value = LONG, method = RequestMethod.GET)
    public ModelAndView getCompetitionsLong(ModelAndView model) {
        checkInitOrUpdate(0);
        model = new ModelAndView("competition_long");
        model.addObject("competitionsLong", competitionService.findByType(0));
        model.addObject("chp1Column", competitionService.checkCHPColumn(0, 0));
        model.addObject("chp2Column", competitionService.checkCHPColumn(0, 1));
        model.addObject("chp3Column", competitionService.checkCHPColumn(0, 2));
        model.addObject("chp4Column", competitionService.checkCHPColumn(0, 3));
        model.addObject("race_title", raceService.findAll().get(raceService.findAll().size() - 1).getRace_title());
        return model;
    }

    // Method for getting all competitions with type Half
    @RequestMapping(value = HALF, method = RequestMethod.GET)
    public ModelAndView getCompetitionsHalf(ModelAndView model) {
        checkInitOrUpdate(1);
        model = new ModelAndView("competition_half");
        model.addObject("competitionsHalf", competitionService.findByType(1));
        model.addObject("chp1Column", competitionService.checkCHPColumn(1, 0));
        model.addObject("chp2Column", competitionService.checkCHPColumn(1, 1));
        model.addObject("chp3Column", competitionService.checkCHPColumn(1, 2));
        model.addObject("chp4Column", competitionService.checkCHPColumn(1, 3));
        model.addObject("race_title", raceService.findAll().get(raceService.findAll().size() - 1).getRace_title());
        return model;
    }

    // Method for getting all competitions with type Short
    @RequestMapping(value = SHORT, method = RequestMethod.GET)
    public ModelAndView getCompetitionsShort(ModelAndView model) {
        checkInitOrUpdate(2);
        model = new ModelAndView("competition_short");
        model.addObject("chp1Column", competitionService.checkCHPColumn(2, 0));
        model.addObject("chp2Column", competitionService.checkCHPColumn(2, 1));
        model.addObject("chp3Column", competitionService.checkCHPColumn(2, 2));
        model.addObject("chp4Column", competitionService.checkCHPColumn(2, 3));
        model.addObject("competitionsShort", competitionService.findByType(2));
        model.addObject("race_title", raceService.findAll().get(raceService.findAll().size() - 1).getRace_title());
        return model;
    }

    //region Redirects
    @RequestMapping(value = HOME)
    public String redirectFromHome() {
        return "redirect:/competitions/long";
    }

    // Method that checks whether the URL was initialized or it needs to get URL from DB
    @Override
    public void checkInitOrUpdate(int CompetitionType) {
        if(iParser.getURLid() == 0){
            List<RaceEntity> races = raceService.findAll();
            if(!races.isEmpty()){
                iParser.updateURL(races.get(races.size() - 1).getRace_url());
                iParser.updateDataFromAllURLs();
            }
        }else
            switch (CompetitionType){
                case 0:
                    iParser.updateDataFromLong();
                    break;
                case 1:
                    iParser.updateDataFromHalf();
                    break;
                case 2:
                    iParser.updateDataFromShort();
                    break;
            }
    }
    //endregion

}
