package uo.ri.cws.application.service.professionalgroup.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.cws.application.service.professionalgroup.crud.ProfessionalGroupAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindProfessionalGroupByName implements Command<Optional<ProfessionalGroupDto>> {

    private String name;
    private ProfessionalGroupGateway pgg = Factories.persistence.forProfessionalGroup();
    
    public FindProfessionalGroupByName(String name) {
        ArgumentChecks.isNotBlank(name, "Professional group name must not be null or empty");
        this.name = name;
    }

    @Override
    public Optional<ProfessionalGroupDto> execute() throws BusinessException {
        Optional<ProfessionalGroupRecord> rec = pgg.findByName(name);

        if (rec.isEmpty())
            return Optional.empty();

        return Optional.of(ProfessionalGroupAssembler.toDto(rec.get()));
    }

}
