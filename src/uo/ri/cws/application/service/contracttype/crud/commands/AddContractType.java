package uo.ri.cws.application.service.contracttype.crud.commands;

import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.service.contracttype.crud.ContractTypeDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddContractType implements Command<ContractTypeDto>{

    private ContractTypeDto dto;
    private ContractTypeGateway ctg = Factories.persistence.forContractType();
    
    public AddContractType(ContractTypeDto dto) {
	ArgumentChecks.isNotNull(dto);
	ArgumentChecks.isNotBlank(dto.name,
	    "Contract Type name must not be empty or null");
	ArgumentChecks.isTrue(dto.compensationDays >= 0, 
            "Compensation days must be non-negative");

	dto.id = UUID.randomUUID()
	     .toString();
        dto.version = 1;
        this.dto = dto;
   }

    @Override
    public ContractTypeDto execute() throws BusinessException {
        Optional<ContractTypeRecord> existing = ctg.findByName(dto.name);
        BusinessChecks.isTrue(existing.isEmpty(), 
            "A contract type with that name already exists");

	ctg.add(ContractTypeDtoAssembler.toRecord(dto));

        return dto;
    }

}
