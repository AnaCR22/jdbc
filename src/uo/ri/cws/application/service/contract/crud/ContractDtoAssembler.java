package uo.ri.cws.application.service.contract.crud;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractSummaryDto;

public class ContractDtoAssembler {

    public static ContractDto toDto(ContractRecord r) {
	ContractDto dto = new ContractDto();
	dto.id = r.id;
	dto.version = r.version;
        dto.state = r.state;
        dto.startDate = r.startDate;
        dto.endDate = r.endDate;
        dto.annualBaseSalary = r.annualBaseSalary;
        dto.taxRate = r.taxRate;
        dto.settlement = r.settlement;

        dto.mechanic.id = r.mechanicId;
        dto.contractType.id = r.contractTypeId;
        dto.professionalGroup.id = r.professionalGroupId;

	return dto;
    }

    public static ContractRecord toRecord(ContractDto dto) {
	ContractRecord r = new ContractRecord();
	r.id = dto.id;
        r.version = dto.version;
        r.state = dto.state;
        r.startDate = dto.startDate;
        r.endDate = dto.endDate;
        r.annualBaseSalary = dto.annualBaseSalary;
        r.taxRate = dto.taxRate;
        r.settlement = dto.settlement;

        r.mechanicId = dto.mechanic.id;
        r.contractTypeId = dto.contractType.id;
        r.professionalGroupId = dto.professionalGroup.id;
	return r;
    }

    public static List<ContractDto> toDtoList(List<ContractRecord> records) {
	List<ContractDto> dtos = new ArrayList<>();
	for (ContractRecord r : records) {
	    dtos.add(toDto(r));
	}
	return dtos;
    }
    
   
    public static ContractSummaryDto toSummaryDto(ContractRecord r, String nif, int numPayrolls) {
	ContractSummaryDto dto = new ContractSummaryDto();
   	dto.id = r.id;
   	dto.nif = nif;
        dto.state = r.state;
        dto.settlement = r.settlement;
        dto.numPayrolls = numPayrolls;
   	return dto;
       }
    
}
