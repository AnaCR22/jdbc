package uo.ri.cws.application.persistence.invoice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;

public interface InvoiceGateway extends Gateway<InvoiceRecord> {

    /**
    * Finds last invoice number.
    * 
    * @return a list a long value or null value
    * @throws PersistenceException when a DB error occurs
    */
    Optional<Long> findLastInvoiceNumber() throws PersistenceException;

    public static class InvoiceRecord {
	public String id;
	public long version;
	
	public LocalDateTime createdAt;
	public LocalDateTime updatedAt;
	public String entityState;

	public long number;
	public double amount;
	public LocalDate date;
	public double vat;
        public String state;
    }
}
