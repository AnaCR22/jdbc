package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;

public class FindByNif implements Command<Optional<MechanicDto>> {

    private String nif;
    private MechanicGateway mg = Factories.persistence.forMechanic();

    public FindByNif(String nif) {
	ArgumentChecks.isNotBlank(nif);
	this.nif = nif;
    }

    public Optional<MechanicDto> execute() {
	Optional<MechanicRecord> op = mg.findByNif(nif);

	if (op.isEmpty()) {
	    return Optional.empty();
	}

	MechanicDto dto = MechanicDtoAssembler.toDto(op.get());
	return Optional.of(dto);

    }
}
