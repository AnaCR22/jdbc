package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class ListAllMechanicsAction implements Action {
    @Override
    public void execute() throws BusinessException {

        Console.println("\nList of mechanics \n");

        MechanicCrudService mcs = Factories.service.forMechanicCrudService();
        List<MechanicDto> mechanics = mcs.findAll();

        if (mechanics.isEmpty()) {
            Console.println("There are no mechanics registered yet.");
        } else {
            mechanics.forEach(m -> Printer.printMechanic(m));
        }
    }
}