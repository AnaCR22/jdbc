package uo.ri.cws.application.ui.manager.contracts.contracttype.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class UpdateContractTypeAction implements Action {

    @Override
    public void execute() throws BusinessException {
	String name = Console.readString("Contract type name");

	// Check contract type exists
	ContractTypeCrudService service = Factories.service.forContractTypeCrudService();
	Optional<ContractTypeDto> op = service.findByName(name);
	if (op.isEmpty()) {
            Console.println("No contract type found with name: " + name);
            return;
        }
	
	ContractTypeDto dto = op.get();

	dto.compensationDays = Console.readDouble("Compensation days");

	// update
	service.update(dto);
	
	Console.println("Contract type updated");
    }

}