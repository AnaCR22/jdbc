package uo.ri.cws.application.service.professionalgroup.crud.commands;

import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.cws.application.service.professionalgroup.crud.ProfessionalGroupAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddProfessionalGroup implements Command<ProfessionalGroupDto>{

    private ProfessionalGroupDto dto;
    private ProfessionalGroupGateway pgg = Factories.persistence.forProfessionalGroup();
    
    public AddProfessionalGroup(ProfessionalGroupDto dto) {
	ArgumentChecks.isNotNull(dto);
	ArgumentChecks.isNotBlank(dto.name,
	    "Professional group name must not be empty or null");
	ArgumentChecks.isTrue(dto.trienniumPayment >= 0, 
            "Triennium Payment must be non-negative");
	ArgumentChecks.isTrue(dto.productivityRate >= 0, 
            "Productivity Rate must be non-negative");
	
	dto.id = UUID.randomUUID()
	     .toString();
        dto.version = 1;
        this.dto = dto;
   }

    @Override
    public ProfessionalGroupDto execute() throws BusinessException {
        Optional<ProfessionalGroupRecord> existing = pgg.findByName(dto.name);
        BusinessChecks.isTrue(existing.isEmpty(), 
            "A professional group with that name already exists");

        pgg.add(ProfessionalGroupAssembler.toRecord(dto));

        return dto;
    }

}
