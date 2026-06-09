package uo.ri.cws.application.persistence.workorder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;

public interface WorkOrderGateway extends Gateway<WorkOrderRecord > {
    
    /**
     * Checks whether all given work orders exist in the database.
     *
     * @param ids the list of work order identifiers to check
     * @return true if all provided work orders exist, false otherwise
     * @throws PersistenceException if a database access error occurs
     */
    boolean existAll(List<String> ids) throws PersistenceException;

    /**
     * Checks whether all given work orders are in FINISHED state.
     *
     * @param ids the list of work order identifiers to verify
     * @return true if all provided work orders are finished, false otherwise
     * @throws PersistenceException if a database access error occurs
     */
    boolean areAllFinished(List<String> ids) throws PersistenceException;

    /**
     * Finds the total amount of a specific work order.
     *
     * @param id the identifier of the work order
     * @return the total amount associated with the work order
     * @throws PersistenceException if a database access error occurs
     */
    double findAmountById(String id) throws PersistenceException;

    /**
     * Links a work order to a given invoice.
     *
     * @param invoiceId the identifier of the invoice
     * @param workOrderId the identifier of the work order to link
     * @throws PersistenceException if a database access error occurs
     */
    void linkToInvoice(String invoiceId, String workOrderId) throws PersistenceException;

    /**
     * Marks a work order as INVOICED.
     *
     * @param workOrderId the identifier of the work order
     * @throws PersistenceException if a database access error occurs
     */
    void markAsInvoiced(String workOrderId) throws PersistenceException;

    /**
     * Updates the version and the updatedAt timestamp of a work order.
     *
     * @param workOrderId the identifier of the work order
     * @throws PersistenceException if a database access error occurs
     */
    void updateVersionAndTime(String workOrderId) throws PersistenceException;

    /**
     * Finds all not yet invoiced work orders belonging to a client.
     *
     * @param nif the client's NIF
     * @return a list of work orders that are not invoiced for this client
     * @throws PersistenceException if a database access error occurs
     */
    List<WorkOrderRecord> findNotInvoicedByClientNif(String nif) throws PersistenceException;

    /**
     * Finds all work orders assigned to a specific mechanic.
     *
     * @param idMechanic the identifier of the mechanic
     * @return a list of work orders belonging to this mechanic
     * @throws PersistenceException if a database access error occurs
     */
    List<WorkOrderRecord> findByMechanicId(String idMechanic) throws PersistenceException;

    /**
     * Checks whether the mechanic has any work orders recorded
     * within the specified date range.
     *
     * @param mechanicId the identifier of the mechanic
     * @param startDate the start date (inclusive)
     * @param endDate the end date (inclusive)
     * @return true if the mechanic has work orders in that period; false otherwise
     * @throws PersistenceException if a database access error occurs
     */
    boolean existsWorkOrdersByMechanicId(String mechanicId, LocalDate startDate,
                                         LocalDate endDate) throws PersistenceException;
    
    public static class WorkOrderRecord {
        public String id;
        public long version;
        
        public LocalDateTime createdAt;
        public LocalDateTime updatedAt;
        public String entityState;

        public String description;
        public LocalDateTime date;
        public double amount;
        public String state;
        
        public String mechanicId;
        public String invoiceId;
        public String vehicleId;
    }


}
