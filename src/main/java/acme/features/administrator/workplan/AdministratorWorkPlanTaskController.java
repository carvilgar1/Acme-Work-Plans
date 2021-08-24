package acme.features.administrator.workplan;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.dashboardwp.Dashboardwp;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Administrator;

@Controller
@RequestMapping("/administrator/dashboardwp/")
public class AdministratorWorkPlanTaskController extends AbstractController<Administrator, Dashboardwp>{
	
	@Autowired 
	private AdministratorWorkPlanDashBoardService dashboardListService;
	
	
	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.LIST, this.dashboardListService);
	}
	
}
