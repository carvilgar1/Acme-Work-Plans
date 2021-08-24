package acme.features.manager.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractShowService;

@Service
public class ManagerTaskShowService implements AbstractShowService<Manager, Task>{
	
	@Autowired
	ManagerMyTasksRepository repository;

	@Override
	public boolean authorise(final Request<Task> request) {
		assert request != null;
		final int id = request.getModel().getInteger("id");
		final Task task = this.repository.findTaskById(id);
		final int managerId= request.getPrincipal().getActiveRoleId();
		
		return task.getManager().getId()==managerId;
	}

	@Override
	public void unbind(final Request<Task> request, final Task entity, final Model model) {
		assert request!=null;
		assert entity!=null;
		assert model!=null;
		
		request.unbind(entity, model, "title","startDate","endDate","workFlow","description","publicTask","url");
	}

	@Override
	public Task findOne(final Request<Task> request) {
		assert request != null;
		
		final int id = request.getModel().getInteger("id");
		Task result;
		result = this.repository.findTaskById(id);
		return result;
	}
}
