package acme.testing.manager.workplan;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class ManagerWorkPlanAddTaskTest extends AcmePlannerTest{
	
	
	//En este test se va a probar el caso positivo de añadir una tarea a un work plan, 
	//para ello se verificara la carga de trabajo,
	//se añadira la nueva tarea y verificaremos la nueva carga de trabajo ya que se actualiza automaticamente
	@ParameterizedTest
	@CsvFileSource(resources="/manager/workplan/addTask-positive.csv", encoding="utf-8", numLinesToSkip=1)
	@Order(10)
	public void updatePositive(final int recordIndex, final String taskId, final String workLoad, final String newWorkLoad) {
		//Iniciamos sesion como manager y accedemos al formulario del plan de trabajo dado en los parametros
		super.signIn("manager2", "manager2");
		super.clickOnMenu("Manager", "Works Plans");
		super.clickOnListingRecord(recordIndex);
		//Comprobamos la carga de trabajo
		super.checkInputBoxHasValue("workLoad", workLoad);
		//Añadimos una nueva tarea
		super.fillInputBoxIn("addTask", taskId);
		super.clickOnSubmitButton("Add");
		
		//Verificamos que la carga de trabajo se ha actualizado
		super.clickOnListingRecord(recordIndex);
		super.checkInputBoxHasValue("workLoad", newWorkLoad);
	}
	
		//En este test se va a probar el caso negativo de añadir una tarea a un work plan, 
		//para ello se añadiran tareas que no se pueden añadir por validaciones
		@ParameterizedTest
		@CsvFileSource(resources="/manager/workplan/addTask-negative.csv", encoding="utf-8", numLinesToSkip=1)
		@Order(20)
		public void updateNegative(final int recordIndex, final String taskId) {
			//Iniciamos sesion como manager y accedemos al formulario del plan de trabajo dado en los parametros
			super.signIn("manager2", "manager2");
			super.clickOnMenu("Manager", "Works Plans");
			super.clickOnListingRecord(recordIndex);
			//Añadimos una nueva tarea que da error por validacion
			super.fillInputBoxIn("addTask", taskId);
			super.clickOnSubmitButton("Add");
			//Comprobamos que saltan errores 
			super.checkErrorsExist();
		}
}
