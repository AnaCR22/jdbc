package uo.ri.cws.application.ui.manager.mechanic.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class UpdateMechanicAction implements Action {

    @Override
    public void execute() throws BusinessException {
        // Get info
        String id = Console.readString("Type mechahic id to update");
        String name = Console.readString("Name");
        String surname = Console.readString("Surname");
        
        MechanicCrudService mcs = Factories.service.forMechanicCrudService();
        MechanicDto dto = mcs.findById(id).get();
        dto.name = name;
        dto.surname = surname;

        // update
	mcs.update(dto);

        // Print result
        Console.println("Mechanic updated");
    }
}