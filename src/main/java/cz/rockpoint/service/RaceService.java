package cz.rockpoint.service;

import cz.rockpoint.model.RaceEntity;

import java.util.List;

public interface RaceService {
    RaceEntity saveRace(RaceEntity raceEntity);
    List<RaceEntity> findAll();
}
