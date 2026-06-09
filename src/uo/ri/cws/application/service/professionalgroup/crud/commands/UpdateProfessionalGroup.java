
package uo.ri.cws.application.service.professionalgroup.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateProfessionalGroup implements Command<Void> {
    private ProfessionalGroupDto dto;
    private ProfessionalGroupGateway pgg = Factories.persistence.forProfessionalGroup();
    
    public UpdateProfessionalGroup(ProfessionalGroupDto dto) {
	ArgumentChecks.isNotNull(dto, "Professional group dto must not be null");
	ArgumentChecks.isNotBlank(dto.name,
	    "Professional groupname must not be null or empty");
	ArgumentChecks.isTrue(dto.trienniumPayment >= 0, 
            "Triennium Payment must be non-negative");
	ArgumentChecks.isTrue(dto.productivityRate >= 0, 
            "Productivity Rate must be non-negative");
	
        this.dto = dto;
    }

    @Override
    public Void execute() throws BusinessException {
	Optional<ProfessionalGroupRecord> oct = pgg.findByName(dto.name);
        BusinessChecks.exists(oct, "The professional group does not exist");
        
        ProfessionalGroupRecord record = oct.get();
        BusinessChecks.isTrue(record.version == dto.version,
            "The professional group has been updated by another user");
        
        record.trienniumPayment = dto.trienniumPayment;
        record.productivityRate = dto.productivityRate;
        
        pgg.update(record);
        
        return null;
    }

}
