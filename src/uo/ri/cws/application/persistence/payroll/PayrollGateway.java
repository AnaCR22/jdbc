package uo.ri.cws.application.persistence.payroll;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;

public interface PayrollGateway extends Gateway<PayrollRecord> {

    /**
     * Counts how many payrolls have been generated for a given contract.
     *
     * @param id the identifier of the contract
     * @return the number of payrolls registered for that contract
     * @throws PersistenceException if a database access error occurs
     */
    int countByContractId(String id) throws PersistenceException;

    /**
     * Finds the last 12 payrolls issued for a given contract, ordered by date descending.
     *
     * <p>This method is typically used to calculate the average daily gross salary
     * for settlement computation upon contract termination.</p>
     *
     * @param contractId the identifier of the contract
     * @return a list containing up to 12 payroll records, or an empty list if none exist
     * @throws PersistenceException if a database access error occurs
     */
    List<PayrollRecord> findLast12ByContractId(String contractId) throws PersistenceException;

    public static class PayrollRecord {
	public String id;
	public long version;

	public LocalDateTime recordTime;
	public LocalDateTime updateTime;
	public String entityState;

	public String contractId;
	public LocalDate date;

	// Earnings
	public double baseSalary;
	public double extraSalary;
	public double productivityEarning;
	public double trienniumEarning;

	// Deductions
	public double taxDeduction;
	public double nicDeduction;

    }



}
