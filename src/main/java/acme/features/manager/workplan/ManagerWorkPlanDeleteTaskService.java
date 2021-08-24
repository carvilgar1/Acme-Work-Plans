package acme.features.manager.workplan;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.entities.workplans.Workplan;
import acme.features.manager.task.ManagerMyTasksRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.DomainEntity;
import acme.framework.services.AbstractUpdateService;

@Service
public class ManagerWorkPlanDeleteTaskService implements AbstractUpdateService<Manager, Workplan>{
	
	@Autowired
	protected ManagerWorkPlanRepository repository;
	
	@Autowired
	protected ManagerMyTasksRepository tasksRepository;
	
	@Override
	public boolean authorise(final Request<Workplan> request) {
		assert request!=null;
		final int id=request.getModel().getInteger("id");
		final Workplan wp=this.repository.findById(id);
		final int managerId= request.getPrincipal().getActiveRoleId();
		return wp.getManager().getId()==managerId;
	}

	@Override
	public void bind(final Request<Workplan> request, final Workplan entity, final Errors errors) {
		assert request != null && entity != null && errors != null;
		request.bind(entity, errors);
		
	}

	@Override
	public void unbind(final Request<Workplan> request, final Workplan entity, final Model model) {
		assert request != null && entity != null && model != null;

		request.unbind(entity, model, "startDate", "endDate", "workLoad", "publicPlan", "tasks");
		
	}

	@Override
	public Workplan findOne(final Request<Workplan> request) {
		int id;
		id=request.getModel().getInteger("id");
		return this.repository.findById(id);
	}

	@Override
	public void validate(final Request<Workplan> request, final Workplan entity, final Errors errors) {
		assert request != null && entity != null && errors != null;
	}

	@Override
	public void update(final Request<Workplan> request, final Workplan entity) {
		final Set<Task> tasks;
		Workplan wp;
		Task t=new Task();
		final Integer taskId;
		wp=this.repository.findById(entity.getId());
		taskId=request.getModel().getInteger("deleteTask");
		final Optional<DomainEntity>opTask=this.tasksRepository.findById(taskId);
		if(opTask.isPresent())t=(Task) opTask.get();
		tasks=entity.getTasks();
		tasks.remove(t);
		wp.setTasks(tasks);
		wp.setWorkLoad();
		this.repository.save(wp);
	}

}
