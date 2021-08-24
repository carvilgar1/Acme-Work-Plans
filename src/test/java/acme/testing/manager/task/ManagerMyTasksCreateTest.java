package acme.testing.manager.task;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class ManagerMyTasksCreateTest extends AcmePlannerTest {
	
	//En este test se crea una task como manager3. Además, se lista la task recientemente creada y se
	//muestran sus detalles. Por lo tanto, se prueba las funcionalidades list, show y create de task.
	//Se espera que se cree, se liste y se muestren los detalles de la task correctamente.
	//Hay que haber iniciado sesión con un rol de manager pero no se infringe ninguna restricción.
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/createPositive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void createPositive(final int recordIndex, final String title, final String startDate, 
		final String endDate, final String workFlow, final String description, final String publicTask, final String url) {
		super.signIn("manager3", "manager3");
		
		super.clickOnMenu("Manager", "Create task");
		
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("url", url);
		super.clickOnSubmitButton("Create task!");
		
		super.clickOnMenu("Manager", "Own tasks");
		
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, startDate);
		super.checkColumnHasValue(recordIndex, 2, endDate);
		super.checkColumnHasValue(recordIndex, 3, description);
		
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
	
	//En este caso, se intenta crear una task como manager3 pero haciendo que salten validaciones del formulario.
	//En la primera línea del csv se infringen todas las restricciones correspondientes a dejar el formulario vacío.
	//En esta, también se infringe la restricción del formato de la url.
	//En la segunda línea del csv se infringe el formato de las fechas.
	//En la tercera línea del csv se infringe la restricción de las spamwords
	//En la cuarta línea, las restricciones de las fechas (fecha final no puede ser puede ser antes que la fecha inicial
	//y la fecha inicial y final deben ser más tarde a la fecha de hoy)
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/createNegative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void createNegative(final int recordIndex, final String title, final String startDate, 
		final String endDate, final String description, final String url) {
		super.signIn("manager3", "manager3");
		
		super.clickOnMenu("Manager", "Create task");
		
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("url", url);
		super.clickOnSubmitButton("Create task!");
		
		super.checkErrorsExist();
		
		super.signOut();
	}

}
