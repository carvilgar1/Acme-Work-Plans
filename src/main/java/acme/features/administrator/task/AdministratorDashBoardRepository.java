package acme.features.administrator.task;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tasks.Task;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorDashBoardRepository extends AbstractRepository{
	
	@Query("SELECT t FROM Task t")
	Collection<Task> findAllTasks();
	
	@Query("SELECT t.workFlow.entera*60 + t.workFlow.decimal FROM Task t")
	Collection<Double> findAllWorkFlows();
	
	@Query("SELECT COUNT(t) FROM Task t WHERE t.endDate >= CURRENT_DATE")
	Integer numberOfNonFinishedTasks();
	
	@Query("SELECT COUNT(t) FROM Task t WHERE t.endDate < CURRENT_DATE")
	Integer numberOfFinishedTasks();
	
	@Query("SELECT COUNT(t) FROM Task t WHERE t.publicTask = TRUE")
	Integer numberOfPublicTasks();
	
	@Query("SELECT COUNT(t) FROM Task t WHERE t.publicTask = FALSE")
	Integer numberOfNonPublicTasks();
	
	@Query("SELECT AVG(t.workFlow.entera*60 + t.workFlow.decimal) FROM Task t")
	Double averageWorkFlow();
	
	@Query("SELECT MAX(t.workFlow.entera*60 + t.workFlow.decimal) FROM Task t")
	Double maxWorkFlow();
	
	@Query("SELECT MIN(t.workFlow.entera*60 + t.workFlow.decimal) FROM Task t")
	Double minWorkFlow();
	
	@Query("SELECT ABS(FUNCTION('DATEDIFF', t.startDate, t.endDate)) FROM Task t")
	Collection<Double> findAllPeriods();
	
	@Query("SELECT AVG(ABS(FUNCTION('DATEDIFF', t.startDate, t.endDate))) FROM Task t")
	Double averagePeriod();
	
	@Query("SELECT MAX(ABS(FUNCTION('DATEDIFF', t.startDate, t.endDate))) FROM Task t")
	Double maxPeriod();
	
	@Query("SELECT MIN(ABS(FUNCTION('DATEDIFF', t.startDate, t.endDate))) FROM Task t")
	Double minPeriod();
}
