package acme.features.anonymous.workplan;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.workplans.Workplan;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnonymousWorkplanRepository extends AbstractRepository{
	
	@Query("SELECT w FROM Workplan w WHERE w.endDate > CURRENT_DATE AND w.publicPlan = TRUE ORDER BY w.startDate DESC")
	Collection<Workplan> findPublicWorkplansNonFinished();
	
	@Query("SELECT w FROM Workplan w WHERE w.endDate > CURRENT_DATE AND w.publicPlan = TRUE AND w.id = ?1")
	Workplan findOnePublicWorkplansNonFinished(int id);
	
}
