package uo.ri.cws.application.ui.manager.contracts.contract.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractTypeOfContractDto;
import uo.ri.cws.application.service.contract.ContractCrudService.MechanicOfContractDto;
import uo.ri.cws.application.service.contract.ContractCrudService.ProfessionalGroupOfContractDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class AddContractAction implements Action {

    /**
     * Creates a contract. The mechanic's NIF, contract type name, type name,
     * month and base salary are provided via console.
     */
    @Override
    public void execute() throws BusinessException {
	ContractDto dto = new ContractDto();
	
        dto.mechanic = new MechanicOfContractDto();
        dto.mechanic.nif = Console.readString("Mechanic NIF");
        
        dto.contractType = new ContractTypeOfContractDto();
        dto.contractType.name = askForType();
        
        dto.professionalGroup = new ProfessionalGroupOfContractDto();
        dto.professionalGroup.name = askForPorfessionalGroup();
        
        
        dto.annualBaseSalary = Console.readDouble("Annual base salary");
        if ("FIXED_TERM".equals(dto.contractType.name)) {
            dto.endDate = Console.readDate("Type end date");
        }

        /*
         * Add the contract
         */
        ContractCrudService service = Factories.service.forContractCrudService();
	dto = service.create(dto);
	
	Console.println("Contract registered");
    }

    private String askForPorfessionalGroup() {
        Console.println("Professional group");
        Console.println("I \t II \t III \t IV \t V \t VI \t VII");
        return Console.readString("Professional group name");
    }

    private String askForType() {
        Console.println("Contract type");
        Console.println("PERMANENT \t SEASONAL \t FIXED_TERM");
        return Console.readString("Contract type name");
    }

}