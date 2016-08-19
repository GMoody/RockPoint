package cz.rockpoint.dao.Impl;

import cz.rockpoint.dao.CompetitionDao;
import cz.rockpoint.model.CompetitionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompetitionDaoImpl implements CompetitionDao {

    // Method for getting competitions collection by type
    @Override
    public List<CompetitionEntity> findByType(int type) {
        return CompetitionEntity.getCompetitions().stream().filter(c -> c.getType() == type).collect(Collectors.toList());
    }
}
