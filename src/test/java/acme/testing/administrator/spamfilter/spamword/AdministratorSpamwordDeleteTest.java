package acme.testing.administrator.spamfilter.spamword;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AdministratorSpamwordDeleteTest extends AcmePlannerTest{

	
	/*
		En este test comprobamos que un administrador puede eliminar una palabra spam.
		Para ello accedemos al formulario de delete de la spamword y pulsamos el boton 
		de Delete, a continuacion comprobamos que la palabra no existe.
	
	 */
	@Test
	@Order(10)
	public void deletePositive() {
		this.signIn("administrator", "administrator");
		
		super.driver.get("http://localhost:8080/Acme-Planner/administrator/spamword/delete?id=66");
		
		super.checkInputBoxHasValue("spamword", "duro");
		super.clickOnSubmitButton("Delete");
		
		super.checkSimplePath("/master/welcome");
		super.driver.get("http://localhost:8080/Acme-Planner/administrator/spamword/show?id=66");
		super.checkErrorsExist();
		
		this.signOut();
	}
	
	
	/*
		En este test se comprueba que un usuario no autorizado no sea capaz de acceder al formulario de delete de una spamword
		Para ello accedemos a la url del formulario de la spamword comprobando que nos devuelve un error de autorizacion
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/administrator/spamfilter/users.csv", encoding="utf-8", numLinesToSkip=1)
	@Order(20)
	public void deleteNegative(final String username, final String password) {	
		if(username!=null) this.signIn(username, password);
		super.driver.get("http://localhost:8080/Acme-Planner/administrator/spamword/delete?id=66");
		super.checkErrorsExist();
		if(username!=null) super.signOut();
	}
	
	
}
