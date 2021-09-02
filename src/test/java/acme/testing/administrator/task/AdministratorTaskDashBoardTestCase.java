package acme.testing.administrator.task;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeWorkPlansTest;

public class AdministratorTaskDashBoardTestCase extends AcmeWorkPlansTest{
	
	/*En el siguiente test se provara que los valores numericos devueltos por el servicio sean correctos*/
	
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/task/task-dashboard.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void listPositive(
	final Integer recordIndex,
	final String publicTasks,
	final String privateTasks,
	final String finishedTasks,
	final String nonFinishedTasks,
	final String averageWorkFlow,
	final String deviationWorkFlow,
	final String maxWorkFlow,
	final String minWorkFlow,
	final String averageExecutionPeriod,
	final String deviationExecutionPeriod,
	final String maxExecutionPeriod,
	final String minExecutionPeriod) {
		
		
		
		if(recordIndex == 1) {
			super.signIn("manager3","manager3");
			
			super.clickOnMenu("Manager", "Create task");
			
			super.fillInputBoxIn("title", "Aprobar DP2");
			super.fillInputBoxIn("startDate", "2021/09/29 23:59");
			super.fillInputBoxIn("endDate", "2021/09/30 23:59");
			super.fillInputBoxIn("description", "Hacer un pedazo de examen");
			super.clickOnSubmitButton("Create task!");
			
			this.signOut();
		}
		
		if(recordIndex == 2) {
			super.signIn("manager3","manager3");
			
			super.clickOnMenu("Manager", "Own tasks");
			super.clickOnListingRecord(9);
			
			super.fillInputBoxIn("workFlow.entera", "24");
			
			super.clickOnSubmitButton("Update task!");
			
			this.signOut();
		}
		
		this.signIn("administrator", "administrator");
		super.clickOnMenu("Administrator", "Dashboard");
		
		super.checkDashBoard(publicTasks, privateTasks, finishedTasks, nonFinishedTasks, averageWorkFlow, deviationWorkFlow, maxWorkFlow, minWorkFlow, averageExecutionPeriod, deviationExecutionPeriod, maxExecutionPeriod, minExecutionPeriod);
		
		this.signOut();
	}
	
	/*En el siguiente test se provara la no posibilidad de acceder al dashboard por parte de un usuario no autorizado*/
	@ParameterizedTest
	@CsvFileSource(resources="/administrator/task/users.csv", encoding="utf-8", numLinesToSkip=1)
	@Order(20)
	public void listNegative(final String username, final String password) {
		if(username!=null) this.signIn(username, password);
			super.driver.get("http://localhost:8080/Acme-Work-Plans/administrator/dashboard/list");
			super.checkErrorsExist();
			if(username!=null) super.signOut();
	}


}
