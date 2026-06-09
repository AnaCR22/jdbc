package uo.ri.cws.application.service.professionalgroup.crud;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;

public class ProfessionalGroupAssembler {

    public static ProfessionalGroupDto toDto(ProfessionalGroupRecord r) {
	ProfessionalGroupDto dto = new ProfessionalGroupDto();
	dto.id = r.id;
        dto.name = r.name;
	dto.trienniumPayment = r.trienniumPayment;
	dto.productivityRate = r.productivityRate;
	dto.version = r.version;
	return dto;
    }

    public static ProfessionalGroupRecord toRecord(ProfessionalGroupDto dto) {
	ProfessionalGroupRecord r = new ProfessionalGroupRecord();
	r.id = dto.id;
	r.name = dto.name;
	r.trienniumPayment = dto.trienniumPayment;
	r.productivityRate = dto.productivityRate;
	r.version = dto.version;
	return r;
    }

    public static List<ProfessionalGroupDto> toDtoList(List<ProfessionalGroupRecord> records) {
	List<ProfessionalGroupDto> dtos = new ArrayList<>();
	for (ProfessionalGroupRecord r : records) {
	    dtos.add(toDto(r));
	}
	return dtos;
    }
}
