package uo.ri.cws.application.service.invoice.create.commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.create.InvoiceDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.math.Rounds;

public class CreateInvoice implements Command<InvoiceDto>{

    private final List<String> workOrderIds;

    private InvoiceGateway ig = Factories.persistence.forInvoice();
    private WorkOrderGateway wg = Factories.persistence.forWorkOrder();

    public CreateInvoice(List<String> workOrderIds) {
	ArgumentChecks.isNotNull(workOrderIds);
	ArgumentChecks.isFalse(workOrderIds.isEmpty());

	for (String id : workOrderIds) {
	    ArgumentChecks.isNotBlank(id);
	}

	this.workOrderIds = workOrderIds;
    }

    public InvoiceDto execute() throws BusinessException {
	try (Connection ignored = Jdbc.createThreadConnection()) {

	    BusinessChecks.isTrue(wg.existAll(workOrderIds),
		"Some workorders do not exist");
	    BusinessChecks.isTrue(wg.areAllFinished(workOrderIds),
		"Some workorders are not finished yet");

	    long numberInvoice = generateInvoiceNumber();
	    LocalDate dateInvoice = LocalDate.now();
	    double amount = calculateTotalInvoice(workOrderIds); // vat not
							     // included
	    double vat = vatPercentage(dateInvoice);
	    double vatAmount = amount * (vat / 100); // vat amount
	    double total = amount * vatAmount; // vat included
	    total = Rounds.toCents(total);

	    InvoiceDto dto = new InvoiceDto();
	    dto.id = UUID.randomUUID().toString();
	    dto.number = numberInvoice;
	    dto.date = dateInvoice;
	    dto.vat = vatAmount;
	    dto.amount = total;
	    dto.state = "NOT_YET_PAID";
	    dto.version = 1L;

	    ig.add(InvoiceDtoAssembler.toRecord(dto));
	    
	    for (String id : workOrderIds) {
                wg.linkToInvoice(dto.id, id);
                wg.markAsInvoiced(id);
                wg.updateVersionAndTime(id);
            }

	    return dto;
	} catch (SQLException e) {
	    throw new RuntimeException("Error creating invoice", e);
	}

    }  
   
    /*
     * Generates next invoice number (not to be confused with the inner id)
     */
    private long generateInvoiceNumber() throws SQLException {
	Optional<Long> last = ig.findLastInvoiceNumber();
	return last.map(n -> n + 1).orElse(1L);

    }

    /*
     * Compute total amount of the invoice (as the total of individual work
     * orders' amount
     */
    private double calculateTotalInvoice(List<String> workOrderIDs)
	throws SQLException {
	double total = 0.0;
        for (String id : workOrderIDs)
            total += wg.findAmountById(id);
        return total;
    }

    /*
     * returns vat percentage
     */
    private double vatPercentage(LocalDate d) {
	return LocalDate.parse("2012-07-01").isBefore(d) ? 21.0 : 18.0;

    }

    

}
