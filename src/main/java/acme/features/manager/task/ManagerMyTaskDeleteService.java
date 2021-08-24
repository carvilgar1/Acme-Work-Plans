
package acme.features.manager.task;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.entities.workplans.Workplan;
import acme.features.manager.workplan.ManagerWorkPlanRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractDeleteService;

@Service
public class ManagerMyTaskDeleteService implements AbstractDeleteService<Manager, Task> {


	@Autowired
	protected ManagerMyTasksRepository repository;
	@Autowired
	protected ManagerWorkPlanRepository repositoryWorkplan;

	@Override
	public boolean authorise(final Request<Task> request) {
		assert request != null;

		boolean result;
		int taskId;
		Task task;
		Manager manager;
		Principal principal;

		taskId = request.getModel().getInteger("id");
		task = this.repository.findTaskById(taskId);
		manager = task.getManager();
		principal = request.getPrincipal();
		result = manager.getUserAccount().getId() == principal.getAccountId();

		return result;
	}

	@Override
	public void bind(final Request<Task> request, final Task entity, final Errors errors) {
		assert request != null && entity != null && errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Task> request, final Task entity, final Model model) {
		assert request != null && entity != null && model != null;

		request.unbind(entity, model, "title", "startDate", "endDate", "description", "publicTask", "url");
	}

	@Override
	public Task findOne(final Request<Task> request) {
		assert request != null;

		Task result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findTaskById(id);

		return result;
	}

	@Override
	public void validate(final Request<Task> request, final Task entity, final Errors errors) {
		assert request != null && entity != null && errors != null;
	}

	@Override
	public void delete(final Request<Task> request, final Task entity) {
		assert request != null;
		assert entity != null;

		final List<Workplan> wps = this.repository.findTaskWorkplans(entity.getId());
		
		for(final Workplan wp: wps) {
			final Set<Task> tasks = wp.getTasks();
			tasks.remove(entity);
			wp.setTasks(tasks);
			wp.setWorkLoad();
			this.repositoryWorkplan.save(wp);
		}
		this.repository.delete(entity);
	}

}
