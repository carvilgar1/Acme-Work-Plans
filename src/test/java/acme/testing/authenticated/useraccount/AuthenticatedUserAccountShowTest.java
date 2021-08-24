package acme.testing.authenticated.useraccount;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AuthenticatedUserAccountShowTest extends AcmePlannerTest{
	
	/*En este test verificaremos que al iniciar sesion 
	  la informacion mostrada sea la correcta*/
	@ParameterizedTest
    @CsvFileSource(resources = "/authenticated/useraccount/show.csv", encoding = "utf-8", numLinesToSkip = 1)    
	@Order(10)
    public void showPositive(
    		final String username, 
    		final String password,
    		final String confirmation, 
    		final String name,
    		final String surname,
    		final String email) {
        super.signIn("manager2", "manager2");
        super.clickOnMenu("Account", "General data");
        
        super.checkInputBoxHasValue("username", username);
        super.checkInputBoxHasValue("identity.name", name);
        super.checkInputBoxHasValue("identity.surname", surname);
        super.checkInputBoxHasValue("identity.email", email);

        super.signOut();
    }
	
	/*En este test verificaremos que no es posible acceder a la informacion de un 
	  user account sin haber iniciado sesion*/    
	@Test
	@Order(20)
    public void showNegative() {
        super.driver.get("http://localhost:8080/Acme-Planner/authenticated/user-account/update");
        super.checkErrorsExist();
        
    }
}
