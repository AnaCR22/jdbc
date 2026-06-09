package uo.ri.cws.application.ui.manager.contracts.contract.action;

import java.time.LocalDate;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class UpdateContractAction implements Action {

    @Override
    public void execute() throws BusinessException {

        String id = Console.readString("Contract id");

        // Find contract by id
	ContractCrudService service = Factories.service.forContractCrudService();
	Optional<ContractDto> opt = service.findById(id);
        if (opt.isEmpty()) {
            Console.println("No contract found with id: " + id);
            return;
        }
        
        ContractDto dto = opt.get();

        Console.println("Current end date: " + dto.endDate);
        Console.println("Current annual base salary: " + dto.annualBaseSalary);
        
        LocalDate newEndDate = askOptionalForDate("New end date (empty to keep current)");
        if (newEndDate != null) {
            dto.endDate = newEndDate;
        }

        double newSalary = Console.readDouble("New annual base salary (current: " + dto.annualBaseSalary + ")");
        dto.annualBaseSalary = newSalary;
        
        service.update(dto);

	Console.println("Contract updated");
    }

    private LocalDate askOptionalForDate(String msg) {
        while (true) {
            try {
                Console.print(msg + " [optional]: ");
                String asString = Console.readString();
                return ("".equals(asString)) ? null : LocalDate.parse(asString);
            } catch (Exception e) {
                Console.println("--> Invalid date");
            }
        }
    }
}