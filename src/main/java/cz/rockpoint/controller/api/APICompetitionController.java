package cz.rockpoint.controller.api;

import cz.rockpoint.controller.IMainController;
import cz.rockpoint.controller.IMapping;
import cz.rockpoint.model.CompetitionEntity;
import cz.rockpoint.model.RaceEntity;
import cz.rockpoint.service.CompetitionService;
import cz.rockpoint.service.RaceService;
import cz.rockpoint.utils.IParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

import static cz.rockpoint.controller.IMapping.API;

// API Competition Controller
@RestController
@RequestMapping(value = API, produces = MediaType.APPLICATION_JSON_VALUE)
public class APICompetitionController implements IMapping, IMainController {

    @Inject
    private CompetitionService competitionService;

    @Inject
    private IParser iParser;

    @Inject
    private RaceService raceService;

    // Method for getting all competitions with type Long
    @RequestMapping(value = COMPETITIONS + LONG, method = RequestMethod.GET)
    public ResponseEntity<List<CompetitionEntity>> getCompetitionsLong(){
        checkInitOrUpdate(0);
        List<CompetitionEntity> temp = competitionService.findByType(0);
        return new ResponseEntity<>(temp, HttpStatus.OK);
    }

    // Method for getting all competitions with type Half
    @RequestMapping(value = COMPETITIONS + HALF, method = RequestMethod.GET)
    public ResponseEntity<List<CompetitionEntity>> getCompetitionsHalf(){
        checkInitOrUpdate(1);
        List<CompetitionEntity> temp = competitionService.findByType(1);
        return new ResponseEntity<>(temp, HttpStatus.OK);
    }

    // Method for getting all competitions with type Short
    @RequestMapping(value = COMPETITIONS + SHORT, method = RequestMethod.GET)
    public ResponseEntity<List<CompetitionEntity>> getCompetitionsShort(){
        checkInitOrUpdate(2);
        List<CompetitionEntity> temp = competitionService.findByType(2);
        return new ResponseEntity<>(temp, HttpStatus.OK);
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
}
