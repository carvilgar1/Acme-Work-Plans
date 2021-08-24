package acme.entities.workplans;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Workplan extends DomainEntity{
	
	// Serialisation identifier -----------------------------------------------

			protected static final long	serialVersionUID	= 1L;

			// Attributes -------------------------------------------------------------			
			@Temporal(TemporalType.TIMESTAMP)
			@NotNull
			private Date startDate;
			
			@Temporal(TemporalType.TIMESTAMP)
			@NotNull
			private Date endDate;
			
			@Min(0)
			private Double workLoad;
			
			@NotNull
			private Boolean publicPlan;
			
			// Derived atttributes ----------------------------------------------------
			
			// Relationships
			@Valid

			@ManyToMany(fetch = FetchType.EAGER)
			protected Set<Task> tasks;
			
			@Valid
			@ManyToOne(optional = false)
			protected Manager manager;

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
				result = prime * result + ((this.endDate == null) ? 0 : this.endDate.hashCode());
				result = prime * result + ((this.publicPlan == null) ? 0 : this.publicPlan.hashCode());
				result = prime * result + ((this.startDate == null) ? 0 : this.startDate.hashCode());
				result = prime * result + ((this.tasks == null) ? 0 : this.tasks.hashCode());
				result = prime * result + ((this.workLoad == null) ? 0 : this.workLoad.hashCode());
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
				final Workplan other = (Workplan) obj;
				if (this.endDate == null) {
					if (other.endDate != null)
						return false;
				} else if (!this.endDate.equals(other.endDate))
					return false;
				if (this.publicPlan == null) {
					if (other.publicPlan != null)
						return false;
				} else if (!this.publicPlan.equals(other.publicPlan))
					return false;
				if (this.startDate == null) {
					if (other.startDate != null)
						return false;
				} else if (!this.startDate.equals(other.startDate))
					return false;
				if (this.tasks == null) {
					if (other.tasks != null)
						return false;
				} else if (!this.tasks.equals(other.tasks))
					return false;
				if (this.workLoad == null) {
					if (other.workLoad != null)
						return false;
				} else if (!this.workLoad.equals(other.workLoad))
					return false;
				return true;
			}

			@Override
			public String toString() {
        	return "WorkPlan [startDate=" + this.startDate + ", endDate=" + this.endDate + ", workLoad=" + this.workLoad + ", publicPlan=" + this.publicPlan + ", tasks=" + this.tasks + "]";
			}
			
			public void setWorkLoad() {
				this.workLoad=this.tasks.stream().mapToDouble(Task::getWorkFlow).sum();
			}
  
			public boolean canUpdate() {
				Date now;
				now = new Date(System.currentTimeMillis());
				return this.endDate.after(now);
      }
			
}
