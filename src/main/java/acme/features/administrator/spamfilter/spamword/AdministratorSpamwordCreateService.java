package acme.features.administrator.spamfilter.spamword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.spamfilter.Spamword;
import acme.features.administrator.spamfilter.AdministratorSpamFilterRepository;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.entities.Administrator;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractCreateService;

@Service
public class AdministratorSpamwordCreateService implements AbstractCreateService<Administrator, Spamword> {

	@Autowired
	protected AdministratorSpamFilterRepository repository;
	

	// AbstractCreateService<Employer, Job> interface -------------------------


	@Override
	public boolean authorise(final Request<Spamword> request) {
		assert request != null;

		return true;
	}

	@Override
	public void validate(final Request<Spamword> request, final Spamword entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void bind(final Request<Spamword> request, final Spamword entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Spamword> request, final Spamword entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "spamword");
		
	}

	@Override
	public Spamword instantiate(final Request<Spamword> request) {
		assert request != null;		

		return new Spamword();
	}

	@Override
	public void create(final Request<Spamword> request, final Spamword entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}
	
	@Override
	public void onSuccess(final Request<Spamword> request, final Response<Spamword> response) {
		assert request != null;
		assert response != null;

		if (request.isMethod(HttpMethod.POST)) {
			PrincipalHelper.handleUpdate();
		}
	}
}
