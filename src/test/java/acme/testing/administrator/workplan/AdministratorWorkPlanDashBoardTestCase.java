package acme.testing.administrator.workplan;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AdministratorWorkPlanDashBoardTestCase extends AcmePlannerTest{
	
/*En el siguiente test se provara que los valores numericos devueltos por el servicio sean correctos*/
	
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/workplan/workplan-dashboard.csv", encoding = "utf-8", numLinesToSkip = 1)
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
		
		if(recordIndex!=2) {
			super.signIn("manager3","manager3");
			super.clickOnMenu("Manager", "Works Plans");
			
			super.clickOnListingRecord(recordIndex);
			super.clickOnSubmitButton("Publish");
			super.signOut();
		}
		
		
		this.signIn("administrator", "administrator");
		super.clickOnMenu("Administrator", "Dashboard Work Plans");
		
		super.checkDashBoard(publicTasks, privateTasks, finishedTasks, nonFinishedTasks, averageWorkFlow, deviationWorkFlow, maxWorkFlow, minWorkFlow, averageExecutionPeriod, deviationExecutionPeriod, maxExecutionPeriod, minExecutionPeriod);
		
		this.signOut();
	}
	
	/*En el siguiente test se provara la no posibilidad de acceder al dashboard por parte de un usuario no autorizado*/
	@ParameterizedTest
	@CsvFileSource(resources="/administrator/workplan/users.csv", encoding="utf-8", numLinesToSkip=1)
	@Order(20)
	public void listNegative(final String username, final String password) {
		if(username!=null) this.signIn(username, password);
			super.driver.get("http://localhost:8080/Acme-Planner/administrator/dashboardwp/list");
			super.checkErrorsExist();
			if(username!=null) super.signOut();;
	}


}
