package uo.ri.cws.application.service.invoice.create.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.service.invoice.create.InvoicingWorkOrderDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;

public class FindNotInvoicedWorkOrdersByClientNif implements Command<List<InvoicingWorkOrderDto>> {
    
    private String nif;
    private WorkOrderGateway wg = Factories.persistence.forWorkOrder();

    public FindNotInvoicedWorkOrdersByClientNif(String nif) {
	ArgumentChecks.isNotNull(nif);
        this.nif = nif;
    }

    public List<InvoicingWorkOrderDto> execute() {
	List<WorkOrderRecord> records = wg.findNotInvoicedByClientNif(nif);
	return InvoicingWorkOrderDtoAssembler.toDtoList(records);	
    }

}
