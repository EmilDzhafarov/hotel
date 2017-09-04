package ua.nure.dzhafarov.hotel.web.commands;

import ua.nure.dzhafarov.hotel.exceptions.HotelAppException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateBillCommand extends AbstractCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, HotelAppException {
        return null;
    }
}
