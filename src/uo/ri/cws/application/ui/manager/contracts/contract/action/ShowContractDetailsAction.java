package uo.ri.cws.application.ui.manager.contracts.contract.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class ShowContractDetailsAction implements Action {

    @Override
    public void execute() throws BusinessException {
        String id = Console.readString("Contract id");

	ContractCrudService service = Factories.service.forContractCrudService();
        Optional<ContractDto> c = service.findById(id);

        
        if (c.isEmpty()) {
            Console.println("No contract found with id: " + id);
        } else {
            Printer.printContractDetails(c.get());
        }
    }
}