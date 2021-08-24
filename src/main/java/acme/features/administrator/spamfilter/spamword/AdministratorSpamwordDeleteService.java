package acme.features.administrator.spamfilter.spamword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.spamfilter.Spamword;
import acme.features.administrator.spamfilter.AdministratorSpamFilterRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.entities.Principal;
import acme.framework.entities.UserRole;
import acme.framework.services.AbstractDeleteService;

@Service
public class AdministratorSpamwordDeleteService implements AbstractDeleteService<Administrator, Spamword>{

	@Autowired
	protected AdministratorSpamFilterRepository repository;



	@Override
	public boolean authorise(final Request<Spamword> request) {
		assert request != null;

		final Principal p = request.getPrincipal();
		final Class<? extends UserRole> a = p.getActiveRole();
		return  a.equals(Administrator.class);
	}

	@Override
	public void bind(final Request<Spamword> request, final Spamword entity, final Errors errors) {
		assert request != null && entity !=null && errors!=null;
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
	public Spamword findOne(final Request<Spamword> request) {
		assert request != null;

		Spamword result;
		int id;
		Model m;

		m = request.getModel();
		id = m.getInteger("id");
		result = this.repository.findSpamwordById(id);

		return result;
	}

	@Override
	public void validate(final Request<Spamword> request, final Spamword entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void delete(final Request<Spamword> request, final Spamword entity) {
		assert request != null;
		assert entity != null;

		this.repository.delete(entity);
	}
}
