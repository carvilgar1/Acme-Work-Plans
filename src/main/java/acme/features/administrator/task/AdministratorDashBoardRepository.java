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
	
	@Query("SELECT COUNT(t) FROM Task t WHERE t.endDate >= CURRENT_DATE")
	Integer numberOfNonFinishedTasks();
	
	@Query("SELECT COUNT(t) FROM Task t WHERE t.endDate < CURRENT_DATE")
	Integer numberOfFinishedTasks();
	
	@Query("SELECT COUNT(t) FROM Task t WHERE t.publicTask = TRUE")
	Integer numberOfPublicTasks();
	
	@Query("SELECT COUNT(t) FROM Task t WHERE t.publicTask = FALSE")
	Integer numberOfNonPublicTasks();
	
	//Es necesario operar en minutos dado que el formato ofrecido en los requisitos
	//de información <<The workload is a number of hours (0 up to 99) with an optional 
	//fraction that represents the number of minutes (0 up to 59)>> está compuesto de 
	//horas y minutos.
	//Ej. Fallo del formato para operar sobre los workloads: [1.30, 1.2, 0.09]
	//Al hacer la media el resultado es 0.86, 0 horas y 86 min -> 1 horas y 26 min
	// workloads en minutos: [90, 80, 9]
	//Al hacer la media el resultado es 59, 59 min -> 0 horas y 59 min
	
	@Query("SELECT t.workFlow.entera*60 + t.workFlow.decimal FROM Task t")
	Collection<Double> findAllWorkFlows();
	
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
