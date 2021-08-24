package acme.testing.manager.workplan;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import acme.testing.AcmePlannerTest;

public class ManagerWorkPlanDeleteTaskTest extends AcmePlannerTest{
	
	//En este test se va a probar el caso positivo de borrar una tarea a un work plan, 
	//para ello se verificara la carga de trabajo,
	//se borrara una tarea y verificaremos la nueva carga de trabajo ya que se actualiza automaticamente
	@ParameterizedTest
	@CsvFileSource(resources="/manager/workplan/deleteTask-positive.csv", encoding="utf-8", numLinesToSkip=1)
	@Order(10)
	public void updatePositive(final int recordIndex, final int taskIndex, final String workLoad, final String newWorkLoad) {
		//Iniciamos sesion como manager y accedemos al formulario del plan de trabajo dado en los parametros
		super.signIn("manager2", "manager2");
		super.clickOnMenu("Manager", "Works Plans");
		super.clickOnListingRecord(recordIndex);
		//Comprobamos la carga de trabajo
		super.checkInputBoxHasValue("workLoad", workLoad);
		//Borramos la tarea dada por el parametro
		final String xpath="//tr[" + taskIndex + "]/td[4]/form/button";
		super.clickAndGo(By.xpath(xpath));
		//Verificamos la nueva carga de trabajo
		super.clickOnListingRecord(recordIndex);
		super.checkInputBoxHasValue("workLoad", newWorkLoad);
	}
	
	
	//En este test se intentara acceder al formulario para poder borrar una tarea sin tener permisos,
	//para comprobar que este no puede acceder verificaremos que salta un error al intentar acceder
	@Test
	@Order(20)
	public void updateNegative() {
		//Iniciamos sesion
		super.signIn("manager2", "manager2");
		//Intetamos acceder a un formlario de un plan de trabajo que no es nuestro
		super.driver.get("http://localhost:8080/Acme-Planner/manageracc/workplan/show?id=42");
		//Verificamos que ha saltado un error
		super.checkErrorsExist();
	}
	
	
}
