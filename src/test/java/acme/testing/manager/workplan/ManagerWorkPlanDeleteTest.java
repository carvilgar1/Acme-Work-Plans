package acme.testing.manager.workplan;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class ManagerWorkPlanDeleteTest extends AcmePlannerTest{
	
	
	//En este test comprobaremos que se pueden eliminar correctamente workplans,
	//para ello iniciacremos sesion, verificaremos los datos del workplan, lo borraremos
	//y verificaremos los datos del workplan ahora son del siguiente en el listado
	@ParameterizedTest
	@CsvFileSource(resources="/manager/workplan/delete-positive.csv", encoding="utf-8", numLinesToSkip=1)
	@Order(10)
	public void deletePositive(final int recordIndex, final String startDate, final String nextStartDate) {
		//Iniciamos sesion
		super.signIn("manager2", "manager2");
		//Accedemos al listado
		super.clickOnMenu("Manager", "Works Plans");
		//Verificamos valor del workplan que vamos a borrar
		super.checkColumnHasValue(recordIndex,0, startDate);
		//Accedemos al workplan y lo borramos
		super.clickOnListingRecord(recordIndex);
		super.clickOnSubmitButton("Delete");
		//Verificamos que ahora tenemos el dato del sigueinte workplan
		super.checkColumnHasValue(recordIndex,0, nextStartDate);
		
		super.signOut();
		
	}
	
	//En este test comprobaremos que no podemos eliminar un workplan estando autenticados pero sin ser su manager
	//Para ello intentaremos acceder a la url del formulario y comprobaremos que no podemos acceder a el
	@ParameterizedTest
	@CsvFileSource(resources="/manager/workplan/delete-negative.csv", encoding="utf-8", numLinesToSkip=1)
	@Order(20)
	public void deleteNegative(final String username, final String password) {
		//Iniciamos sesion
		if(username!=null) this.signIn(username, password);
		//Intentamos acceder al fomulario
		super.driver.get("http://localhost:8080/Acme-Planner/manageracc/workplan/delete?id=41");
		//COmprobamos que ha saltado un error
		super.checkErrorsExist();
		if(username!=null) super.signOut();
	}
	

	
	//En este test verificaremos que podemos borrar un workplan accediendo por la url al formulario siendo sus managers,
	//para ello iniciaremos sesion, accederemos al formulario, lo borraremos y comprobaremos que ahora no esta en la lista
	@ParameterizedTest
	@CsvFileSource(resources="/manager/workplan/delete-positive1.csv", encoding="utf-8", numLinesToSkip=1)
	@Order(40)
	public void deletePositive(final int id, final String nextStartDate, final String nextEndDate, final String nextWorkLoad) {
		//Iniciamos sesion
		super.signIn("manager3", "manager3");
		//Accedemos a la url
		final String url="http://localhost:8080/Acme-Planner/manageracc/workplan/delete?id=" + id;
		super.driver.get(url);
		//Borramos el workplan
		super.clickOnSubmitButton("Delete");
		//Accedemos al listado y comprobamos que ahora el sigueinte workplan ha bajado un recordIndex
		super.clickOnMenu("Manager", "Works Plans");
		super.checkColumnHasValue(0, 0, nextStartDate);
		super.checkColumnHasValue(0, 1, nextEndDate);
		super.checkColumnHasValue(0, 2, nextWorkLoad);
		super.signOut();
		
	}
	
	
	
	
}
