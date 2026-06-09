package uo.ri.cws.application.service.contract.crud.commands;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class FinishContract implements Command<Void> {

    private String contractId;
    private ContractGateway cg = Factories.persistence.forContract();
    private ContractTypeGateway ctg = Factories.persistence.forContractType();
    private PayrollGateway pg = Factories.persistence.forPayroll();

    public FinishContract(String contractId) {
	ArgumentChecks.isNotBlank(contractId,
	    "Contract id must not be null or empty");
	this.contractId = contractId;
    }

    @Override
    public Void execute() throws BusinessException {
	// Check if the contract exist
	Optional<ContractRecord> oc = cg.findById(contractId);
	BusinessChecks.exists(oc, "The contract does not exist");
	ContractRecord c = oc.get();

	// Check in force
	BusinessChecks.isTrue("IN_FORCE".equals(c.state),
	    "The contract is not in force");

	c.state = "TERMINATED";
	c.endDate = LocalDate.now()
			     .withDayOfMonth(LocalDate.now()
						      .lengthOfMonth());
	c.settlement = calculateSettlement(c);
	c.version++;

	cg.update(c);
	return null;
    }

    private double calculateSettlement(ContractRecord c)
	throws BusinessException {
	if (c.startDate == null || c.endDate == null)
	    return 0.0;

	long daysWorked = ChronoUnit.DAYS.between(c.startDate, c.endDate);
	if (daysWorked < 365)
	    return 0.0;

	Optional<ContractTypeRecord> oct = ctg.findById(c.contractTypeId);
	BusinessChecks.exists(oct, "Contract type not found");

	double daysPerYear = oct.get().compensationDaysPerYear;
	double avgDailySalary = calculateAverageDailySalary(c.id);
	long years = daysWorked / 365;
	

	return avgDailySalary * daysPerYear * years;
    }

    private double calculateAverageDailySalary(String contractId) {
	List<PayrollRecord> payrolls = pg.findLast12ByContractId(contractId);

	if (payrolls.isEmpty())
	    return 0.0;

	double totalGross = 0.0;
	for (PayrollRecord p : payrolls) {
	    totalGross += (p.baseSalary + p.extraSalary + p.productivityEarning
		+ p.trienniumEarning);
	}


	return totalGross / 365.0;
    }

}
