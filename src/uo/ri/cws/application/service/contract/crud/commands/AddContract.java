package uo.ri.cws.application.service.contract.crud.commands;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.contract.crud.ContractDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddContract implements Command<ContractDto> {

    private ContractDto dto;
    private ContractGateway cg = Factories.persistence.forContract();
    private MechanicGateway mg = Factories.persistence.forMechanic();
    private ContractTypeGateway ctg = Factories.persistence.forContractType();
    private ProfessionalGroupGateway pgg = Factories.persistence.forProfessionalGroup();
    private PayrollGateway pg = Factories.persistence.forPayroll();

    public AddContract(ContractDto dto) {
	ArgumentChecks.isNotNull(dto);
	ArgumentChecks.isNotNull(dto.mechanic);
	ArgumentChecks.isNotNull(dto.contractType);
	ArgumentChecks.isNotNull(dto.professionalGroup);

	ArgumentChecks.isNotBlank(dto.mechanic.nif,
	    "Mechanic nif must not be empty or null");
	ArgumentChecks.isNotBlank(dto.contractType.name,
	    "Contract Type name must not be empty or null");
	ArgumentChecks.isNotBlank(dto.professionalGroup.name,
	    "Group name must not be empty or null");
	ArgumentChecks.isTrue(dto.annualBaseSalary > 0,
	    "Annual salary must be greater than 0");

	if ("FIXED_TERM".equalsIgnoreCase(dto.contractType.name)) {
	    ArgumentChecks.isNotNull(dto.endDate,
		"End date required for FIXED_TERM contracts");
	}

	dto.id = UUID.randomUUID()
		     .toString();
	dto.version = 1;
	this.dto = dto;
    }

    @Override
    public ContractDto execute() throws BusinessException {
	// Verify that mechanic exits
	Optional<MechanicRecord> om = mg.findByNif(dto.mechanic.nif);
	BusinessChecks.exists(om, "Mechanic does not exist");
	
	  // Verify type contract 
	Optional<ContractTypeRecord> oct = ctg.findByName(dto.contractType.name); 
	BusinessChecks.exists(oct, "Contract type " + dto.contractType.name + " does not exist");
	  
	  // Verify professional group 
	Optional<ProfessionalGroupRecord> opg = pgg.findByName(dto.professionalGroup.name);
	BusinessChecks.exists(opg, "Professional group " + dto.professionalGroup.name + " does not exist");

	// If there is a contract in force, terminate it
	Optional<ContractRecord> active = cg.findInForceByMechanic(om.get().id);
	if (active.isPresent()) {
	    ContractRecord old = active.get();
	    old.state = "TERMINATED";
	    old.endDate = LocalDate.now()
		.withDayOfMonth(LocalDate.now().lengthOfMonth());
	    old.settlement = calculateSettlement(old);
	    old.version++;
	    cg.update(old);
	}
	
	dto.startDate = LocalDate.now().plusMonths(1).withDayOfMonth(1);

	if (dto.endDate != null) {
	    BusinessChecks.isTrue(dto.endDate.isAfter(dto.startDate),
		"End date must be after start date");
	}

	dto.taxRate = calculateTaxRate(dto.annualBaseSalary);
	dto.state = "IN_FORCE";
	dto.settlement = 0.0;

	cg.add(ContractDtoAssembler.toRecord(dto));

	return dto;
    }
    

    private double calculateTaxRate(double annualBaseSalary) {
	if (annualBaseSalary < 12450)
	    return 0.19;
	if (annualBaseSalary < 20200)
	    return 0.24;
	if (annualBaseSalary < 35200)
	    return 0.30;
	if (annualBaseSalary < 60000)
	    return 0.37;
	if (annualBaseSalary < 300000)
	    return 0.45;
	return 0.47;
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
