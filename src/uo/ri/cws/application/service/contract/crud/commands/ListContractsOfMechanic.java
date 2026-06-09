package uo.ri.cws.application.service.contract.crud.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractSummaryDto;
import uo.ri.cws.application.service.contract.crud.ContractDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class ListContractsOfMechanic
    implements Command<List<ContractSummaryDto>> {

    private String nif;
    private ContractGateway cg = Factories.persistence.forContract();
    private PayrollGateway pg = Factories.persistence.forPayroll();
    private MechanicGateway mg = Factories.persistence.forMechanic();

    public ListContractsOfMechanic(String nif) {
	ArgumentChecks.isNotBlank(nif,
	    "Mechanic nif must not be empty or null");
	this.nif = nif;
    }

    @Override
    public List<ContractSummaryDto> execute() throws BusinessException { 
	Optional<MechanicRecord> om = mg.findByNif(nif);
	if (om.isEmpty()) {
	    return new ArrayList<>();
	}
	MechanicRecord mechanic = om.get();

	List<ContractRecord> contracts = cg.findByMechanicId(mechanic.id);
	List<ContractSummaryDto> dtos = new ArrayList<>();

	for (ContractRecord c : contracts) {
	    int numPayrolls = pg.countByContractId(c.id);

	    ContractSummaryDto dto = ContractDtoAssembler.toSummaryDto(c, nif,
		numPayrolls);
	    dtos.add(dto);
	}

	return dtos;
    }

}
