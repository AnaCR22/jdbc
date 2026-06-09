package uo.ri.cws.application.service.contract.crud.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.contract.crud.ContractDtoAssembler;
import uo.ri.util.exception.BusinessException;

public class FindInforceContracts implements Command<List<ContractDto>> {

    private ContractGateway cg = Factories.persistence.forContract();
    
    @Override
    public List<ContractDto> execute() throws BusinessException {
	List<ContractRecord> records = cg.findInForce();
	
	return ContractDtoAssembler.toDtoList(records);
    }

}
