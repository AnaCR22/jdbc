package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateMechanic implements Command<Void> {

    private MechanicDto dto;
    private MechanicGateway mg = Factories.persistence.forMechanic();

    public UpdateMechanic(MechanicDto dto) {
	ArgumentChecks.isNotNull(dto, "Mechanic dto must not be null");
	ArgumentChecks.isNotBlank(dto.id,
	    "Mechanic id must not be empty or null");
	ArgumentChecks.isNotBlank(dto.nif,
	    "Mechanic nif must not be empty or null");
	ArgumentChecks.isNotBlank(dto.name,
	    "Mechanic name must not be empty or null");
	ArgumentChecks.isNotBlank(dto.surname,
	    "Mechanic surname must not be empty or null");
	this.dto = dto;
    }

    public Void execute() throws BusinessException {
	Optional<MechanicRecord> op = mg.findById(dto.id);
	BusinessChecks.exists(op, "Mechanic does not exist");
	
	BusinessChecks.hasVersion(dto.version, op.get().version,
	    "The mechanic has been modified concurrently");

	mg.update(MechanicDtoAssembler.toRecord(dto));
	return null;

    }

}
