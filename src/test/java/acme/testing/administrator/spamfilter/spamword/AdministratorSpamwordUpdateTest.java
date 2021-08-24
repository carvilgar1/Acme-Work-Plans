package acme.testing.administrator.spamfilter.spamword;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.core.annotation.Order;

import acme.testing.AcmePlannerTest;

public class AdministratorSpamwordUpdateTest extends AcmePlannerTest {

	
	/*
		En este test se comprueba que un administrador sea capaz de actualizar una spamword
		Para ello accedemos al formulario de la spamword y actualizamos con un valor valido (NotNull)
		comprobando que se ha actualizado posteriormente.
		Finalmente volvemos a establecer el valor original de la spamword para no provocar errores a posteriori.
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/spamfilter/updateSpamwordPositive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void updatePositive(final int recordIndex, final String spamword) {
		this.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator", "Spam filter");
		
		super.checkColumnHasValue(recordIndex, 0, "duro");
		
		super.clickOnListingRecord(recordIndex);		
		
		super.fillInputBoxIn("spamword", spamword);
		super.clickOnSubmitButton("Update");
		
		super.checkSimplePath("/administrator/spamword/list");
		super.driver.get("http://localhost:8080/Acme-Planner/administrator/spamword/update?id=66");
		super.checkInputBoxHasValue("spamword", spamword);

		super.fillInputBoxIn("spamword", "duro");
		super.clickOnSubmitButton("Update");
		
		this.signOut();
	}
	
	/*
		En este test se comprueba que un administrador no sea capaz de actualizar la spamword debido a restricciones.
		Para ello accedemos al formulario de la spamword y lo actualizamos con un valor no valido (Null)
		comprobando que se han producido errores y que no el valor sigue siendo el mismo.
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/spamfilter/updateSpamwordNegative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(15)
	public void updateNegative(final int recordIndex, final String spamword) {
		super.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator", "Spam filter");
		
		super.checkColumnHasValue(recordIndex, 0, "duro");
		
		super.clickOnListingRecord(recordIndex);		
		
		super.fillInputBoxIn("spamword", spamword);
		super.clickOnSubmitButton("Update");
		
		super.checkErrorsExist();
		
		super.driver.get("http://localhost:8080/Acme-Planner/administrator/spamword/update?id=66");
		super.checkInputBoxHasValue("spamword", "duro");
		
		super.signOut();
	}
	
	/*
		En este test se comprueba que un usuario no autorizado no sea capaz de acceder al formulario de la spamword
		Para ello accedemos a la url del formulario de la spamword comprobando que nos devuelve un error de autorizacion
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/administrator/spamfilter/users.csv", encoding="utf-8", numLinesToSkip=1)
	@Order(20)
	public void updateNegative(final String username, final String password) {
		if(username!=null) this.signIn(username, password);
		super.driver.get("http://localhost:8080/Acme-Planner/administrator/spamword/update?id=66");
		super.checkErrorsExist();
		if(username!=null) super.signOut();
	}
	



}
