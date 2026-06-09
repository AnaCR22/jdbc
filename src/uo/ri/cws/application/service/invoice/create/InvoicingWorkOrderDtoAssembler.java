package uo.ri.cws.application.service.invoice.create;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;

public class InvoicingWorkOrderDtoAssembler {

    public static InvoicingWorkOrderDto toDto(WorkOrderRecord r) {
        InvoicingWorkOrderDto dto = new InvoicingWorkOrderDto();
        dto.id = r.id;
        dto.description = r.description;
        dto.date = r.date;
        dto.state = r.state;
        dto.amount = r.amount;
        return dto;
    }
    
    public static WorkOrderRecord toRecord(InvoicingWorkOrderDto dto) {
	WorkOrderRecord  r = new WorkOrderRecord ();
	r.id = dto.id;
	r.description  = dto.description;
	r.date = dto.date;
	dto.state = r.state;
        dto.amount = r.amount;
	return r;
    }

    public static List<InvoicingWorkOrderDto> toDtoList(List<WorkOrderRecord> records) {
	List<InvoicingWorkOrderDto> dtos = new ArrayList<>();
	for (WorkOrderRecord  r : records) {
	    dtos.add(toDto(r));
	}
	return dtos;
    }
}
