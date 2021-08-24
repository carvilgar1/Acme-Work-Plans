package acme.testing.anonymous.task;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AnonymousTaskListAllTest extends AcmePlannerTest{
	
	//En este test se prueba la funcionalidad list y show de las tasks propias de una persona anónima. El resultado esperado es que se listen
	//las tasks y se muestren sus detalles.
	//No se infringe ninguna restricción.
	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/task/list-all.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void listPositive(final int recordIndex, final String title, final String startDate, final String endDate, final String workFlow, final String description, final String publicTask,final String url) {
		
		super.clickOnMenu("Anonymous", "Tasks");
		
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, startDate);
		super.checkColumnHasValue(recordIndex, 2, endDate);
		super.checkColumnHasValue(recordIndex, 3, description);
		super.checkColumnHasValue(recordIndex, 4, url);
		
		super.clickOnListingRecord(recordIndex);
		
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("workFlow",workFlow);
		super.checkInputBoxHasValue("description",description);
		super.checkInputBoxHasValue("publicTask",publicTask);
		
	}
	
	//Aquí se prueba la funcionalidad list de tasks pero de forma negativa. El resultado esperado es
	//un error de acceso denegado ya que se está accediendo con un rol de usuario no autorizado que no tiene acceso
	//a la funcionalidad.
	@ParameterizedTest
	@CsvFileSource(resources="/anonymous/task/users.csv", encoding="utf-8", numLinesToSkip=1)
	@Order(20)
	public void listNegative(final String username, final String password) {
		if(username!=null) this.signIn(username, password);
		super.driver.get("http://localhost:8080/Acme-Planner/anonymous/task/list");
		super.checkErrorsExist();
		if(username!=null) super.signOut();
	}
		
	

}
