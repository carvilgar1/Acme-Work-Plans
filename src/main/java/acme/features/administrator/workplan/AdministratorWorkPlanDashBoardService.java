package acme.features.administrator.workplan;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.dashboardwp.Dashboardwp;
import acme.entities.workplans.Workplan;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractListService;

@Service
public class AdministratorWorkPlanDashBoardService implements AbstractListService<Administrator, Dashboardwp>{

	@Autowired
	AdministratorWorkPlanDashBoardRepository repository;


	@Override
	public boolean authorise(final Request<Dashboardwp> request) {
		assert request != null;
		return true;
	}

	@Override
	public void unbind(final Request<Dashboardwp> request, final Dashboardwp entity, final Model model) {
		assert request!=null;
		assert entity!=null;
		assert model!=null;
		
		
		request.unbind(entity, model, 
		"publicWorkPlans",
		"privateWorkPlans",
		"finishedWorkPlans",
		"nonFinishedWorkPlans",
		"averageWorkLoad",
		"deviationWorkLoad",
		"maxWorkLoad",
		"minWorkLoad",
		"averageExecutionPeriod",
		"deviationExecutionPeriod",
		"maxExecutionPeriod",
		"minExecutionPeriod");
		
	}

	@Override
	public Collection<Dashboardwp> findMany(final Request<Dashboardwp> request) {
		Collection<Dashboardwp> r;
		r = new ArrayList<>();
		final Collection<Workplan> list = this.repository.findAllWorkPlan();
		if(!list.isEmpty()) {
			Dashboardwp test;
			test = new Dashboardwp();
			
			test.setPublicWorkPlans(this.repository.numberOfPublicWorkPlan());
			test.setPrivateWorkPlans(this.repository.numberOfNonPublicWorkPlan());
			test.setFinishedWorkPlans(this.repository.numberOfFinishedWorkPlan());
			test.setNonFinishedWorkPlans(this.repository.numberOfNonFinishedWorkPlan());
			
			test.setAverageWorkLoad(this.repository.averageWorkFlow());
			test.setDeviationWorkLoad(Dashboardwp.deviation(this.repository.findAllWorkFlows()));
			test.setMaxWorkLoad(this.repository.maxWorkFlow());
			test.setMinWorkLoad(this.repository.minWorkFlow());
			
			test.setAverageExecutionPeriod(this.repository.averagePeriod());
			test.setDeviationExecutionPeriod(Dashboardwp.deviation(this.repository.findAllPeriods()));
			test.setMaxExecutionPeriod(this.repository.maxPeriod());
			test.setMinExecutionPeriod(this.repository.minPeriod());
			r.add(test);
		}else {		
			Dashboardwp test;
			test = new Dashboardwp();
			
			test.setPublicWorkPlans(0);
			test.setPrivateWorkPlans(0);
			test.setFinishedWorkPlans(0);
			test.setNonFinishedWorkPlans(0);
			
			test.setAverageWorkLoad(0.);
			test.setDeviationWorkLoad(0.);
			test.setMaxWorkLoad(0.);
			test.setMinWorkLoad(0.);
			
			test.setAverageExecutionPeriod(0.);
			test.setDeviationExecutionPeriod(0.);
			test.setMaxExecutionPeriod(0.);
			test.setMinExecutionPeriod(0.);
			r.add(test);
		}
		return r;
	}
	
}
