package acme.entities.spamfilter;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Spamword extends DomainEntity{

	
	protected static final long serialVersionUID = 1L;
	
	@NotBlank
	protected String spamword;
	
}
