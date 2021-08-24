package acme.features.manager.workplan;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.workplans.Workplan;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractListService;

@Service
public class ManagerWorkPlanListService implements AbstractListService<Manager, Workplan> {

	@Autowired
	ManagerWorkPlanRepository repository;

	@Override
	public boolean authorise(final Request<Workplan> request) {
		assert request != null;
		return true;
	}

	@Override
	public void unbind(final Request<Workplan> request, final Workplan entity, final Model model) {
		assert request!=null;
		assert entity!=null;
		assert model!=null;
		
		request.unbind(entity, model, "startDate", "endDate", "workLoad");
	}

	@Override
	public Collection<Workplan> findMany(final Request<Workplan> request) {
		assert request!=null;
		final Collection<Workplan> result;
		final int id=request.getPrincipal().getActiveRoleId();
		result=this.repository.findByManagerId(id);
		return result;
	}
	
	
	
}
