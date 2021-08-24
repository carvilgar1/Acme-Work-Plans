package acme.features.manager.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.features.administrator.spamfilter.spamword.AdministratorSpamwordListService;
import acme.features.authenticated.manager.AuthenticatedManagerRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class ManagerMyTasksCreateService implements AbstractCreateService<Manager, Task> {

	@Autowired
	protected ManagerMyTasksRepository repository;
	
	@Autowired
	protected AuthenticatedManagerRepository managerRepo;
	
	@Autowired
	protected AdministratorSpamwordListService spamService;
	
	@Override
	public boolean authorise(final Request<Task> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Task> request, final Task entity, final Errors errors) {
		assert request!=null;
		assert entity!=null;
		assert errors!=null;
		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Task> request, final Task entity, final Model model) {
		assert request!=null;
		assert entity!=null;
		assert model!=null;
		request.unbind(entity, model, "title", "startDate", "endDate", "description", "publicTask", "url");
	}

	@Override
	public Task instantiate(final Request<Task> request) {
		assert request != null;
		Task task;
		final Double workFlow = 0.0;
		final Boolean publicTask = false;
		task = new Task();
		task.setWorkFlow(workFlow);
		task.setPublicTask(publicTask);
		return task;
	}
	
	public Boolean validacionFechas(final Task task) {
		Boolean b = false;
		if(task.getStartDate()!=null && task.getEndDate()!=null) {
			b = task.getStartDate().before(task.getEndDate());
		}
		return b;
	}
	
	public Boolean fechaInicialDespuesFechaActual(final Task task) {
		if(task.getStartDate()!=null) {
			final Date actual = new Date(System.currentTimeMillis()-1);
			return task.getStartDate().after(actual);
		} else {
			return false;
		}
	}
	
	public Boolean fechaFinalDespuesFechaActual(final Task task) {
		if(task.getEndDate()!=null) {
			final Date actual = new Date(System.currentTimeMillis()-1);
			return task.getEndDate().after(actual);
		} else {
			return false;
		}
	}

	@Override
	public void validate(final Request<Task> request, final Task entity, final Errors errors) {
		assert request!=null;
		assert entity!=null;
		assert errors!=null;
		
		if(entity.getEndDate()!=null && entity.getStartDate()!=null) {
			final Boolean b3 = this.validacionFechas(entity);
			errors.state(request, b3, "endDate", "manager.mytasks.error.dates");
		}
		if(entity.getStartDate()!=null) {
			final Boolean b1 = this.fechaInicialDespuesFechaActual(entity);
			errors.state(request, b1, "startDate", "manager.mytasks.error.startDate");
		}
		if(entity.getEndDate()!=null) {
			final Boolean b2 = this.fechaFinalDespuesFechaActual(entity);
			errors.state(request, b2, "endDate", "manager.mytasks.error.endDate");
		}
		final Boolean spam = this.spamService.filtroTasks(entity);
		errors.state(request, spam, "description", "manager.mytasks.error.spam");
	}

	@Override
	public void create(final Request<Task> request, final Task entity) {
		assert request!=null;
		assert entity!=null;
		
		final Double workFlow = 0.0;
		final Boolean publicTask = false;
		final Manager manager;
		final int id = request.getPrincipal().getActiveRoleId();
		manager = this.managerRepo.findManagerById(id);
		
		entity.setWorkFlow(workFlow);
		entity.setPublicTask(publicTask);
		entity.setManager(manager);
		this.repository.save(entity);
	}

}
