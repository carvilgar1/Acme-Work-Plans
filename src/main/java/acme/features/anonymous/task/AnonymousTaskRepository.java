package acme.features.anonymous.task;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tasks.Task;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnonymousTaskRepository extends AbstractRepository{
	
	@Query("SELECT t FROM Task t WHERE t.publicTask = TRUE AND t.endDate > CURRENT_DATE")
	Collection<Task> findPublicTasksNonFinished();
	
	@Query("SELECT t FROM Task t WHERE t.publicTask = TRUE AND t.endDate > CURRENT_DATE AND t.id = ?1")
	Task findOnePublicTasksNonFinished(int id);
	
}
