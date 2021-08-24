package acme.testing.authenticated.manager;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import acme.testing.AcmePlannerTest;

public class AuthenticatedManagerCreateTest extends AcmePlannerTest{


	
	//En este test verificaremos que un authenticated puede convertirse en manager,
	//para ello accederemos al formulario y lo enviaremos
	@Test
	@Order(10)
	public void createPositive() {
		//Iniciamos sesison como usuario que no es manager
		super.signIn("fervalnav", "Qwerty123");
		//Accedemos al formulario para convertirnos en manager
		super.clickOnMenu("Account", "Become a manager");
		//Nos convertimos en manager
		super.clickOnSubmitButton("Register");
		//Comprobamos que tenemos los links para acceder que debe tener un manager
		super.checkLinkExists("Own tasks");
		super.signOut();
	}
	
	//EN este test verificaremos que un manager no puede volver a convertirse en manager,
	//esto lo verficaremos viendo que saltan erroes al intentar acceder

	@Test
	@Order(20)
	public void createNegative() {
    //Iniciamos sesion
		super.signIn("manager2", "manager2");
    //Accedemos al formulario
		super.driver.get("http://localhost:8080/Acme-Planner/authenticated/manager/create");
    //Verificamos que hay erroes
		super.checkErrorsExist();
	}
}
