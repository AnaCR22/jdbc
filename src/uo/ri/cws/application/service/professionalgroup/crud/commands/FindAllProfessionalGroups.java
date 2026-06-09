package uo.ri.cws.application.service.professionalgroup.crud.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.cws.application.service.professionalgroup.crud.ProfessionalGroupAssembler;
import uo.ri.util.exception.BusinessException;

public class FindAllProfessionalGroups implements Command<List<ProfessionalGroupDto>> {

    private ProfessionalGroupGateway pgg = Factories.persistence.forProfessionalGroup();
    
    @Override
    public List<ProfessionalGroupDto> execute() throws BusinessException {
	List<ProfessionalGroupRecord> records = pgg.findAll();
	return ProfessionalGroupAssembler.toDtoList(records);
    }

}
