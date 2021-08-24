package acme.features.manager.workplan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.workplans.Workplan;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractDeleteService;

@Service
public class ManagerWorkPlanDeleteService implements AbstractDeleteService<Manager, Workplan> {

	@Autowired
	ManagerWorkPlanRepository repository;
	
	@Override
	public boolean authorise(final Request<Workplan> request) {
		assert request!=null;
		Workplan wp;
		int id;
		int managerId;
		Boolean result;
		
		id=request.getModel().getInteger("id");
		wp=this.repository.findById(id);
		managerId= request.getPrincipal().getActiveRoleId();
		result=wp.getManager().getId()==managerId;
		return result;
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
		assert request != null;
		
		int id;
		Workplan result;
		
		id = request.getModel().getInteger("id");
		result=this.repository.findById(id);
		return result;
	}

	@Override
	public void validate(final Request<Workplan> request, final Workplan entity, final Errors errors) {
		assert request != null && entity != null && errors != null;
	}

	@Override
	public void delete(final Request<Workplan> request, final Workplan entity) {
		assert request != null && entity!=null;
		this.repository.delete(entity);
	}
	

}
