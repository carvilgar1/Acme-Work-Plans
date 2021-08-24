package acme.testing;

import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;

import acme.framework.helpers.StringHelper;

public class AcmePlannerTest extends AcmeTest{
	
	@Override
	@BeforeAll
	public void beforeAll() {
		super.beforeAll();

		super.setBaseCamp("http", "localhost", "8080", "/Acme-Planner", "/master/welcome", "?language=en&debug=true");
		super.setAutoPausing(false);
		
		this.navigateHome();
		this.signIn("administrator", "administrator");
		super.clickOnMenu("Administrator", "Populate DB (samples)");
		super.checkAlertExists(true);
		this.signOut();
	}
	
	protected void signIn(final String username, final String password) {
		assert !StringHelper.isBlank(username);
		assert !StringHelper.isBlank(password);
		
		super.navigateHome();
		super.clickOnMenu("Sign in", null);
		super.fillInputBoxIn("username", username);
		super.fillInputBoxIn("password", password);
		super.fillInputBoxIn("remember", "true");
		super.clickOnSubmitButton("Sign in");
		super.checkSimplePath("/master/welcome");
		super.checkLinkExists("Account");
	}
	
	protected void signOut() {
		super.navigateHome();
		super.clickOnMenu("Sign out", null);
		super.checkSimplePath("/master/welcome");
	}

	protected void signUp(final String username, final String password, final String confirmation, final String name, final String surname, final String email) {
		assert !StringHelper.isBlank(username);
		assert !StringHelper.isBlank(password);
		assert !StringHelper.isBlank(name);
		assert !StringHelper.isBlank(surname);
		assert !StringHelper.isBlank(email);
		
		super.navigateHome();
		super.clickOnMenu("Sign up", null);
		super.fillInputBoxIn("username", username);
		super.fillInputBoxIn("password", password);
		super.fillInputBoxIn("confirmation", confirmation);
		super.fillInputBoxIn("identity.name", name);
		super.fillInputBoxIn("identity.surname", surname);
		super.fillInputBoxIn("identity.email", email);
		super.fillInputBoxIn("accept", "true");
		super.clickOnSubmitButton("Sign up");
		super.checkSimplePath("/master/welcome");
	}
	
	protected void checkDashBoard(
		final String publicTasks,
		final String privateTasks,
		final String finishedTasks,
		final String nonFinishedTasks,
		final String averageWorkFlow,
		final String deviationWorkFlow,
		final String maxWorkFlow,
		final String minWorkFlow,
		final String averageExecutionPeriod,
		final String deviationExecutionPeriod,
		final String maxExecutionPeriod,
		final String minExecutionPeriod) {
		
		assert publicTasks != null;
		assert privateTasks != null;
		assert finishedTasks != null;
		assert nonFinishedTasks != null;
		assert averageWorkFlow != null;
		assert deviationWorkFlow != null;
		assert maxWorkFlow != null;
		assert minWorkFlow != null;
		assert averageExecutionPeriod != null;
		assert deviationExecutionPeriod != null;
		assert maxExecutionPeriod != null;
		assert minExecutionPeriod != null;
		
		By locatorPublicTasks;
		By locatorprivateTasks;
		By locatorfinishedTasks;
		By locatornonFinishedTasks;
		By locatoraverageWorkFlow;
		By locatordeviationWorkFlow;
		By locatormaxWorkFlow;
		By locatorminWorkFlow;
		By locatoraverageExecutionPeriod;
		By locatordeviationExecutionPeriod;
		By locatormaxExecutionPeriod;
		By locatorminExecutionPeriod;
		
		
		locatorPublicTasks = By.xpath("/html/body/div[2]/div/table[1]/tbody/tr/td[2]");
		locatorprivateTasks = By.xpath("/html/body/div[2]/div/table[1]/tbody/tr/td[3]");
		locatorfinishedTasks = By.xpath("/html/body/div[2]/div/table[1]/tbody/tr/td[4]");
		locatornonFinishedTasks = By.xpath("/html/body/div[2]/div/table[1]/tbody/tr/td[5]");
		
		locatoraverageWorkFlow = By.xpath("/html/body/div[2]/div/table[2]/tbody/tr/td[2]");
		locatordeviationWorkFlow = By.xpath("/html/body/div[2]/div/table[2]/tbody/tr/td[3]");
		locatormaxWorkFlow = By.xpath("/html/body/div[2]/div/table[2]/tbody/tr/td[4]");
		locatorminWorkFlow = By.xpath("/html/body/div[2]/div/table[2]/tbody/tr/td[5]");
		
		locatoraverageExecutionPeriod = By.xpath("/html/body/div[2]/div/table[3]/tbody/tr/td[2]");
		locatordeviationExecutionPeriod = By.xpath("/html/body/div[2]/div/table[3]/tbody/tr/td[3]");
		locatormaxExecutionPeriod = By.xpath("/html/body/div[2]/div/table[3]/tbody/tr/td[4]");
		locatorminExecutionPeriod = By.xpath("/html/body/div[2]/div/table[3]/tbody/tr/td[5]");
		
		assert super.driver.findElement(locatorPublicTasks).getText().equals(publicTasks) ;
		assert super.driver.findElement(locatorprivateTasks).getText().equals(privateTasks);
		assert super.driver.findElement(locatorfinishedTasks).getText().equals(finishedTasks);
		assert super.driver.findElement(locatornonFinishedTasks).getText().equals(nonFinishedTasks);
		assert super.driver.findElement(locatoraverageWorkFlow).getText().equals(averageWorkFlow);
		assert super.driver.findElement(locatordeviationWorkFlow).getText().equals(deviationWorkFlow);
		assert super.driver.findElement(locatormaxWorkFlow).getText().equals(maxWorkFlow);
		assert super.driver.findElement(locatorminWorkFlow).getText().equals(minWorkFlow);
		assert super.driver.findElement(locatoraverageExecutionPeriod).getText().equals(averageExecutionPeriod);
		assert super.driver.findElement(locatordeviationExecutionPeriod).getText().equals(deviationExecutionPeriod);
		assert super.driver.findElement(locatormaxExecutionPeriod).getText().equals(maxExecutionPeriod);
		assert super.driver.findElement(locatorminExecutionPeriod).getText().equals(minExecutionPeriod);

		
	}
}



