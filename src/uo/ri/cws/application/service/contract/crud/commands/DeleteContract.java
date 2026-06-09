package uo.ri.cws.application.service.contract.crud.commands;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteContract implements Command<Void > {

    private String id;
    private ContractGateway cg = Factories.persistence.forContract();
    private InterventionGateway ig = Factories.persistence.forIntervention();
    private PayrollGateway pg = Factories.persistence.forPayroll();
    
    public DeleteContract(String id) {
        ArgumentChecks.isNotBlank(id, "Contract id cannot be null or empty");
        this.id = id;
    }
    
    @Override
    public Void  execute() throws BusinessException {
	Optional<ContractRecord> oc = cg.findById(id);
        BusinessChecks.exists(oc, "Contract does not exist");

        ContractRecord record = oc.get();

        // Check interventions
        /*boolean hasInterventions = ig.existsInterventionsByMechanicId(
            record.mechanicId, record.startDate, record.endDate);*/
        
        List<InterventionRecord> interventions  = ig.findByMechanicId(record.mechanicId);
        LocalDate notNullEndDate = record.endDate != null ? record.endDate : LocalDate.now();
        boolean hasInterventions = interventions.stream().anyMatch(i -> {
            	LocalDate date = i.date.toLocalDate();
        	return !date.isBefore(record.startDate) && !date.isAfter(notNullEndDate);
        });
        
        BusinessChecks.isFalse(hasInterventions,
            "Contract cannot be deleted: mechanic has interventions during this period");

        // Check payrolls
        BusinessChecks.isFalse(pg.countByContractId(id) > 0,
            "Contract cannot be deleted: contract has payrolls");

        cg.remove(id);	
        return null;
    }

}
