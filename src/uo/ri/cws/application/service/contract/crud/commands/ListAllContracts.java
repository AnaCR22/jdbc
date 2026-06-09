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
import uo.ri.util.exception.BusinessException;

public class ListAllContracts implements Command<List<ContractSummaryDto>>{

    private ContractGateway cg = Factories.persistence.forContract();
    private MechanicGateway mg = Factories.persistence.forMechanic();
    private PayrollGateway pg = Factories.persistence.forPayroll();

    @Override
    public List<ContractSummaryDto> execute() throws BusinessException {
	List<ContractRecord> contracts = cg.findAll();
        List<ContractSummaryDto> dtos = new ArrayList<>();
        
        for (ContractRecord c : contracts) {
            Optional<MechanicRecord> om = mg.findById(c.mechanicId);
            String nif = om.map(m -> m.nif).orElse("UNKNOWN");

            int numPayrolls = pg.countByContractId(c.id);

            dtos.add(ContractDtoAssembler.toSummaryDto(c, nif, numPayrolls));
        }
        
    	return dtos;

    }

}
