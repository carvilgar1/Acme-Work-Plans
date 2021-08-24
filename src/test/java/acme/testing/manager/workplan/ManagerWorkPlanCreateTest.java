package acme.testing.manager.workplan;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class ManagerWorkPlanCreateTest extends AcmePlannerTest {
	
	//En este test comprobara que se crean los workplans correctamente, 
	//para ello accederemos al formulario, rellenaremos los datos
	//y para verificarlo comprobaremos todos los datos tanto en la list como en el show
	@ParameterizedTest
	@CsvFileSource(resources="/manager/workplan/create-positive.csv", encoding="utf-8", numLinesToSkip=1)
	@Order(10)
	public void createPositive(final int recordIndex, final String startDate, final String endDate) {
		//Iniciamos sesion
		super.signIn("manager2", "manager2");
		//Accedemos al formulario
		super.clickOnMenu("Manager", "Create Work Plan");
		//Rellenamos los campos
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		//Enviamos el formulario
		super.clickOnSubmitButton("Create");
		//Accedemos al listado
		super.clickOnMenu("Manager", "Works Plans");
		//Comprobamos los datos del nuevo workplan
		super.checkColumnHasValue(recordIndex, 0, startDate);
		super.checkColumnHasValue(recordIndex, 1, endDate);
		super.checkColumnHasValue(recordIndex, 2, "0.00");
		//Accedemos al show del nuevo workplan
		super.clickOnListingRecord(recordIndex);
		//Volvemos a comprobar los datos
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("workLoad", "0.00");
		
		super.signOut();
	}
	
	
	//En este test verificaremos que saltan las validaciones al crear un workplan
	//Para ello iniciamos sesion como manager, accedemos al formulario para crearlo
	// y rellenamos los campos con fechas que incumplen las restricciones
	//Para verificarlo comprobamos que han saltado errores
	@ParameterizedTest
	@CsvFileSource(resources="/manager/workplan/create-negative.csv", encoding="utf-8", numLinesToSkip=1)
	@Order(20)
	public void createNegative(final String startDate, final String endDate) {
		//Iniciamos sesion
		super.signIn("manager2", "manager2");
		//Accedemos al formulario
		super.clickOnMenu("Manager", "Create Work Plan");
		//Rellenamos los campos
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		//Enviamos el formulario
		super.clickOnSubmitButton("Create");
		//Verificamos que hay errores
		super.checkErrorsExist();
		
		super.signOut();
	}
	
}

