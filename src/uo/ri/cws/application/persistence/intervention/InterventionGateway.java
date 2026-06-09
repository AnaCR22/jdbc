package uo.ri.cws.application.persistence.intervention;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;

public interface InterventionGateway extends Gateway<InterventionRecord> {

    /**
    * Finds all interventions done by a mechanic.
    * 
    * @param mechanicId mechanic’s id
    * @return a list of InterventionRecord (possibly empty)
    * @throws PersistenceException when a DB error occurs
    */
    List<InterventionRecord> findByMechanicId(String mechanicId) throws PersistenceException;

    /**
     * Checks whether a mechanic has registered any interventions
     * within the specified date range.
     *
     * @param mechanicId the identifier of the mechanic to check
     * @param startDate the start date (inclusive) of the search period
     * @param endDate the end date (inclusive) of the search period
     * @return true if there are interventions by this mechanic in the period; false otherwise
     * @throws PersistenceException if a database access error occurs
     */
    boolean existsInterventionsByMechanicId(String mechanicId, LocalDate startDate,
	LocalDate endDate);
    
    public static class InterventionRecord {
	public String id;
	public long version;

	public int minutes;
	public LocalDateTime date;
	public String mechanicId;
	public String workOrderId;
	
	public LocalDateTime createdAt;
	public LocalDateTime updatedAt;
	public String entityState;
    }

    
}
