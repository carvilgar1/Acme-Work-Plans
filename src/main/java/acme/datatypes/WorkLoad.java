package acme.datatypes;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import acme.framework.datatypes.DomainDatatype;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class WorkLoad extends DomainDatatype implements Comparable<WorkLoad>{

	protected static final long	serialVersionUID	= 1L;
	
	@NotNull
	@Min(0)
	@Max(99)
	protected Integer entera;
	
	@NotNull
	@Min(0)
	@Max(59)
	protected Integer decimal;

	public Double getValorDecimal() {
		return this.entera + 0.01*this.decimal;
	}
	
	public long getMilliseconds() {
		return this.entera*3600000l + this.decimal*60000l;
	}
	
	public Integer getMinutes() {
		return this.entera*60 + this.decimal;
	}
	
	public static WorkLoad ofMinutes(final Integer minutes) {
		final WorkLoad r = new WorkLoad();
		r.setEntera(minutes/60);
		r.setDecimal(minutes%60);
		return r;
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

	@Override
	public int compareTo(final WorkLoad o) {
		int r = this.getEntera().compareTo(o.getEntera());
		if(r == 0) {
			r = this.getDecimal().compareTo(o.getDecimal());
		}
		return r;
	}
	
	
	
	
}
