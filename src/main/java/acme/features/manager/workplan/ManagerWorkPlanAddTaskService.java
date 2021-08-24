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
public class ManagerWorkPlanAddTaskService implements AbstractUpdateService<Manager, Workplan>{
	
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
		Task t = new Task();
		final Optional<DomainEntity> opTask=this.tasksRepository.findById(request.getModel().getInteger("addTask"));
		if(opTask.isPresent()) t=(Task) opTask.get();
		if(Boolean.TRUE.equals(entity.getPublicPlan())) {
			final Boolean b=t.getPublicTask();
			errors.state(request,b, "addTask", "manager.workplan.error.taskPrivate");
		}
		final Date actual = new Date(System.currentTimeMillis()-1);
		final Boolean b1= t.getStartDate().before(actual);
		errors.state(request, b1, "addTask", "manager.workplan.error.taskStarted");
		
		
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
		
		request.getModel().setAttribute("startDate", wp.getStartDate());
		request.getModel().setAttribute("endDate", wp.getEndDate());
		request.getModel().setAttribute("workLoad", wp.getWorkLoad());
		request.getModel().setAttribute("publicPlan", wp.getPublicPlan());
		request.getModel().setAttribute("tasks",tasks);
	}
	
	

	@SuppressWarnings("deprecation")
	@Override
	public void update(final Request<Workplan> request, final Workplan entity) {
		Set<Task> tasks;
		Workplan wp;
		wp=this.repository.findById(entity.getId());
	
		Task t = new Task();
		final Optional<DomainEntity> opTask=this.tasksRepository.findById(request.getModel().getInteger("addTask"));
		if(opTask.isPresent()) t=(Task) opTask.get();
		tasks=entity.getTasks();
		tasks.add(t);
		
		if(t.getStartDate().before(wp.getStartDate())) {
			final Date newStartDate = t.getStartDate();
			newStartDate.setDate(newStartDate.getDate()-1);
			newStartDate.setHours(8);
			newStartDate.setMinutes(0);
			wp.setStartDate(newStartDate);
		}
		
		if(t.getEndDate().after(wp.getEndDate())) {
			final Date newEndDate = t.getEndDate();
			newEndDate.setDate(newEndDate.getDate()+1);
			newEndDate.setHours(17);
			newEndDate.setMinutes(0);
			wp.setEndDate(newEndDate);
		}
		wp.setTasks(tasks);
		wp.setWorkLoad();
		this.repository.save(wp);
	}

}
