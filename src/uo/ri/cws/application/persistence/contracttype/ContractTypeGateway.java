package uo.ri.cws.application.persistence.contracttype;

import java.time.LocalDateTime;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;

public interface ContractTypeGateway extends Gateway<ContractTypeRecord> {

    /**
     * Finds a contract type by its unique name.
     *
     * @param name the name of the contract type to look for
     * @return an Optional containing the matching ContractTypeRecord if found,
     *         or an empty Optional if no contract type exists with that name
     * @throws PersistenceException if any database access error occurs
     */
    Optional<ContractTypeRecord> findByName(String name) throws PersistenceException;
    
    public static class ContractTypeRecord {
        public String id;
        public long version;
        public String name;
        public double compensationDaysPerYear;

        public LocalDateTime createdAt;
        public LocalDateTime updatedAt;
        public String entityState;
    }
}
