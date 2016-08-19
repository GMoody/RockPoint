package cz.rockpoint.service.Impl;

import cz.rockpoint.dao.RaceDao;
import cz.rockpoint.model.RaceEntity;
import cz.rockpoint.service.RaceService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RaceServiceImpl implements RaceService{

    @Inject
    private RaceDao raceDao;

    // Method checks if race exists, returns the existed one or saves the new one
    @Override
    public RaceEntity saveRace(RaceEntity raceEntity){
        for (RaceEntity race : raceDao.findAll())
            if(race.equals(raceEntity))
                return race;
        return raceDao.save(raceEntity);
    }

    @Override
    public List<RaceEntity> findAll() {
        return raceDao.findAll();
    }

}
