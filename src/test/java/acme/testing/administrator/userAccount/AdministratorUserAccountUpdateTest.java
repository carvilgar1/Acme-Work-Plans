package acme.testing.administrator.userAccount;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AdministratorUserAccountUpdateTest extends AcmePlannerTest{
	
	
	
	/*
	En este test se comprueba que un usuario no autorizado no sea capaz de acceder al formulario de los perfiles de usuario
	Para ello accedemos a la url del formulario del perfil que queremos actualizar comprobando que nos 
	devuelve un error de autorizacion
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/administrator/userAccount/users.csv", encoding="utf-8", numLinesToSkip=1)
	@Order(20)
	public void updateNegative(final String username, final String password) {
		if(username!=null) this.signIn(username, password);
		super.driver.get("http://localhost:8080/Acme-Planner/administrator/user-account/update?id=6");
		super.checkErrorsExist();
		if(username!=null) super.signOut();
	}
	

	
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/userAccount/updatePositive2.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(40)
	public void updatePositive(final int recordIndex, final String username, final String name, final String surname, final String email, final String roles, final String status,final String newStatus) {
		super.signIn("administrator", "administrator");
		
		super.driver.get("http://localhost:8080/Acme-Planner/administrator/user-account/update?id=9");
		
		super.fillInputBoxIn("newStatus", newStatus);
		super.clickOnSubmitButton("Update");
		
		super.clickOnMenu("Administrator", "User accounts");
		super.clickOnListingRecord(recordIndex);
		
		super.checkInputBoxHasValue("newStatus", newStatus);
		
		super.signOut();	
	}


}


