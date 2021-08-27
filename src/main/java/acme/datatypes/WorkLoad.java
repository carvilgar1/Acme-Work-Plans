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

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	
	@NotNull
	@Min(0)
	protected Integer entera;
	
	@NotNull
	@Min(0)
	@Max(59)
	protected Integer decimal;

	public long getMilliseconds() {
		return this.entera*3600000l + this.decimal*60000l;
	}
	
	public Integer getMinutes() {
		return this.entera*60 + this.decimal;
	}
	
	public void addWorkLoad(final WorkLoad o) {
		final Integer horas = this.getEntera() + o.getEntera() + o.getDecimal()/60;
		final Integer minutos = this.getDecimal() + o.getDecimal()%60;
		this.setEntera(horas);
		this.setDecimal(minutos);
	}
	
	public void removeWorkLoad(final WorkLoad o) {
		final Integer horas = this.getEntera() - o.getEntera() - o.getDecimal()/60;
		final Integer minutos = this.getDecimal() - o.getDecimal()%60;
		this.setEntera(horas);
		this.setDecimal(minutos);
	}
	
	public static WorkLoad ofMinutes(final double minutes) {
		final WorkLoad r = new WorkLoad();
		r.setEntera((int)minutes/60);
		r.setDecimal((int)minutes%60);
		return r;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.decimal == null) ? 0 : this.decimal.hashCode());
		result = prime * result + ((this.entera == null) ? 0 : this.entera.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final WorkLoad other = (WorkLoad) obj;
		if (this.decimal == null) {
			if (other.decimal != null)
				return false;
		} else if (!this.decimal.equals(other.decimal))
			return false;
		if (this.entera == null) {
			if (other.entera != null)
				return false;
		} else if (!this.entera.equals(other.entera))
			return false;
		return true;
	}
	
	@Override
	public int compareTo(final WorkLoad o) {
		int r = this.getEntera().compareTo(o.getEntera());
		if(r==0) {
			r = this.getDecimal().compareTo(o.getDecimal());
		}
		return r;
	}
	
	@Override
	public String toString() {
		StringBuilder result;

		result = new StringBuilder();
		result.append(String.format("%02d", this.entera));
		result.append(":");
		result.append(String.format("%02d", this.decimal));

		return result.toString();
	}

	
	
}
