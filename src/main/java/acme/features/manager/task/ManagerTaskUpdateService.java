package acme.features.manager.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.features.administrator.spamfilter.spamword.AdministratorSpamwordListService;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractUpdateService;

@Service
public class ManagerTaskUpdateService implements AbstractUpdateService<Manager, Task>{
	
	@Autowired
	ManagerMyTasksRepository repository;
	
	@Autowired
	protected AdministratorSpamwordListService spamService;

	@Override
	public boolean authorise(final Request<Task> request) {
		assert request != null;
		final int id = request.getModel().getInteger("id");
		final Task task = this.repository.findTaskById(id);
		final int managerId= request.getPrincipal().getActiveRoleId();
		
		return task.getManager().getId()==managerId;
	}
	
	@Override
	public void bind(final Request<Task> request, final Task entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Task> request, final Task entity, final Model model) {
		assert request!=null;
		assert entity!=null;
		assert model!=null;
				
		request.unbind(entity, model, "title","startDate","endDate","workFlow","description","publicTask", "url");
		
	}

	@Override
	public Task findOne(final Request<Task> request) {
		assert request != null;
		
		final int id = request.getModel().getInteger("id");
		Task result;
		
		result = this.repository.findTaskById(id);
		return result;
	}

	@Override
	public void validate(final Request<Task> request, final Task entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		if(entity.getEndDate()!=null && entity.getStartDate()!=null && entity.getWorkFlow()!=null) {
			final Boolean b1 = this.workFlowValidation(entity);
			final Boolean b3 = this.validacionFechas(entity);
			errors.state(request, b3, "endDate", "manager.mytasks.error.dates");
			errors.state(request, b1, "workFlow", "manager.mytasks.error.workFlow");
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
		final Date actual = new Date(System.currentTimeMillis()-1);
		return task.getEndDate().after(actual);
	}
	
	private Boolean workFlowValidation(final Task entity) {
		final Double taskWorkFlow = entity.getWorkFlow();
		final long diff = entity.getEndDate().getTime() - entity.getStartDate().getTime();
		final long workFlowMs = (long)(taskWorkFlow * 3600000);
		if(workFlowMs > diff) {
			return false;
		}else {
			return true;
		}
	}

	@Override
	public void update(final Request<Task> request, final Task entity) {
		assert request != null;
		assert entity != null;
		
		String title;
		Date endDate;
		Double workFlow;
		String description;
		Boolean publicTask;
		String url;
		
		title = request.getModel().getString("title");
		endDate =request.getModel().getDate("endDate");
		workFlow = request.getModel().getDouble("workFlow");
		description = request.getModel().getString("description");
		publicTask = request.getModel().getBoolean("publicTask");
		url = request.getModel().getString("url");
		
		entity.setTitle(title);
		entity.setEndDate(endDate);
		entity.setWorkFlow(workFlow);
		entity.setDescription(description);
		entity.setPublicTask(publicTask);
		entity.setUrl(url);
		
		this.repository.save(entity);
		
	}
}
