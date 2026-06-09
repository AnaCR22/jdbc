package uo.ri.cws.application.service.professionalgroup.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteProfessionalGroup implements Command<Void> {

    private String name;
    private ProfessionalGroupGateway pgg = Factories.persistence.forProfessionalGroup();
    private ContractGateway cg = Factories.persistence.forContract();

    public DeleteProfessionalGroup(String name) {
	ArgumentChecks.isNotBlank(name,
	    "Professional group name must not be empty or null");
	this.name = name;
    }

    @Override
    public Void execute() throws BusinessException {
	Optional<ProfessionalGroupRecord> oct = pgg.findByName(name);
        BusinessChecks.exists(oct, "The professional group does not exist");

        ProfessionalGroupRecord record = oct.get();

        // Check that no contract uses this type
        boolean used = cg.existsByProfessionalGroupId(record.id);
        BusinessChecks.isFalse(used, "There are contracts registered with this professional group");

        // Delete
        pgg.remove(record.id);
        
	return null;
    }

}
