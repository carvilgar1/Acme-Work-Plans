package acme.testing.administrator.userAccount;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AdministratorUserAccountListTest extends AcmePlannerTest{
	

	//En este test se prueba la funcionalidad list y show de los perfiles de usuario que no se les puede modificar su "status". El resultado esperado es que se listen
	//los perfiles y se muestren sus detalles.
	//No se infringe ninguna restricción.
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/userAccount/list-all.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void listPositive(final int recordIndex, final String username, final String name, final String surname, final String email, final String roles, final String status) {
		super.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator", "User accounts");
		
		super.checkColumnHasValue(recordIndex, 0, username);
		super.checkColumnHasValue(recordIndex, 1, name);
		super.checkColumnHasValue(recordIndex, 2, surname);
		
		super.clickOnListingRecord(recordIndex);
		
		super.checkInputBoxHasValue("username", username);
		super.checkInputBoxHasValue("identity.name", name);
		super.checkInputBoxHasValue("identity.surname", surname);
		super.checkInputBoxHasValue("identity.email",email);
		super.checkInputBoxHasValue("roleList",roles);
		super.checkInputBoxHasValue("status",status);
		
		super.signOut();
	}
	
	//En este test se prueba la funcionalidad list y show de los perfiles de usuario que se les puede modificar su "status". El resultado esperado es que se listen
	//los perfiles y se muestren sus detalles.
	//No se infringe ninguna restricción.
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/userAccount/list-all-status.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void listPositive(final int recordIndex, final String username, final String name, final String surname, final String email, final String roles, final String status, final String newStatus) {
		super.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator", "User accounts");
		
		super.checkColumnHasValue(recordIndex, 0, username);
		super.checkColumnHasValue(recordIndex, 1, name);
		super.checkColumnHasValue(recordIndex, 2, surname);
		
		super.clickOnListingRecord(recordIndex);
		
		super.checkInputBoxHasValue("username", username);
		super.checkInputBoxHasValue("identity.name", name);
		super.checkInputBoxHasValue("identity.surname", surname);
		super.checkInputBoxHasValue("identity.email",email);
		super.checkInputBoxHasValue("roleList",roles);
		super.checkInputBoxHasValue("status",status);
		super.checkInputBoxHasValue("newStatus",newStatus);
		
		super.signOut();
	}
	
	//Aquí se prueba la funcionalidad list de los perfiles de los usuarios pero de forma negativa. El resultado esperado es
	//un error de acceso denegado ya que se está accediendo con un rol usuario no autorizado que no tiene acceso
	//a la funcionalidad.
	@ParameterizedTest
	@CsvFileSource(resources="/administrator/userAccount/users.csv", encoding="utf-8", numLinesToSkip=1)
	@Order(30)
	public void listNegative(final String username, final String password) {
		if(username!=null) this.signIn(username, password);
		super.driver.get("http://localhost:8080/Acme-Planner/administrator/user-account/list");
		super.checkErrorsExist();
		if(username!=null) super.signOut();
	}
	


}
