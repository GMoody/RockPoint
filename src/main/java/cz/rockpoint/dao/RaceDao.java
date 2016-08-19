package cz.rockpoint.dao;

import cz.rockpoint.model.RaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// Race DAO extends from JpaRepository to auto-implement class that works with DB
public interface RaceDao extends JpaRepository<RaceEntity, Integer> {

}
