package uo.ri.cws.application.service.contract.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateContract implements Command<Void> {

    private ContractGateway cg = Factories.persistence.forContract();
    private ContractDto dto;

    public UpdateContract(ContractDto dto) {
	ArgumentChecks.isNotNull(dto, "Contract dto cannot be null");
	ArgumentChecks.isNotBlank(dto.id,
	    "Contract id cannot be null or empty");
	ArgumentChecks.isTrue(dto.annualBaseSalary > 0,
	    "Annual base salary must be non-negative");
	this.dto = dto;
    }

    @Override
    public Void execute() throws BusinessException {
	// Check contract exist
	Optional<ContractRecord> oc = cg.findById(dto.id);
	BusinessChecks.exists(oc, "The contract does not exist");
	ContractRecord record = oc.get();

	// Check in force contract
	BusinessChecks.isTrue("IN_FORCE".equals(record.state),
	    "The contract is no longer in force");

	BusinessChecks.hasVersion(dto.version, record.version,
	        "The contract has been modified concurrently");
	
	//if endDate is given
	if (dto.endDate != null) {
	    BusinessChecks.isTrue(dto.endDate.isAfter(record.startDate),
                "End date must be after start date");

	    record.endDate = dto.endDate;
	} else {
	    record.endDate = null;
	}

        record.annualBaseSalary = dto.annualBaseSalary;
        record.taxRate = calculateTaxRate(record.annualBaseSalary);
        record.version++;

        cg.update(record);

	return null;
    }

    
    private double calculateTaxRate(double annualBaseSalary) {
        if (annualBaseSalary < 12450) return 0.19;
        if (annualBaseSalary < 20200) return 0.24;
        if (annualBaseSalary < 35200) return 0.30;
        if (annualBaseSalary < 60000) return 0.37;
        if (annualBaseSalary < 300000) return 0.45;
        return 0.47;
    }
}
