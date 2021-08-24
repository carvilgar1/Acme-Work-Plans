package acme.testing.administrator.spamfilter.threshold;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AdministratorThresholdListTest extends AcmePlannerTest {

	
	/*
		En este test se comprueba que un administrador sea capaz de listar el threshold
		Para ello accedemos al listado de threshold comprobando que el valor de los campos del
		listado son correctos, así como, que el valor del formulario (show) es correcto tambien.
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/spamfilter/listThreshold.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void listPositive(final int recordIndex, final String value) {
		this.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator", "Spam threshold");
		
		super.checkColumnHasValue(recordIndex, 0, value);
		
		super.clickOnListingRecord(recordIndex);
		
		super.checkInputBoxHasValue("value", value);
		
		this.signOut();
	}
	
	

	
	/*
		En este test se comprueba que un usuario no autorizado no sea capaz de listar el threshold
		Para ello accedemos al listado de threshold comprobando que nos devuelve un error de autorizacion
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/administrator/spamfilter/users.csv", encoding="utf-8", numLinesToSkip=1)
	@Order(30)
	public void listNegative(final String username, final String password) {
		if(username!=null) this.signIn(username, password);
		super.driver.get("http://localhost:8080/Acme-Planner/administrator/threshold/list");
		super.checkErrorsExist();
		if(username!=null) super.signOut();
	}
}
