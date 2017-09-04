package ua.nure.dzhafarov.hotel.web.commands;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {

    private static final Logger LOG = Logger.getLogger(CommandContainer.class);

    private static Map<String, AbstractCommand> commands = new TreeMap<String, AbstractCommand>();

    static {
        // common commands
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("viewSettings", new ViewSettingsCommand());
        commands.put("noCommand", new NoCommand());

        // manager commands
        commands.put("listOrders", new ListOrdersCommand());
        commands.put("createBill", new CreateBillCommand());

        // client commands
        commands.put("listHotelRooms", new ListHotelRoomsCommand());
        commands.put("createOrder", new CreateOrderCommand());
        commands.put("cancelOrder", new CancelOrderCommand());
        commands.put("payBill", new PayBillCommand());

        LOG.debug("AbstractCommand container was successfully initialized");
        LOG.trace("Number of commands --> " + commands.size());
    }

    public static AbstractCommand get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            LOG.trace("AbstractCommand not found, name --> " + commandName);
            return commands.get("noCommand");
        }

        return commands.get(commandName);
    }
}
