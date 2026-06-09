package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;

public class FindById implements Command<Optional<MechanicDto>> {

    private String id;
    private MechanicGateway mg = Factories.persistence.forMechanic();

    public FindById(String id) {
	ArgumentChecks.isNotBlank(id);
	this.id = id;
    }

    public Optional<MechanicDto> execute() {

	Optional<MechanicRecord> op = mg.findById(id);

	if (op.isEmpty()) {
	    return Optional.empty();
	}

	MechanicDto dto = MechanicDtoAssembler.toDto(op.get());
	return Optional.of(dto);
    }
}