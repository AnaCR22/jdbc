package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddMechanic implements Command<MechanicDto> {

    private MechanicDto dto;
    private MechanicGateway mg = Factories.persistence.forMechanic();

    public AddMechanic(MechanicDto dto) {
	ArgumentChecks.isNotNull(dto);
	ArgumentChecks.isNotBlank(dto.nif,
	    "Mechanic nif must not be empty or null");
	ArgumentChecks.isNotBlank(dto.name,
	    "Mechanic name must not be empty or null");
	ArgumentChecks.isNotBlank(dto.surname,
	    "Mechanic surname must not be empty or null");

	dto.id = UUID.randomUUID()
		     .toString();
	dto.version = 1;
	this.dto = dto;
    }

    public MechanicDto execute() throws BusinessException {
	Optional<MechanicRecord> op = mg.findByNif(dto.nif);
	BusinessChecks.doesNotExist(op,
	    "A mechanic with nif " + dto.nif + " already exists");

	mg.add(MechanicDtoAssembler.toRecord(dto));

	return dto;
    }
}
