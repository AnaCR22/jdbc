package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class ListMechanicAction implements Action {

    @Override
    public void execute() throws BusinessException {

        // Get info
        String nif = Console.readString("nif");

        Console.println("\nMechanic information \n");

        MechanicCrudService service = Factories.service.forMechanicCrudService();
        Optional<MechanicDto> md = service.findByNif(nif);

        if (md.isEmpty()) {
            Console.println("No mechanic found with NIF: " + nif);
        } else {
            Printer.printMechanic(md.get());
        }
    }
}