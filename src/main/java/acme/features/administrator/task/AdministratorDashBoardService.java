package acme.features.administrator.task;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.WorkLoad;
import acme.entities.dashboard.Dashboard;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractListService;

@Service
public class AdministratorDashBoardService implements AbstractListService<Administrator, Dashboard>{

	@Autowired
	AdministratorDashBoardRepository repository;

	@Override
	public boolean authorise(final Request<Dashboard> request) {
		assert request != null;
		return true;
	}

	@Override
	public void unbind(final Request<Dashboard> request, final Dashboard entity, final Model model) {
		assert request!=null;
		assert entity!=null;
		assert model!=null;
		
		request.unbind(entity, model, 
		"publicTasks",
		"privateTasks",
		"finishedTasks",
		"nonFinishedTasks",
		"averageWorkFlow",
		"deviationWorkFlow",
		"maxWorkFlow",
		"minWorkFlow",
		"averageExecutionPeriod",
		"deviationExecutionPeriod",
		"maxExecutionPeriod",
		"minExecutionPeriod");
	}


	@Override
	public Collection<Dashboard> findMany(final Request<Dashboard> request) {
		Collection<Dashboard> r;
		r = new ArrayList<>();
		
		if(!this.repository.findAllTasks().isEmpty()) {
			Dashboard test;
			test = new Dashboard();
			
			test.setPublicTasks(this.repository.numberOfPublicTasks());
			test.setPrivateTasks(this.repository.numberOfNonPublicTasks());
			test.setFinishedTasks(this.repository.numberOfFinishedTasks());
			test.setNonFinishedTasks(this.repository.numberOfNonFinishedTasks());

			test.setAverageWorkFlow(WorkLoad.ofMinutes(this.repository.averageWorkFlow()).toString());
			test.setDeviationWorkFlow(WorkLoad.ofMinutes(Dashboard.deviation(
				this.repository.findAllWorkFlows())).toString());
			test.setMaxWorkFlow(WorkLoad.ofMinutes(this.repository.maxWorkFlow()).toString());
			test.setMinWorkFlow(WorkLoad.ofMinutes(this.repository.minWorkFlow()).toString());
			
			test.setAverageExecutionPeriod(this.repository.averagePeriod());
			test.setDeviationExecutionPeriod(Dashboard.deviation(this.repository.findAllPeriods()));
			test.setMaxExecutionPeriod(this.repository.maxPeriod());
			test.setMinExecutionPeriod(this.repository.minPeriod());

			
			r.add(test);
		}else{
			Dashboard test;
			test = new Dashboard();
			
			test.setPublicTasks(0);
			test.setPrivateTasks(0);
			test.setFinishedTasks(0);
			test.setNonFinishedTasks(0);
			
			test.setAverageWorkFlow("00:00");
			test.setDeviationWorkFlow("00:00");
			test.setMaxWorkFlow("00:00");
			test.setMinWorkFlow("00:00");
			
			test.setAverageExecutionPeriod(0.);
			test.setDeviationExecutionPeriod(0.);
			test.setMaxExecutionPeriod(0.);
			test.setMinExecutionPeriod(0.);
			
			r.add(test);
		}
		
		return r;
	}
	
	
	
}
