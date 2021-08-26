package acme.datatypes;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkLoad{
	
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
	
	public Double getValorEnHoras() {
		return this.entera + (double)this.decimal/60;
	}
	
	public static WorkLoad workLoadOfDouble(final Double d) {
		final WorkLoad r = new WorkLoad();
		final String[] values = String.format("%,.2f", d).split("\\.");
		r.setEntera(Integer.valueOf(values[0]));
		r.setDecimal(Integer.valueOf(values[1]));
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
	
	
	
	
}
