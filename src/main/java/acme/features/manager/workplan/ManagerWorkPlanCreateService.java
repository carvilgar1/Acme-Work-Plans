package acme.features.manager.workplan;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.entities.workplans.Workplan;
import acme.features.authenticated.manager.AuthenticatedManagerRepository;
import acme.features.manager.task.ManagerMyTasksRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class ManagerWorkPlanCreateService implements AbstractCreateService<Manager, Workplan>  {

	@Autowired
	protected ManagerWorkPlanRepository repository;
	
	@Autowired
	protected AuthenticatedManagerRepository managerRepository;
	
	@Autowired
	protected ManagerMyTasksRepository tasksRepository;
	
	@Override
	public boolean authorise(final Request<Workplan> request) {
		assert request!=null;
		return true;
	}

	@Override
	public void bind(final Request<Workplan> request, final Workplan entity, final Errors errors) {
		assert request!=null;
		assert entity!=null;
		assert errors!=null;
		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Workplan> request, final Workplan entity, final Model model) {
		assert request!=null;
		assert entity!=null;
		assert model!=null;
		final Collection<Task>t;
		t=this.tasksRepository.findMyTasks(request.getPrincipal().getActiveRoleId());
		model.setAttribute("tasksInsert", t);
		model.setAttribute("canUpdate",true);
		request.unbind(entity, model,"startDate", "endDate");
		
	}

	@Override
	public Workplan instantiate(final Request<Workplan> request) {
		assert request!=null;
		final Workplan wp=new Workplan();
		wp.setTasks(new HashSet<>());
		wp.setWorkLoad(0.0);
		wp.setManager(this.managerRepository.findManagerById(request.getPrincipal().getActiveRoleId()));
		wp.setPublicPlan(false);
		return wp;
	}

	public Boolean validacionFechas(final Workplan wp) {
		Boolean b = false;
		if(wp.getStartDate()!=null && wp.getEndDate()!=null) {
			b = wp.getStartDate().before(wp.getEndDate());
		}
		return b;
	}
	
	public Boolean fechaInicialDespuesFechaActual(final Workplan wp) {
		if(wp.getStartDate()!=null) {
			final Date actual = new Date(System.currentTimeMillis()-1);
			return wp.getStartDate().after(actual);
		} else {
			return false;
		}
	}
	
	public Boolean fechaFinalDespuesFechaActual(final Workplan wp) {
		if(wp.getEndDate()!=null) {
			final Date actual = new Date(System.currentTimeMillis()-1);
			return wp.getEndDate().after(actual);
		} else {
			return false;
		}
	}
	
	@Override
	public void validate(final Request<Workplan> request, final Workplan entity, final Errors errors) {
		assert request!=null;
		assert entity!=null;
		assert errors!=null;
		
		if(entity.getEndDate()!=null && entity.getStartDate()!=null) {
			final Boolean b3 = this.validacionFechas(entity);
			errors.state(request, b3, "endDate", "manager.workplan.error.dates");
		}
		if(entity.getStartDate()!=null) {
			final Boolean b1 = this.fechaInicialDespuesFechaActual(entity);
			errors.state(request, b1, "startDate", "manager.workplan.error.startDate");
		}
		if(entity.getEndDate()!=null) {
			final Boolean b2 = this.fechaFinalDespuesFechaActual(entity);
			errors.state(request, b2, "endDate", "manager.workplan.error.endDate");
		}
		request.getModel().setAttribute("canUpdate",true);
	}

	@Override
	public void create(final Request<Workplan> request, final Workplan entity) {
		assert request!=null;
		assert entity!=null;

		this.repository.save(entity);
	}

}
