package acme.testing.manager.task;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class ManagerMyTasksDeleteTest extends AcmePlannerTest {
	
	public Integer i = 0;
	
	//En este test se prueba la funcionalidad eliminar una task propia de un manager. Además, también se vuelve
	//a probar el listado y el show.
	//El resultado esperado es que se liste la task, se haga click en ella para mostrar todos sus campos, se haga
	//click en el botón Delete y, con esto, se borre la task.
	//No se infringe ninguna restricción.
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/list.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void deletePositive(final int recordIndex, final String title, final String startDate, 
			final String endDate, final String workFlow, final String description, final String publicTask, final String url) {
		super.signIn("manager2", "manager2");
		
		super.clickOnMenu("Manager", "Own tasks");
		
		super.checkColumnHasValue(recordIndex-this.i, 0, title);
		super.checkColumnHasValue(recordIndex-this.i, 1, startDate);
		super.checkColumnHasValue(recordIndex-this.i, 2, endDate);
		super.checkColumnHasValue(recordIndex-this.i, 3, description);
		
		super.clickOnListingRecord(recordIndex-this.i);
		
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("workFlow", workFlow);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("publicTask", publicTask);
		super.checkInputBoxHasValue("url", url);
		
		super.clickOnSubmitButton("Delete");
		
		this.i++;
		
		super.signOut();
	}
	

	
	//Aquí se prueba la funcionalidad delete de tasks pero de forma negativa. El resultado esperado es
	//un error de acceso denegado ya que se está accediendo con un rol usuario no autorizado que no tiene acceso
	//ni a la funcionalidad ni a la task con id 23 (que pertenece al manager2)
	@ParameterizedTest
	@CsvFileSource(resources="/manager/task/users.csv", encoding="utf-8", numLinesToSkip=1)
	@Order(30)
	public void deleteNegative(final String username, final String password) {
		if(username!=null) this.signIn(username, password);
		super.driver.get("http://localhost:8080/Acme-Planner/manageracc/task/delete?id=23");
		super.checkPanicExists();
		if(username!=null) super.signOut();
	}
	
	//Se vuelve a probar la funcionalidad delete de tasks pero, ahora, accediendo a través de la URL.
	//Se espera que se acceda a la vista del show de la tarea con id 23 para luego hacer click en el
	//botón delete sin ningún error.
	//No se infringe ninguna restricción.
	@Test
	@Order(40)
	public void deletePositive() {
		super.signIn("manager3", "manager3");
		super.driver.get("http://localhost:8080/Acme-Planner/manageracc/task/delete?id=18");
		super.clickOnSubmitButton("Delete");
		super.checkNotPanicExists();
		super.signOut();
	}


}
