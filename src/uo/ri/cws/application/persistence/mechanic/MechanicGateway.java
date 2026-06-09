package uo.ri.cws.application.persistence.mechanic;

import java.time.LocalDateTime;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;

public interface MechanicGateway extends Gateway<MechanicRecord>{

    /**
     * Finds a mechanic record with a given nif
     * 
     * @param nif mechanic’s nif
     * @return a mechanic record or null value
     * @throws PersistenceException when a DB error occurs
     */
    Optional<MechanicRecord> findByNif(String nif) throws PersistenceException;

    public static class MechanicRecord {
	public String id;
	public long version;
	public LocalDateTime recordTime;
	public LocalDateTime updateTime;
	public String entityState;

	public String nif;
	public String name;
	public String surname;
    }
}
