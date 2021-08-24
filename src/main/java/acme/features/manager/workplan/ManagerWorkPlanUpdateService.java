package acme.features.manager.workplan;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
public class ManagerWorkPlanUpdateService implements AbstractUpdateService<Manager, Workplan>{

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
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors);
		
	}

	@Override
	public void unbind(final Request<Workplan> request, final Workplan entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "startDate", "endDate", "workLoad", "publicPlan", "tasks");
		
	}

	@Override
	public Workplan findOne(final Request<Workplan> request) {
		int id;
		id=request.getModel().getInteger("id");
		return this.repository.findById(id);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void validate(final Request<Workplan> request, final Workplan entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		Workplan wp=new Workplan();
		Optional<DomainEntity> opWorkplan;
		opWorkplan= this.repository.findById(request.getModel().getInteger("id"));
		if(opWorkplan.isPresent())wp=(Workplan) opWorkplan.get();
		
		
		if(entity.getEndDate()!=null && entity.getStartDate()!=null) {
			final Boolean b3 = this.validacionFechas(entity);
			errors.state(request, b3, "endDate", "manager.workplan.error.dates");
			
			final Boolean b2a = this.validacionFechaFinal(entity);
			errors.state(request, b2a, "endDate", "manager.workplan.error.endDate.task");
			final Boolean b1a = this.validacionFechaInicial(entity);
			errors.state(request, b1a, "startDate", "manager.workplan.error.startDate.task");
		}
		if(entity.getStartDate()!=null) {
			final Boolean b1 = this.fechaInicialDespuesFechaActual(entity);
			errors.state(request, b1, "startDate", "manager.workplan.error.startDate");
		}
		if(entity.getEndDate()!=null) {
			final Boolean b2 = this.fechaFinalDespuesFechaActual(entity);
			errors.state(request, b2, "endDate", "manager.workplan.error.endDate");
		}
		
		final Collection<Task>ta;
		Set<Task> tasks;
		tasks=wp.getTasks();
		if(!tasks.isEmpty()) {
			final Date startRecommend=tasks.stream().map(Task::getStartDate).min((x,y)->x.compareTo(y)).orElse(new Date());
			startRecommend.setDate(startRecommend.getDate()-1);
			startRecommend.setHours(8);
			startRecommend.setMinutes(0);
			
			final Date finalRecommend=tasks.stream().map(Task::getEndDate).max((x,y)->x.compareTo(y)).orElse(new Date());
			finalRecommend.setDate(finalRecommend.getDate()+1);
			finalRecommend.setHours(17);
			finalRecommend.setMinutes(0);
			
			request.getModel().setAttribute("startRecommend", startRecommend);
			request.getModel().setAttribute("finalRecommend", finalRecommend);
			}
			if(Boolean.TRUE.equals(wp.getPublicPlan()))ta= this.tasksRepository.findMyPublicTasks(wp.getManager().getId());
			else ta= this.tasksRepository.findMyTasks(wp.getManager().getId());
			ta.stream().filter(x->!tasks.contains(x)).collect(Collectors.toSet());
			
			request.getModel().setAttribute("tasksInsert", ta);
		
		if(wp.getEndDate()!=null)request.getModel().setAttribute("canUpdate", wp.canUpdate());
		else request.getModel().setAttribute("canUpdate",true);
		
		request.getModel().setAttribute("tasks", wp.getTasks());
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
	
	public Boolean validacionFechaInicial(final Workplan wp) {
		Boolean b = false;
		if(wp.getStartDate()!=null) {
			wp.getTasks().stream().allMatch(x->x.getStartDate().after(wp.getStartDate()));
			b = wp.getStartDate().before(wp.getEndDate());
		}
		return b;
	}
	
	public Boolean validacionFechaFinal(final Workplan wp) {
		Boolean b = false;
		if(wp.getEndDate()!=null) {
			wp.getTasks().stream().allMatch(x->x.getEndDate().before(wp.getEndDate()));
			b = wp.getStartDate().before(wp.getEndDate());
		}
		return b;
	}

	@Override
	public void update(final Request<Workplan> request, final Workplan entity) {
		Workplan wp;
		wp=this.repository.findById(entity.getId());
		wp.setEndDate(entity.getEndDate());
		wp.setPublicPlan(entity.getPublicPlan());
		wp.setStartDate(entity.getStartDate());
		wp.setWorkLoad();
		this.repository.save(wp);
		
		
		
	}

}
