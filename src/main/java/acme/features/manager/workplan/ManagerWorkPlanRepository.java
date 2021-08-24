package acme.features.manager.workplan;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.entities.workplans.Workplan;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface ManagerWorkPlanRepository extends AbstractRepository{

	@Query("SELECT wp FROM Workplan wp WHERE wp.manager.id=:id")
	Collection<Workplan> findByManagerId(@Param("id")int id);

	@Query("SELECT wp FROM Workplan wp WHERE wp.id=:id")
	Workplan findById(@Param("id") int id);
}
