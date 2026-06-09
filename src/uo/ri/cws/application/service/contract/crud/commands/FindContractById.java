package uo.ri.cws.application.service.contract.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.contract.crud.ContractDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindContractById implements Command<Optional<ContractDto>> {

    private String id;
    private ContractGateway cg = Factories.persistence.forContract();
   
    public FindContractById(String id) {
        ArgumentChecks.isNotBlank(id);
        this.id = id;
    }
    
    @Override
    public Optional<ContractDto> execute() throws BusinessException {
	Optional<ContractRecord> oc = cg.findById(id);
	
	if (oc.isEmpty()) {
	    return Optional.empty();
	}      
	
        ContractDto dto = ContractDtoAssembler.toDto(oc.get());
        return Optional.of(dto);
    }

}
