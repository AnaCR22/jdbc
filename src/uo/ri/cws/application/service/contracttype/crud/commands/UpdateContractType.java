
package uo.ri.cws.application.service.contracttype.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateContractType implements Command<Void> {
    private ContractTypeDto dto;
    private ContractTypeGateway ctg = Factories.persistence.forContractType();
    
    public UpdateContractType(ContractTypeDto dto) {
	ArgumentChecks.isNotNull(dto, "Contract type dto must not be null");
	ArgumentChecks.isNotBlank(dto.name,
	    "Contract type name must not be null or empty");
	ArgumentChecks.isTrue(dto.compensationDays >= 0,
	    "Compensation days must be non-negative");
	
        this.dto = dto;
    }

    @Override
    public Void execute() throws BusinessException {
	Optional<ContractTypeRecord> oct = ctg.findByName(dto.name);
        BusinessChecks.exists(oct, "The contract type does not exist");
        
        ContractTypeRecord record = oct.get();
        record.compensationDaysPerYear = dto.compensationDays;
        
        ctg.update(record);
        
        return null;
    }

}
