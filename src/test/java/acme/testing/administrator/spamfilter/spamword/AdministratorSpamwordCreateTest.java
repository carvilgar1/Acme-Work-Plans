package acme.testing.administrator.spamfilter.spamword;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AdministratorSpamwordCreateTest extends AcmePlannerTest{

	/*
		En este test comprobamos que un administrador puede crear una palabra spam.
		Para ello accedemos al formulario de create a través del menú y rellenamos el campo necesario 
		con un valor válido (NotNull&NotSpam) y pulsando el boton Create.
		A continuación se comprueba si la palabra se ha creado.
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/spamfilter/createSpamword.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void createPositive(final int recordIndex, final String spamword) {
		this.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator", "Add spam word");

		super.checkInputBoxHasValue("spamword", "");
		
		super.fillInputBoxIn("spamword", spamword);
		super.clickOnSubmitButton("Create");
		
		super.clickOnMenu("Administrator", "Spam filter");
		super.checkColumnHasValue(recordIndex, 0, spamword);
		
		this.signOut();
	}
	
	/*
		En este test comprobamos que un administrador no puede crear una palabra spam vacía.
		Para ello accedemos al formulario de create a través del menú y rellenamos el campo necesario 
		con un valor no válido (Null) y pulsando el boton Create.
		A continuación se comprueba que han surgido errores.
	 */
	@Test
	@Order(15)
	public void createNegative() {
		this.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator", "Add spam word");
		
		super.checkInputBoxHasValue("spamword", "");
		
		super.clickOnSubmitButton("Create");
		super.checkErrorsExist();
		
		this.signOut();
	}
	
	/*
		En este test se comprueba que un usuario no autorizado no sea capaz de crear una spamword
		Para ello accedemos a la url del formulario de create de spamword comprobando que nos devuelve un error de autorizacion
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/administrator/spamfilter/users.csv", encoding="utf-8", numLinesToSkip=1)
	@Order(20)
	public void listNegative(final String username, final String password) {
		if(username!=null) this.signIn(username, password);
		super.driver.get("http://localhost:8080/Acme-Planner/administrator/spamword/create");
		super.checkErrorsExist();
		if(username!=null) super.signOut();
	}
	

}
