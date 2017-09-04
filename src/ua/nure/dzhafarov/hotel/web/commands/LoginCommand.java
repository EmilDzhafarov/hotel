package ua.nure.dzhafarov.hotel.web.commands;

import org.apache.log4j.Logger;
import ua.nure.dzhafarov.hotel.database.entities.User;
import ua.nure.dzhafarov.hotel.database.enums.Role;
import ua.nure.dzhafarov.hotel.database.utils.DBManager;
import ua.nure.dzhafarov.hotel.exceptions.HotelAppException;
import ua.nure.dzhafarov.hotel.web.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCommand extends AbstractCommand {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, HotelAppException {

        LOG.debug("LoginCommand starts");
        HttpSession session = request.getSession();

        DBManager manager = DBManager.getInstance();
        String login = request.getParameter("login");
        LOG.trace("Request parameter: logging --> " + login);

        String password = request.getParameter("password");
        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            throw new HotelAppException("Login/password cannot be empty");
        }

        User user = manager.findUserByLogin(login);
        LOG.trace("Found in DB: user --> " + user);

        if (user == null || !password.equals(user.getPassword())) {
            throw new HotelAppException("Cannot find user with such login/password");
        }

        Role userRole = user.getRole();
        LOG.trace("userRole --> " + userRole);

        String forward = Path.PAGE_ERROR_PAGE;

        if (userRole == Role.MANAGER) {
            forward = Path.COMMAND_LIST_ORDERS;
        }

        if (userRole == Role.CLIENT) {
            forward = Path.COMMAND_LIST_HOTEL_ROOMS;
        }

        session.setAttribute("user", user);
        LOG.trace("Set the session attribute: user --> " + user);

        session.setAttribute("userRole", userRole);
        LOG.trace("Set the session attribute: userRole --> " + userRole);

        LOG.info("User " + user + " logged as " + userRole.toString().toLowerCase());

        LOG.debug("Command finished");
        return forward;
    }
}
