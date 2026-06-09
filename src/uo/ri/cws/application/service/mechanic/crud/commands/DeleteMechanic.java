package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteMechanic implements Command<Void> {

    private MechanicGateway mg = Factories.persistence.forMechanic();
    private ContractGateway contractGateway = Factories.persistence.forContract();
    private InterventionGateway interventionGateway = Factories.persistence.forIntervention();
    private WorkOrderGateway workOrderGateway = Factories.persistence.forWorkOrder();

    private final String idMechanic;

    public DeleteMechanic(String idMechanic) {
	ArgumentChecks.isNotBlank(idMechanic,
	    "Mechanic id must not be empty or null");
	this.idMechanic = idMechanic;
    }

    public Void execute() throws BusinessException {

	Optional<MechanicRecord> op = mg.findById(idMechanic);
	BusinessChecks.exists(op,
	    "Mechanic with id " + idMechanic + " does not exist");

	BusinessChecks.isTrue(workOrderGateway.findByMechanicId(idMechanic).isEmpty(), 
	    "Mechanic has work orders and cannot be deleted");
	BusinessChecks.isTrue(interventionGateway.findByMechanicId(idMechanic).isEmpty(),
	    "Mechanic has interventions and cannot be deleted");
	BusinessChecks.isTrue(contractGateway.findByMechanicId(idMechanic).isEmpty(),
	    "Mechanic has contracts and cannot be deleted");

	mg.remove(idMechanic);

	return null;
    }


}
