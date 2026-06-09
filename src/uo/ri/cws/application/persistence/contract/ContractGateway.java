package uo.ri.cws.application.persistence.contract;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;

public interface ContractGateway extends Gateway<ContractRecord> {

    /**
     * Finds the contract in force for a given mechanic, if any.
     *
     * @param id the mechanic's id
     * @return an Optional containing the in-force contract record, or an empty Optional
     *         if the mechanic currently has no active contract
     * @throws PersistenceException if any database access error occurs
     */
    public Optional<ContractRecord> findInForceByMechanic(String id);

    /**
     * Finds all contracts that are currently in force in the system.
     *
     * @return a list of contract records with state 'IN_FORCE';
     *         an empty list if there are no contracts in that state
     * @throws PersistenceException if any database access error occurs
     */
    public List<ContractRecord> findInForce();

    /**
     * Finds all contracts associated with a given mechanic.
     *
     * @param idMechanic the id of the mechanic
     * @return a list of all contract records belonging to that mechanic;
     *         an empty list if the mechanic has no contracts
     * @throws PersistenceException if any database access error occurs
     */
    public List<ContractRecord> findByMechanicId(String idMechanic);

    /**
     * Checks whether there is any contract linked to a given contract type.
     *
     * @param id the id of the contract type
     * @return true if there is at least one contract using this contract type,
     *         false otherwise
     * @throws PersistenceException if any database access error occurs
     */
    public boolean existsByContractTypeId(String id);

    /**
     * Checks whether there is any contract linked to a given professional group.
     *
     * @param id the id of the professional group
     * @return true if there is at least one contract using this  professional group,
     *         false otherwise
     * @throws PersistenceException if any database access error occurs
     */
    public boolean existsByProfessionalGroupId(String id);


    public static class ContractRecord {
	public String id;
	public long version;

	public String mechanicId;
	public String contractTypeId;
	public String professionalGroupId;

	public LocalDate startDate;
	public LocalDate endDate;
	public double annualBaseSalary;
	public double taxRate;

	// Filled in reading operations
	public double settlement;
	public String state;

	public LocalDateTime createdAt;
	public LocalDateTime updatedAt;
	public String entityState;
    }

}
