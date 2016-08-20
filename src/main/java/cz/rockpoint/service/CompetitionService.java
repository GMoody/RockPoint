package cz.rockpoint.service;

import cz.rockpoint.model.CompetitionEntity;

import java.util.List;

public interface CompetitionService extends BaseService<CompetitionEntity>{

    List<CompetitionEntity> findByType(int type);
    boolean checkCHPColumn(int type, int column);
}
