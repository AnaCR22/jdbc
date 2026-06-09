package uo.ri.cws.application.service.contracttype.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteContractType implements Command<Void> {

    private String name;
    private ContractTypeGateway ctg = Factories.persistence.forContractType();
    private ContractGateway cg = Factories.persistence.forContract();

    public DeleteContractType(String name) {
	ArgumentChecks.isNotBlank(name,
	    "Contract Type name must not be empty or null");
	this.name = name;
    }

    @Override
    public Void execute() throws BusinessException {
	Optional<ContractTypeRecord> oct = ctg.findByName(name);
        BusinessChecks.exists(oct, "The contract type does not exist");

        ContractTypeRecord record = oct.get();

        // Check that no contract uses this type
        boolean used = cg.existsByContractTypeId(record.id);
        BusinessChecks.isFalse(used, "There are contracts registered with this contract type");

        // Delete
        ctg.remove(record.id);
        
	return null;
    }

}
