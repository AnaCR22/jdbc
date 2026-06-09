package uo.ri.cws.application.persistence.professionalgroup;

import java.time.LocalDateTime;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;

public interface ProfessionalGroupGateway extends Gateway<ProfessionalGroupGateway.ProfessionalGroupRecord> {

    /**
     * Finds a professional group by its unique name.
     *
     * @param name the name of the professional group to search for
     * @return an Optional containing the professional group record if found, 
     *         or empty if not found
     * @throws PersistenceException if a database access error occurs
     */
    Optional<ProfessionalGroupRecord> findByName(String name) throws PersistenceException;

    public static class ProfessionalGroupRecord {
        public String id;
        public long version;
        public String name;
        public double trienniumPayment;
        public double productivityRate;

        public LocalDateTime createdAt;
        public LocalDateTime updatedAt;
        public String entityState;
    }

}
