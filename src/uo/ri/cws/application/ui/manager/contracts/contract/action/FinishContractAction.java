package uo.ri.cws.application.ui.manager.contracts.contract.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class FinishContractAction implements Action {

    @Override
    public void execute() throws BusinessException {
        String id = Console.readString("Contract Id");

        ContractCrudService service = Factories.service.forContractCrudService();
        service.terminate(id);
        
        Console.println("The contract has been terminated");
    }
}
