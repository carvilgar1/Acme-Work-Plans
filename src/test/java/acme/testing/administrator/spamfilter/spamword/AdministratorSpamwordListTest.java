package acme.testing.administrator.spamfilter.spamword;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AdministratorSpamwordListTest extends AcmePlannerTest{

	
	/*
		En este test se comprueba que un administrador sea capaz de listar las spamwords
		Para ello accedemos al listado de spamword comprobando que el valor de los campos del
		listado son correctos, así como, que el valor del formulario (show) es correcto tambien.
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/spamfilter/listSpamword.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void listPositive(final int recordIndex, final String spamword) {
		this.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator", "Spam filter");
		
		super.checkColumnHasValue(recordIndex, 0, spamword);
		
		super.clickOnListingRecord(recordIndex);
		
		super.checkInputBoxHasValue("spamword", spamword);
		
		this.signOut();
	}
	
	
	/*
		En este test se comprueba que un usuario no autorizado no sea capaz de listar las spamwords
		Para ello accedemos al listado de spamword comprobando que nos devuelve un error de autorizacion
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/administrator/spamfilter/users.csv", encoding="utf-8", numLinesToSkip=1)
	@Order(30)
	public void listNegative(final String username, final String password) {
		if(username!=null) this.signIn(username, password);
		super.driver.get("http://localhost:8080/Acme-Planner/administrator/spamword/list");
		super.checkErrorsExist();
		if(username!=null) super.signOut();
	}
	
	
	/*
		Este test se realiza debido a que el servicio de list de spamword contiene los metodo de validacion de filtro de spam
		Por lo tanto para poder cumplir el porcentaje exigido tenemos que crear un shout para poder acceder a estos metodos.
		Simplemente como anonimo se crea un shout valido y se comprueba que no salta el filtro spam, publicandose el shout.
	 */
	@Test
	@Order(35)
	public void filtroShoutPositive() {
		
		super.clickOnMenu("Anonymous", "Shout!");
		
		super.checkInputBoxHasValue("author", "John Doe");
		super.checkInputBoxHasValue("text", "Lorem ipsum!");
		super.checkInputBoxHasValue("info", "http://example.org");
		
		super.fillInputBoxIn("author", "Pablo");
		super.fillInputBoxIn("text", "Hola como estais?");
		super.fillInputBoxIn("info", "");
		
		super.clickOnSubmitButton("Shout!");
		
		super.clickOnMenu("Anonymous", "List shouts");
		
		super.checkColumnHasValue(0, 1, "Pablo");
		super.checkColumnHasValue(0, 2, "Hola como estais?");
		super.checkColumnHasValue(0, 3, "");
		
	}
	
	/*
		Este caso es igual que el anterior pero esperando que la restriccion de Spam se cumple, provocando un error al intentar publicar el shout.
	 */
	@Test
	@Order(40)
	public void filtroShoutNegative() {
		
		super.clickOnMenu("Anonymous", "Shout!");
		
		super.checkInputBoxHasValue("author", "John Doe");
		super.checkInputBoxHasValue("text", "Lorem ipsum!");
		super.checkInputBoxHasValue("info", "http://example.org");
		
		super.fillInputBoxIn("author", "Sex");
		super.fillInputBoxIn("text", "Hola como estais?");
		super.fillInputBoxIn("info", "");
		
		super.clickOnSubmitButton("Shout!");
		
		super.checkErrorsExist();
		
	}
	
	/*
		Este test se realiza debido a que el servicio de list de spamword contiene los metodo de validacion de filtro de spam
		Por lo tanto para poder cumplir el porcentaje exigido tenemos que crear un task para poder acceder a estos metodos.
		Simplemente como anonimo se crea un task valido y se comprueba que no salta el filtro spam, creandose la task.
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/createPositive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(50)
	public void filtroTaskPositive(final int recordIndex, final String title, final String startDate, 
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
}
