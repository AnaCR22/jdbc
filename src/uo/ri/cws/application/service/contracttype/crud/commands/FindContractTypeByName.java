package uo.ri.cws.application.service.contracttype.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.service.contracttype.crud.ContractTypeDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindContractTypeByName implements Command<Optional<ContractTypeDto>> {

    private String name;
    private ContractTypeGateway ctg = Factories.persistence.forContractType();
    
    public FindContractTypeByName(String name) {
        ArgumentChecks.isNotBlank(name, "Contract type name must not be null or empty");
        this.name = name;
    }

    @Override
    public Optional<ContractTypeDto> execute() throws BusinessException {
        Optional<ContractTypeRecord> rec = ctg.findByName(name);

        if (rec.isEmpty())
            return Optional.empty();

        return Optional.of(ContractTypeDtoAssembler.toDto(rec.get()));
    }

}
