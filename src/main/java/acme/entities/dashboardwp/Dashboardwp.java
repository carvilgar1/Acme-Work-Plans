package acme.entities.dashboardwp;

import java.util.Collection;
import java.util.function.DoubleUnaryOperator;

import javax.persistence.Entity;
import javax.persistence.Transient;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Dashboardwp extends DomainEntity{
		// Serialisation identifier -----------------------------------------------

		protected static final long	serialVersionUID	= 1L;

		// Attributes -------------------------------------------------------------
		private Integer publicWorkPlans;
		private Integer privateWorkPlans;
		private Integer finishedWorkPlans;
		private Integer nonFinishedWorkPlans;
		private Double averageWorkLoad;
		private Double deviationWorkLoad;
		private Double maxWorkLoad;
		private Double minWorkLoad;
		private Double averageExecutionPeriod;
		private Double deviationExecutionPeriod;
		private Double maxExecutionPeriod;
		private Double minExecutionPeriod;
		
		private static Double g(final Double i) {
			if(i == null) {
				return 0.;
			}else {
				return i;
			}
		}
		
		public static Double deviation(final Collection<Double> data) {
			final int n = data.size();
			final double avg = data.stream().mapToDouble(Dashboardwp::g).average().getAsDouble();
			final DoubleUnaryOperator f = x-> Math.pow(x - avg, 2);
			return Math.sqrt(data.stream().mapToDouble(x->f.applyAsDouble(Dashboardwp.g(x))).sum()/n);
		}
		// Derived atttributes ----------------------------------------------------

		@Override
		@Transient
		public boolean isTransient() {
			boolean result;

			result = this.id == 0;

			return result;
		}
		
		// Object interface -------------------------------------------------------
}
