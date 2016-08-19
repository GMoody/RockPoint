package cz.rockpoint.dao;

import cz.rockpoint.model.CompetitionEntity;

import java.util.List;

public interface CompetitionDao extends BaseDao<CompetitionEntity>{

    List<CompetitionEntity> findByType(int type);

}
