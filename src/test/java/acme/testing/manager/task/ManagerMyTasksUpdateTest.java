package acme.testing.manager.task;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class ManagerMyTasksUpdateTest extends AcmePlannerTest {
	
	//En este test se actualiza una task del manager2. Además, se lista las tasks que hay creadas y se
	//muestran sus detalles. Por lo tanto, se prueba las funcionalidades list, show y update de tasks.
	//Al clicar en una task, se muestran los detalles, pudiendo modificar algunos campos. Se espera que se actualice
	//la task correctamente.
	//Hay que haber iniciado sesión con un rol de manager pero no se infringe ninguna restricción.
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/updatePositive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void updatePositive(final int recordIndex, final String title, final String startDate, 
		final String endDate, final String workFlow, final String description, final String publicTask, final String url) {
		super.signIn("manager2", "manager2");
		
		super.clickOnMenu("Manager", "Own tasks");
		super.clickOnListingRecord(recordIndex);
		
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("workFlow", workFlow);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("url", url);
		super.clickOnSubmitButton("Update task!");
		
		super.clickOnListingRecord(recordIndex);
		
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("workFlow", workFlow);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("publicTask", publicTask);
		super.checkInputBoxHasValue("url", url);
		
		super.signOut();
	}
	
	//Este test prueba la funcionalidad de publicar una tarea. Se accede a una task privada y se hace click en pulicar.
	//Se espera que se publique correctamente.
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/publishTask.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void updatePositive(final int recordIndex, final String title, final String startDate, 
		final String endDate, final String workFlow, final String publicTask, final String url) {
		super.signIn("manager2", "manager2");
		
		super.clickOnMenu("Manager", "Own tasks");
		super.clickOnListingRecord(recordIndex);
		
		super.fillInputBoxIn("publicTask", "true");
		super.fillInputBoxIn("workFlow", workFlow);
		super.fillInputBoxIn("url", url);
		super.clickOnSubmitButton("Update task!");
		
		super.clickOnListingRecord(recordIndex);
		
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("workFlow", workFlow);
		super.checkInputBoxHasValue("publicTask", publicTask);
		super.checkInputBoxHasValue("url", url);
		
		super.signOut();
	}
	
	//En este caso, se intenta actualizar una task como manager3 pero haciendo que salten validaciones del formulario.
	//Se infringe las restricciones correspondiente a dejar el formulario completamente vacío, las restricciones de las fechas
	//(fecha final no puede ser puede ser antes que la fecha inicial y la fecha final debe ser más tarde a la fecha de hoy),
	//la restricción del filtro anti palabras spam y las restricciones del workload (debe ser máximo el intervalo entre las dos fechas y debe ser mínimo 0)
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/updateNegative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(30)
	public void updateNegative(final int recordIndex, final String title, final String startDate, 
		final String endDate, final String workFlow, final String description, final String publicTask, final String url) {
		super.signIn("manager2", "manager2");
		
		super.clickOnMenu("Manager", "Own tasks");
		super.clickOnListingRecord(recordIndex);
		
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("workFlow", workFlow);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("url", url);
		super.clickOnSubmitButton("Update task!");
		
		super.checkErrorsExist();
		
		super.signOut();
	}

}
