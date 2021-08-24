package acme.entities.dashboard;

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
public class Dashboard extends DomainEntity{
		// Serialisation identifier -----------------------------------------------

		protected static final long	serialVersionUID	= 1L;

		// Attributes -------------------------------------------------------------
		private Integer publicTasks;
		private Integer privateTasks;
		private Integer finishedTasks;
		private Integer nonFinishedTasks;
		private Double averageWorkFlow;
		private Double deviationWorkFlow;
		private Double maxWorkFlow;
		private Double minWorkFlow;
		private Double averageExecutionPeriod;
		private Double deviationExecutionPeriod;
		private Double maxExecutionPeriod;
		private Double minExecutionPeriod;
		
		public static Double deviation(final Collection<Double> data) {
			final int n = data.size();
			final double avg = data.stream().mapToDouble(x->x).average().getAsDouble();
			final DoubleUnaryOperator f = x-> Math.pow(x - avg, 2);
			return Math.sqrt(data.stream().mapToDouble(f::applyAsDouble).sum()/n);
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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + ((this.averageExecutionPeriod == null) ? 0 : this.averageExecutionPeriod.hashCode());
			result = prime * result + ((this.averageWorkFlow == null) ? 0 : this.averageWorkFlow.hashCode());
			result = prime * result + ((this.deviationExecutionPeriod == null) ? 0 : this.deviationExecutionPeriod.hashCode());
			result = prime * result + ((this.deviationWorkFlow == null) ? 0 : this.deviationWorkFlow.hashCode());
			result = prime * result + ((this.finishedTasks == null) ? 0 : this.finishedTasks.hashCode());
			result = prime * result + ((this.maxExecutionPeriod == null) ? 0 : this.maxExecutionPeriod.hashCode());
			result = prime * result + ((this.maxWorkFlow == null) ? 0 : this.maxWorkFlow.hashCode());
			result = prime * result + ((this.minExecutionPeriod == null) ? 0 : this.minExecutionPeriod.hashCode());
			result = prime * result + ((this.minWorkFlow == null) ? 0 : this.minWorkFlow.hashCode());
			result = prime * result + ((this.nonFinishedTasks == null) ? 0 : this.nonFinishedTasks.hashCode());
			result = prime * result + ((this.privateTasks == null) ? 0 : this.privateTasks.hashCode());
			result = prime * result + ((this.publicTasks == null) ? 0 : this.publicTasks.hashCode());
			return result;
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (!super.equals(obj))
				return false;
			if (this.getClass() != obj.getClass())
				return false;
			final Dashboard other = (Dashboard) obj;
			if (this.averageExecutionPeriod == null) {
				if (other.averageExecutionPeriod != null)
					return false;
			} else if (!this.averageExecutionPeriod.equals(other.averageExecutionPeriod))
				return false;
			if (this.averageWorkFlow == null) {
				if (other.averageWorkFlow != null)
					return false;
			} else if (!this.averageWorkFlow.equals(other.averageWorkFlow))
				return false;
			if (this.deviationExecutionPeriod == null) {
				if (other.deviationExecutionPeriod != null)
					return false;
			} else if (!this.deviationExecutionPeriod.equals(other.deviationExecutionPeriod))
				return false;
			if (this.deviationWorkFlow == null) {
				if (other.deviationWorkFlow != null)
					return false;
			} else if (!this.deviationWorkFlow.equals(other.deviationWorkFlow))
				return false;
			if (this.finishedTasks == null) {
				if (other.finishedTasks != null)
					return false;
			} else if (!this.finishedTasks.equals(other.finishedTasks))
				return false;
			if (this.maxExecutionPeriod == null) {
				if (other.maxExecutionPeriod != null)
					return false;
			} else if (!this.maxExecutionPeriod.equals(other.maxExecutionPeriod))
				return false;
			if (this.maxWorkFlow == null) {
				if (other.maxWorkFlow != null)
					return false;
			} else if (!this.maxWorkFlow.equals(other.maxWorkFlow))
				return false;
			if (this.minExecutionPeriod == null) {
				if (other.minExecutionPeriod != null)
					return false;
			} else if (!this.minExecutionPeriod.equals(other.minExecutionPeriod))
				return false;
			if (this.minWorkFlow == null) {
				if (other.minWorkFlow != null)
					return false;
			} else if (!this.minWorkFlow.equals(other.minWorkFlow))
				return false;
			if (this.nonFinishedTasks == null) {
				if (other.nonFinishedTasks != null)
					return false;
			} else if (!this.nonFinishedTasks.equals(other.nonFinishedTasks))
				return false;
			if (this.privateTasks == null) {
				if (other.privateTasks != null)
					return false;
			} else if (!this.privateTasks.equals(other.privateTasks))
				return false;
			if (this.publicTasks == null) {
				if (other.publicTasks != null)
					return false;
			} else if (!this.publicTasks.equals(other.publicTasks))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Dashboard [publicTasks=" + this.publicTasks + ", privateTasks=" + this.privateTasks + ", finishedTasks=" + this.finishedTasks + ", nonFinishedTasks=" + this.nonFinishedTasks + ", averageWorkFlow=" + this.averageWorkFlow + ", deviationWorkFlow="
				+ this.deviationWorkFlow + ", maxWorkFlow=" + this.maxWorkFlow + ", minWorkFlow=" + this.minWorkFlow + ", averageExecutionPeriod=" + this.averageExecutionPeriod + ", deviationExecutionPeriod=" + this.deviationExecutionPeriod + ", maxExecutionPeriod="
				+ this.maxExecutionPeriod + ", minExecutionPeriod=" + this.minExecutionPeriod + "]";
		}		
		
		
		
}
