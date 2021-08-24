package acme.features.administrator.spamfilter.spamword;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.shouts.Shout;
import acme.entities.spamfilter.Spamword;
import acme.entities.tasks.Task;
import acme.features.administrator.spamfilter.AdministratorSpamFilterRepository;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractListService;

@Service
public class AdministratorSpamwordListService implements AbstractListService<Administrator, Spamword>{

	
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
	public Collection<Spamword> findMany(final Request<Spamword> request) {

		return this.repository.getSpamwords();
	}
	
	
	public Boolean filtro(final Shout shout) {
		final List<Spamword> spamwords = this.repository.getSpamwords().stream().collect(Collectors.toList());
		final Double umbral;
		if(!spamwords.isEmpty()) {
			
		if(!this.repository.getThreshold().isEmpty()) {
			umbral = this.repository.getThreshold().stream().collect(Collectors.toList()).get(0).getValue();
		}else {
			umbral = 10.0;
		}
		
		final String autor = shout.getAuthor().toLowerCase();
		final String mensaje = shout.getText().toLowerCase();
		final Integer countCompuestasAutor = this.compruebaSpamWordsCompuestas(spamwords, autor);
		final Integer countCompuestasMensaje = this.compruebaSpamWordsCompuestas(spamwords, mensaje);
		final Integer countAutor = this.compruebaSpamWords(spamwords, autor);
		final Integer countMensaje = this.compruebaSpamWords(spamwords, mensaje);
		final Integer count = countAutor + countMensaje + countCompuestasAutor + countCompuestasMensaje;
		final Integer cantPalabras = autor.replaceAll("\\s{2,}", " ").split(" ").length + mensaje.replaceAll("\\s{2,}", " ").split(" ").length;
		final Double operacion = (double)count/cantPalabras*100.0;
		if(operacion >= umbral) {
			return false;
		}else {
			return true;
		}
		}else {
			return true;
		}
		
	}

	
	public Integer compruebaSpamWordsCompuestas(final List<Spamword> spamwords, final String texto) {
		Integer count = 0;
		final String[] t = texto.replaceAll("\\s{2,}", " ").split(" ");
		final List<Spamword> swCompuestas = this.escogeCompuestas(spamwords);

		for(final Spamword sw : swCompuestas) {
			final String[] s = sw.getSpamword().split(" ");
			for(Integer i=0; i<t.length-1;i++) {
				if(s[0].equals(t[i]) && s[1].equals(t[i+1])) {
					count++;
				}
			}
		}
		return count;
	}
	
	public List<Spamword> escogeCompuestas(final List<Spamword> spamwords){
		final List<Spamword> swCompuestas = new ArrayList<Spamword>();
		for(Integer i=0; i<spamwords.size();i++) {
			final Spamword sw  = spamwords.get(i);
			if(sw.getSpamword().split(" ").length > 1) {
				swCompuestas.add(sw);
			}
		}
		
		return swCompuestas;
	}
  
	public Integer compruebaSpamWords(final List<Spamword> spamwords, final String texto) {
		Integer count = 0;
		final String[] t = texto.replaceAll("\\s{2,}", " ").split(" ");
		final List<Spamword> swCompuestas = this.escogeCompuestas(spamwords);

		for(final Spamword sw : spamwords) {
			if(!swCompuestas.contains(sw)) {
				final String sws = sw.getSpamword();
				for(Integer i=0; i<t.length;i++) {
					if(sws.equals(t[i])) {
						count++;
					}
				}
			}
		}
		return count;
	}
	
    public Boolean filtroTasks(final Task task) {
    	final List<Spamword> spamwords = this.repository.getSpamwords().stream().collect(Collectors.toList());
    	final Double umbral;
    	if(!spamwords.isEmpty()) {
			if(!this.repository.getThreshold().isEmpty()) {
				umbral = this.repository.getThreshold().stream().collect(Collectors.toList()).get(0).getValue();
			}else {
				umbral = 10.0;
			}
			final String title = task.getTitle().toLowerCase();
			final String description = task.getDescription().toLowerCase();
			final Integer countCompuestasAutor = this.compruebaSpamWordsCompuestas(spamwords, title);
			final Integer countCompuestasMensaje = this.compruebaSpamWordsCompuestas(spamwords, description);
			final Integer countAutor = this.compruebaSpamWords(spamwords, title);
			final Integer countMensaje = this.compruebaSpamWords(spamwords, description);
			final Integer count = countAutor + countMensaje + countCompuestasAutor + countCompuestasMensaje;
			final Integer cantPalabras = title.replaceAll("\\s{2,}", " ").split(" ").length + description.replaceAll("\\s{2,}", " ").split(" ").length;
			final Double operacion = (double)count/cantPalabras*100.0;
			if(operacion >= umbral) {
				return false;
			}else {
				return true;
			}
    	}else {
    		return true;
    	}
    }
}
