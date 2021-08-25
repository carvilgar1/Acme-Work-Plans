package acme.datatypes;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import acme.framework.datatypes.DomainDatatype;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class WorkLoad extends DomainDatatype{

	protected static final long	serialVersionUID	= 1L;
	
	
	@Min(0)
	@Max(99)
	protected Integer entera;
	
	
	@Min(0)
	@Max(59)
	protected Integer decimal;

	public Double getValorDecimal() {
		return this.entera + 0.1*this.decimal;
	}
	
	@Override
	public String toString() {
		StringBuilder result;

		result = new StringBuilder();
		result.append(this.entera);
		result.append(".");
		result.append(this.decimal);

		return result.toString();
	}
	
	
	
	
}
