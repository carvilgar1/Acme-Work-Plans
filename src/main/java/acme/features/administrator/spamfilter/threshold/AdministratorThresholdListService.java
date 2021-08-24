package acme.features.administrator.spamfilter.threshold;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.spamfilter.Threshold;
import acme.features.administrator.spamfilter.AdministratorSpamFilterRepository;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractListService;

@Service
public class AdministratorThresholdListService implements AbstractListService<Administrator, Threshold>{

	@Autowired
	AdministratorSpamFilterRepository repository;
	
	@Override
	public boolean authorise(final Request<Threshold> request) {
		assert request != null;
		return true;
	}

	@Override
	public void unbind(final Request<Threshold> request, final Threshold entity, final Model model) {
		assert request!=null;
		assert entity!=null;
		assert model!=null;
		
		request.unbind(entity, model, "value");
	}

	@Override
	public Collection<Threshold> findMany(final Request<Threshold> request) {
		assert request != null;

		return  this.repository.getThreshold();
	}

}
