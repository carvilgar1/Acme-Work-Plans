package acme.features.administrator.spamfilter.spamword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.spamfilter.Spamword;
import acme.features.administrator.spamfilter.AdministratorSpamFilterRepository;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorSpamwordShowService implements AbstractShowService<Administrator, Spamword> {

	
	@Autowired
	AdministratorSpamFilterRepository repository;
	
	@Override
	public boolean authorise(final Request<Spamword> request) {
		assert request != null;
		return true;
	}

	@Override
	public void unbind(final Request<Spamword> request, final Spamword entity, final Model model) {
		assert request!=null;
		assert entity!=null;
		assert model!=null;
		
		request.unbind(entity, model, "spamword");
	}

	@Override
	public Spamword findOne(final Request<Spamword> request) {
		assert request != null;

		Spamword result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findSpamwordById(id);

		return result;
	}
}
