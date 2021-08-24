/*
 * SignUpTest.java
 *
 * Copyright (C) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.testing;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class SignUpTest extends AcmePlannerTest {

	//En este test verificaremos que se puede registrar un usuario, 
	//para ello accederemos al formulario, lo rellenaremos y lo enviaremos
	@ParameterizedTest
	@CsvFileSource(resources="/sign-up/positive.csv", encoding="utf-8", numLinesToSkip=1)
	@Order(10)
	public void createPositive(final String username, final String password, final String name, 
		final String surname, final String email) {
		
		//Registramos al usuario
		super.signUp(username, password, password, name, surname, email);
		//Iniciamos sesion
		super.signIn(username, password);
		//Accedemos los datos del usuario
		super.clickOnMenu("Account", "General data");
		//Comprobamos que los datos son los mismos con los que nos hemos registrado
		super.checkInputBoxHasValue("username", username);
		super.checkInputBoxHasValue("identity.name", name);
		super.checkInputBoxHasValue("identity.surname", surname);
		super.checkInputBoxHasValue("identity.email", email);
		
		super.signOut();
	}
	
	//En este test se comprobaran las validaciones,
	//para ello se accedera al formulario y se rellenaran los campos de forma incorrecta  para verficar que saltan errores

	@ParameterizedTest
	@CsvFileSource(resources="/sign-up/negative.csv", encoding="utf-8", numLinesToSkip=1)
	@Order(10)
	public void createNegative(final String username, final String password, final String name, 
		final String surname, final String email, final String license) {
		//Accedemos al formulario de registro
		super.clickOnLink("Sign up");
		//Rellenamos los datos del formulario
		super.fillInputBoxIn("username", username);
		super.fillInputBoxIn("password", password);
		super.fillInputBoxIn("confirmation", password);
		super.fillInputBoxIn("identity.name", name);
		super.fillInputBoxIn("identity.surname", surname);
		super.fillInputBoxIn("identity.email", email);
		super.fillInputBoxIn("accept", "true");
		//Intentamos registranos
		super.clickOnSubmitButton("Sign up");
		//Verificamos que han saltado errores
		super.checkErrorsExist();
	
	}
	
	
}
