package acme.testing.manager.workplan;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class ManagerWorkPlanPublishTest extends AcmePlannerTest {
	
	
	//En este test verificaremos que podemos publicar un workplan,
	//para ellos accederemos al formulario de uno de ellos y lo publicaremos,
	//posteriormente verficaremos que esta publicado
	@ParameterizedTest
	@CsvFileSource(resources="/manager/workplan/publish-positive.csv", encoding="utf-8", numLinesToSkip=1)
	@Order(10)
	public void updatePositive(final int recordIndex) {
		//Iniciamos sesion
		super.signIn("manager3", "manager3");
		//Accedemos a un workplan
		super.clickOnMenu("Manager", "Works Plans");
		super.clickOnListingRecord(recordIndex);
		//Publicamos el workplan
		super.clickOnSubmitButton("Publish");
		//Verificamos que esta publicado
		super.clickOnListingRecord(recordIndex);
		super.checkInputBoxHasValue("publicPlan", "true");
		
		super.signOut();
	}
	
	
	//En este test verificamos que saltan validacione sal publicar un workplan,
	//para ello intentaremos publicar workplan con tareas en privado y debera saltar un error
	@ParameterizedTest
	@CsvFileSource(resources="/manager/workplan/publish-negative.csv", encoding="utf-8", numLinesToSkip=1)
	@Order(20)
	public void updateNegative(final int recordIndex) {
		//Iniciamos sesion
		super.signIn("manager3", "manager3");
		//Accedemos a un workplan
		super.clickOnMenu("Manager", "Works Plans");
		super.clickOnListingRecord(recordIndex);
		//Lo publicamos
		super.clickOnSubmitButton("Publish");
		//Verificamos que han salado erroes
		super.checkErrorsExist();
	}
	
	

}
