package acme.testing.manager.workplan;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class ManagerWorkPlanUpdateTest  extends AcmePlannerTest {

	
	//En este test verificamos que se puede modificar un workplan,
	//para ello accederemos a un workplan y modificaremos sus datos,
	//para verficarlos comprobaremos que los datos han cambiado
	@ParameterizedTest
	@CsvFileSource(resources="/manager/workplan/update-positive.csv", encoding="utf-8", numLinesToSkip=1)
	@Order(10)
	public void updatePositive(final int recordIndex, final String startDate, final String endDate) {
		//Iniciamos sesion
		super.signIn("manager2", "manager2");
		//Accedemos a un workplan
		super.clickOnMenu("Manager", "Works Plans");
		super.clickOnListingRecord(recordIndex);
		//Modificamos los datos
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		//Actualizamos
		super.clickOnSubmitButton("Update");
		//Verificamos se han cambiado los datos
		super.checkColumnHasValue(recordIndex, 0, startDate);
		super.checkColumnHasValue(recordIndex, 1, endDate);
		
		super.signOut();
	}
	
	//En este test verificaremos que saltan las validaciones al modificar un workplan
	//para ello introduciremos fechas no validas
	@ParameterizedTest
	@CsvFileSource(resources="/manager/workplan/update-negative.csv", encoding="utf-8", numLinesToSkip=1)
	@Order(20)
	public void updateNegative(final int recordIndex, final String startDate, final String endDate) {
		//Iniciamos sesion
		super.signIn("manager2", "manager2");
		//Accedemos a un workplan
		super.clickOnMenu("Manager", "Works Plans");
		super.clickOnListingRecord(recordIndex);
		//Cambiamos los datos
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		//Actualizamos
		super.clickOnSubmitButton("Update");
		//Verificamos que han saltado errores
		super.checkErrorsExist();
	}
}

