package cz.rockpoint.service.Impl;

import cz.rockpoint.dao.CompetitionDao;
import cz.rockpoint.model.CompetitionEntity;
import cz.rockpoint.service.CompetitionService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class CompetitionServiceImpl implements CompetitionService{

    @Inject
    private CompetitionDao competitionDao;

    // Method finds competitions by type and also updates information from the source
    @Override
    public List<CompetitionEntity> findByType(int type) {
        return competitionDao.findByType(type);
    }

    @Override
    public boolean checkCHPColumn(int type, int column) {
        return competitionDao.checkCHPColumn(type, column);
    }

}
