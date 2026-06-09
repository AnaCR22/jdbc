package uo.ri.cws.application.ui.manager.contracts.contracttype.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class FindContractTypeByNameAction implements Action {

    @Override
    public void execute() throws BusinessException {
        String name = Console.readString("Contract type name");

        Console.println("\nContract Type information \n");

        ContractTypeCrudService service = Factories.service.forContractTypeCrudService();
        Optional<ContractTypeDto> dto = service.findByName(name);

        if (dto.isEmpty()) {
            Console.println("No contract type found with name: " + name);
        } else {
            Printer.printContractType(dto.get());
        }
    }

}