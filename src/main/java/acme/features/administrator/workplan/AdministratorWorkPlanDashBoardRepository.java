package acme.features.administrator.workplan;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.workplans.Workplan;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorWorkPlanDashBoardRepository extends AbstractRepository{
	
	@Query("SELECT wp FROM Workplan wp")
	Collection<Workplan> findAllWorkPlan();
	
	@Query("SELECT wp.workLoadMinutes FROM Workplan wp")
	Collection<Double> findAllWorkFlows();
	
	@Query("SELECT COUNT(wp) FROM Workplan wp WHERE wp.endDate >= CURRENT_DATE")
	Integer numberOfNonFinishedWorkPlan();
	
	@Query("SELECT COUNT(wp) FROM Workplan wp WHERE wp.endDate < CURRENT_DATE")
	Integer numberOfFinishedWorkPlan();
	
	@Query("SELECT COUNT(wp) FROM Workplan wp WHERE wp.publicPlan = TRUE")
	Integer numberOfPublicWorkPlan();
	
	@Query("SELECT COUNT(wp) FROM Workplan wp WHERE wp.publicPlan = FALSE")
	Integer numberOfNonPublicWorkPlan();
	
	@Query("SELECT AVG(wp.workLoadMinutes) FROM Workplan wp")
	Double averageWorkFlow();
	
	@Query("SELECT MAX(wp.workLoadMinutes) FROM Workplan wp")
	Double maxWorkFlow();
	
	@Query("SELECT MIN(wp.workLoadMinutes) FROM Workplan wp")
	Double minWorkFlow();
	
	@Query("SELECT ABS(FUNCTION('DATEDIFF', wp.startDate, wp.endDate)) FROM Workplan wp")
	Collection<Double> findAllPeriods();
	
	@Query("SELECT AVG(ABS(FUNCTION('DATEDIFF', wp.startDate, wp.endDate))) FROM Workplan wp")
	Double averagePeriod();
	
	@Query("SELECT MAX(ABS(FUNCTION('DATEDIFF', wp.startDate, wp.endDate))) FROM Workplan wp")
	Double maxPeriod();
	
	@Query("SELECT MIN(ABS(FUNCTION('DATEDIFF', wp.startDate, wp.endDate))) FROM Workplan wp")
	Double minPeriod();
}
