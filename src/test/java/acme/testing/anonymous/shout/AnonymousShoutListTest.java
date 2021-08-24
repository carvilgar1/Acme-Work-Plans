package acme.testing.anonymous.shout;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AnonymousShoutListTest extends AcmePlannerTest{
	/*
		En este test se comprueba que un anonimo sea capaz de listar los shouts
		Para ello accedemos al listado de shouts a través del menu comprobando que el valor de los campos del
		listado son correctos.
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/shout/list.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void listPositive(final int recordIndex, final String author, final String text, final String info, final String moment) {
		
		super.clickOnMenu("Anonymous", "List shouts");
		
		super.checkColumnHasValue(recordIndex, 0, moment);
		super.checkColumnHasValue(recordIndex, 1, author);
		super.checkColumnHasValue(recordIndex, 2, text);
		super.checkColumnHasValue(recordIndex, 3, info);
		
		
	}


	/*
		En este test se comprueba que un usuario no autorizado no sea capaz de listar los shouts
		Para ello accedemos a la url del listado de shouts comprobando que nos devuelve 
		un error de autorizacion
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/anonymous/shout/users.csv", encoding="utf-8", numLinesToSkip=1)
	@Order(30)
	public void listNegative(final String username, final String password) {
		
		if(username!=null) this.signIn(username, password);
		super.driver.get("http://localhost:8080/Acme-Planner/anonymous/shout/list");
		super.checkPanicExists();
		if(username!=null) super.signOut();
		
	}
}
