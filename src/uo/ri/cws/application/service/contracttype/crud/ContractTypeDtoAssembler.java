package uo.ri.cws.application.service.contracttype.crud;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;

public class ContractTypeDtoAssembler {

    public static ContractTypeDto toDto(ContractTypeRecord r) {
	ContractTypeDto dto = new ContractTypeDto();
	dto.id = r.id;
        dto.name = r.name;
	dto.compensationDays = r.compensationDaysPerYear;
	dto.version = r.version;
	return dto;
    }

    public static ContractTypeRecord toRecord(ContractTypeDto dto) {
	ContractTypeRecord r = new ContractTypeRecord();
	r.id = dto.id;
	r.name = dto.name;
	r.compensationDaysPerYear = dto.compensationDays;
	r.version = dto.version;
	return r;
    }

    public static List<ContractTypeDto> toDtoList(List<ContractTypeRecord> records) {
	List<ContractTypeDto> dtos = new ArrayList<>();
	for (ContractTypeRecord r : records) {
	    dtos.add(toDto(r));
	}
	return dtos;
    }
}
