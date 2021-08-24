package acme.features.manager.workplan;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.entities.workplans.Workplan;
import acme.features.manager.task.ManagerMyTasksRepository;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractShowService;

@Service
public class ManagerWorkPlanShowService implements AbstractShowService<Manager, Workplan>{

	@Autowired
	ManagerWorkPlanRepository repository;
	
	@Autowired
	ManagerMyTasksRepository tasksRepository;
	
	@Override
	public boolean authorise(final Request<Workplan> request) {
		assert request!=null;
		final int id=request.getModel().getInteger("id");
		final Workplan wp=this.repository.findById(id);
		final int managerId= request.getPrincipal().getActiveRoleId();
		return wp.getManager().getId()==managerId;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void unbind(final Request<Workplan> request, final Workplan entity, final Model model) {
		assert request!=null;
		assert entity!=null;
		assert model!=null;
		
		final int id=request.getModel().getInteger("id");
		final Workplan wp=this.repository.findById(id);
		final Collection<Task>t;
		
		if(!wp.getTasks().isEmpty()) {
		final Date startRecommend=wp.getTasks().stream().map(Task::getStartDate).min((x,y)->x.compareTo(y)).orElse(new Date());
		startRecommend.setDate(startRecommend.getDate()-1);
		startRecommend.setHours(8);
		startRecommend.setMinutes(0);
		
		final Date finalRecommend=wp.getTasks().stream().map(Task::getEndDate).max((x,y)->x.compareTo(y)).orElse(new Date());
		finalRecommend.setDate(finalRecommend.getDate()+1);
		finalRecommend.setHours(17);
		finalRecommend.setMinutes(0);
		
		
//		finalRecommend.toString().replaceAll("-","/");
		model.setAttribute("startRecommend", startRecommend);
		model.setAttribute("finalRecommend", finalRecommend);
		}
		if(Boolean.TRUE.equals(wp.getPublicPlan()))t=this.tasksRepository.findMyPublicTasks(wp.getManager().getId());
		else t=this.tasksRepository.findMyTasks(wp.getManager().getId());
		t.stream().filter(x->!wp.getTasks().contains(x)).collect(Collectors.toSet());
		
		model.setAttribute("tasksInsert", t);
		if(wp.getEndDate()!=null)model.setAttribute("canUpdate", wp.canUpdate());
		else model.setAttribute("canUpdate",true);
		request.unbind(entity, model, "startDate", "endDate", "workLoad", "publicPlan", "tasks");
	}

	@Override
	public Workplan findOne(final Request<Workplan> request) {
		assert request!=null;
		final int id=request.getModel().getInteger("id");
		Workplan result;
		result=this.repository.findById(id);
		return result;
	}

}
