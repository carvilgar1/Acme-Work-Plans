package acme.features.manager.task;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.entities.tasks.Task;
import acme.entities.workplans.Workplan;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface ManagerMyTasksRepository extends AbstractRepository {
	
	@Query("SELECT t FROM Task t WHERE t.manager.id=:id")
	Collection<Task> findMyTasks(@Param("id") int id);
	
	@Query("SELECT t FROM Task t WHERE t.id=:id")
	Task findTaskById(@Param("id") int id);

	@Query("SELECT t FROM Task t WHERE t.manager.id=:id and t.publicTask=1")
	Collection<Task> findMyPublicTasks(@Param("id") int id);
	
	@Query("SELECT wp FROM Workplan wp JOIN wp.tasks t WHERE t.id =:taskId")
	List<Workplan> findTaskWorkplans(@Param("taskId") int taskId);
}
