package uo.ri.cws.application.service.mechanic.crud;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;

public class MechanicDtoAssembler {

    public static MechanicDto toDto(MechanicRecord r) {
	MechanicDto dto = new MechanicDto();
	dto.id = r.id;
	dto.nif = r.nif;
	dto.name = r.name;
	dto.surname = r.surname;
	dto.version = r.version;
	return dto;
    }

    public static MechanicRecord toRecord(MechanicDto dto) {
	MechanicRecord mr = new MechanicRecord();
	mr.id = dto.id;
	mr.nif = dto.nif;
	mr.name = dto.name;
	mr.surname = dto.surname;
	mr.version = dto.version;
	return mr;
    }

    public static List<MechanicDto> toDtoList(List<MechanicRecord> records) {
	List<MechanicDto> dtos = new ArrayList<>();
	for (MechanicRecord r : records) {
	    dtos.add(toDto(r));
	}
	return dtos;
    }
}
