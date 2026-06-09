package uo.ri.cws.application.service.invoice.create;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;

public class InvoiceDtoAssembler {

    public static InvoiceDto toDto(InvoiceRecord r) {
	InvoiceDto dto = new InvoiceDto();
	dto.id = r.id;
	dto.number = r.number;
	dto.date = r.date;
	dto.vat = r.vat;
	dto.amount = r.amount;
	dto.state = r.state;
	dto.version = r.version;
	return dto;
    }

    public static InvoiceRecord toRecord(InvoiceDto dto) {
	InvoiceRecord r = new InvoiceRecord();
	r.id = dto.id;
	r.number = dto.number;
	r.date = dto.date;
	r.vat = dto.vat;
	r.amount = dto.amount;
	r.state = dto.state;
	r.version = dto.version;
	return r;
    }

    public static List<InvoiceDto> toDtoList(List<InvoiceRecord> records) {
	List<InvoiceDto> dtos = new ArrayList<>();
	for (InvoiceRecord r : records) {
	    dtos.add(toDto(r));
	}
	return dtos;
    }
}
