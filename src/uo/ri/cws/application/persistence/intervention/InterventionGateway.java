package uo.ri.cws.application.persistence.intervention;

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
