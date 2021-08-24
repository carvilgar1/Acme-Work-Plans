package acme.features.administrator.spamfilter;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.entities.spamfilter.Spamword;
import acme.entities.spamfilter.Threshold;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorSpamFilterRepository extends AbstractRepository{
	
	
	@Query("SELECT sw FROM Spamword sw ORDER BY sw.id DESC")
	Collection<Spamword> getSpamwords();
	
	@Query("SELECT th FROM Threshold th")
	Collection<Threshold> getThreshold();
	
	
	@Query("SELECT sw FROM Spamword sw WHERE sw.id =:id")
	Spamword findSpamwordById(@Param("id") Integer id);
}
