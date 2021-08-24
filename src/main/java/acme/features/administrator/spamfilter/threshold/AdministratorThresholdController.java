package acme.features.administrator.spamfilter.threshold;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.spamfilter.Threshold;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Administrator;

@Controller
@RequestMapping("/administrator/threshold/")
public class AdministratorThresholdController extends AbstractController<Administrator, Threshold>{
	
	@Autowired
	private AdministratorThresholdListService thresholdListService;
	
	@Autowired
	private AdministratorThresholdUpdateService thresholdUpdateService;
	
	@Autowired
	private AdministratorThresholdShowService thresholdShowService;

	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.LIST, this.thresholdListService);
		super.addBasicCommand(BasicCommand.UPDATE, this.thresholdUpdateService);
		super.addBasicCommand(BasicCommand.SHOW, this.thresholdShowService);
	}
}
