package ua.nure.dzhafarov.hotel.database.utils;

import org.apache.log4j.Logger;
import ua.nure.dzhafarov.hotel.database.entities.Bill;
import ua.nure.dzhafarov.hotel.database.entities.HotelRoom;
import ua.nure.dzhafarov.hotel.database.entities.Order;
import ua.nure.dzhafarov.hotel.database.entities.User;
import ua.nure.dzhafarov.hotel.database.enums.ApartmentClass;
import ua.nure.dzhafarov.hotel.database.enums.Role;
import ua.nure.dzhafarov.hotel.database.enums.RoomStatus;
import ua.nure.dzhafarov.hotel.exceptions.DBException;
import ua.nure.dzhafarov.hotel.exceptions.HotelExceptionMessages;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class DBManager {

    private static final Logger LOG = Logger.getLogger(DBManager.class);

    private static DBManager instance;

    public static synchronized DBManager getInstance() throws DBException {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DataSource dataSource;

    private DBManager() throws DBException {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/hotel");
            LOG.trace("Data source ==> " + dataSource);
        } catch (NamingException ex) {
            LOG.error(HotelExceptionMessages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
            throw new DBException(HotelExceptionMessages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
        }
    }

    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";

    private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE id=?";

    private static final String SQL_FIND_ALL_NOT_PAID_ORDERS = "SELECT * FROM orders WHERE id IN " + "(SELECT order_id FROM bills WHERE is_paid = 0)";

    private static final String SQL_FIND_HOTEL_ROOMS_BY_STATUS = "SELECT * FROM hotel_rooms WHERE status = ?";

    private static final String SQL_FIND_HOTEL_ROOMS_BY_APARTMENT_CLASS = "SELECT * FROM hotel_rooms WHERE apartment_class = ?";

    private static final String SQL_FIND_BILLS_BY_PAY = "SELECT * FROM bills WHERE is_paid = ?";


    public User findUserByLogin(String login) throws DBException {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = extractUser(rs);
            }

            con.commit();

        } catch (SQLException ex) {
            rollback(con);
            LOG.error(HotelExceptionMessages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
            throw new DBException(HotelExceptionMessages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
        } finally {
            close(con, pstmt, rs);
        }

        return user;
    }

    public User findUserById(long id) throws DBException {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_USER_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = extractUser(rs);
            }

            con.commit();

        } catch (SQLException ex) {
            rollback(con);
            LOG.error(HotelExceptionMessages.ERR_CANNOT_OBTAIN_USER_BY_ID, ex);
            throw new DBException(HotelExceptionMessages.ERR_CANNOT_OBTAIN_USER_BY_ID, ex);
        } finally {
            close(con, pstmt, rs);
        }

        return user;
    }

    public List<Order> findAllNotPaidOrders() throws DBException {
        List<Order> orders = new LinkedList<>();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {

            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_FIND_ALL_NOT_PAID_ORDERS);

            while (rs.next()) {
                Order o = extractOrder(rs);
                orders.add(o);
            }

            conn.commit();

        } catch (SQLException e) {
            rollback(conn);
            LOG.error(HotelExceptionMessages.ERR_CANNOT_OBTAIN_ALL_NOT_PAID_ORDERS, e);
            throw new DBException(HotelExceptionMessages.ERR_CANNOT_OBTAIN_ALL_NOT_PAID_ORDERS, e);
        } finally {
            close(conn, stmt, rs);
        }

        return orders;
    }

    public List<HotelRoom> findHotelRoomsByStatus(RoomStatus status) throws DBException {
        List<HotelRoom> hotelRooms = new LinkedList<>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL_FIND_HOTEL_ROOMS_BY_STATUS);
            pstmt.setInt(1, status.getRoomStatusValue());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                HotelRoom room = extractHotelRoom(rs);
                hotelRooms.add(room);
            }

            conn.commit();

        } catch (SQLException e) {
            rollback(conn);
            LOG.error(HotelExceptionMessages.ERR_CANNOT_OBTAIN_HOTEL_ROOMS_BY_STATUS, e);
            throw new DBException(HotelExceptionMessages.ERR_CANNOT_OBTAIN_HOTEL_ROOMS_BY_STATUS, e);
        } finally {
            close(conn, pstmt, rs);
        }

        return hotelRooms;
    }

    public List<HotelRoom> findHotelRoomsByApartmentClass(ApartmentClass apClass) throws DBException {
        List<HotelRoom> hotelRooms = new LinkedList<>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL_FIND_HOTEL_ROOMS_BY_APARTMENT_CLASS);
            pstmt.setInt(1, apClass.getApartmentClassValue());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                HotelRoom room = extractHotelRoom(rs);
                hotelRooms.add(room);
            }

            conn.commit();

        } catch (SQLException e) {
            rollback(conn);
            LOG.error(HotelExceptionMessages.ERR_CANNOT_OBTAIN_HOTEL_ROOMS_BY_APARTMENT_CLASS, e);
            throw new DBException(HotelExceptionMessages.ERR_CANNOT_OBTAIN_HOTEL_ROOMS_BY_APARTMENT_CLASS, e);
        } finally {
            close(conn, pstmt, rs);
        }

        return hotelRooms;
    }

    public List<Bill> findBillsByPay(boolean isPaid) throws DBException {
        List<Bill> bills = new LinkedList<>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL_FIND_BILLS_BY_PAY);
            pstmt.setInt(1, isPaid ? 1 : 0);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Bill bill = extractBill(rs);
                bills.add(bill);
            }
            
            conn.commit();
            
        } catch (SQLException e) {
            rollback(conn);
            LOG.error(HotelExceptionMessages.ERR_CANNOT_OBTAIN_BILLS_BY_PAY, e);
            throw new DBException(HotelExceptionMessages.ERR_CANNOT_OBTAIN_BILLS_BY_PAY, e);
        } finally {
            close(conn, pstmt, rs);
        }

        return bills;
    }
    
    

    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();

        user.setId(rs.getLong(DBSchema.ENTITY_ID));
        user.setFirstName(rs.getString(DBSchema.USER_FIRST_NAME));
        user.setLastName(rs.getString(DBSchema.USER_LAST_NAME));
        user.setLogin(rs.getString(DBSchema.USER_LOGIN));
        user.setPassword(rs.getString(DBSchema.USER_PASSWORD));
        user.setEmail(rs.getString(DBSchema.USER_EMAIL));
        user.setRole(Role.valueOf(rs.getInt(DBSchema.USER_ROLE)));
        user.setRegistrationTime(
                LocalDateTime.of(
                    rs.getDate(DBSchema.USER_REGISTRATION_TIME).toLocalDate(),
                    rs.getTime(DBSchema.USER_REGISTRATION_TIME).toLocalTime()
                )
        );

        return user;
    }

    private Order extractOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong(DBSchema.ENTITY_ID));
        order.setUserId(rs.getLong(DBSchema.ORDER_USER_ID));
        order.setHotelRoomId(rs.getLong(DBSchema.ORDER_HOTEL_ROOM_ID));
        order.setStayingDays(rs.getInt(DBSchema.ORDER_STAYING_DAYS));

        return order;
    }

    private HotelRoom extractHotelRoom(ResultSet rs) throws SQLException {
        HotelRoom hotelRoom = new HotelRoom();

        hotelRoom.setId(rs.getLong(DBSchema.ENTITY_ID));
        hotelRoom.setNumber(rs.getInt(DBSchema.HOTEL_ROOM_NUMBER));
        hotelRoom.setNumberOfPlaces(rs.getInt(DBSchema.HOTEL_ROOM_NUMBER_OF_PLACES));
        hotelRoom.setPricePerDay(rs.getBigDecimal(DBSchema.HOTEL_ROOM_PRICE_PER_DAY));
        hotelRoom.setApartmentClass(ApartmentClass.valueOf(rs.getInt(DBSchema.HOTEL_ROOM_APARTMENT_CLASS)));
        hotelRoom.setStatus(RoomStatus.valueOf(rs.getInt(DBSchema.HOTEL_ROOM_STATUS)));

        return hotelRoom;
    }

    private Bill extractBill(ResultSet rs) throws SQLException {
        Bill bill = new Bill();
        
        bill.setId(rs.getLong(DBSchema.BILL_ORDER_ID));
        bill.setPrice(rs.getBigDecimal(DBSchema.BILL_PRICE));
        bill.setPaid(rs.getBoolean(DBSchema.BILL_IS_PAID));
        bill.setCreationTime(
                LocalDateTime.of(
                        rs.getDate(DBSchema.BILL_CREATION_TIME).toLocalDate(),
                        rs.getTime(DBSchema.BILL_CREATION_TIME).toLocalTime()
                )
        );
        
        return bill;
    }
    
    private Connection getConnection() throws DBException {
        try {
            return dataSource.getConnection();
        } catch (SQLException ex) {
            LOG.error(HotelExceptionMessages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
            throw new DBException(HotelExceptionMessages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
        }
    }

    private void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                LOG.error(HotelExceptionMessages.ERR_CANNOT_CLOSE_RESULTSET, ex);
            }
        }
    }

    private void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                LOG.error(HotelExceptionMessages.ERR_CANNOT_CLOSE_STATEMENT, ex);
            }
        }
    }

    private void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                LOG.error(HotelExceptionMessages.ERR_CANNOT_CLOSE_CONNECTION, ex);
            }
        }
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
        close(con);
    }

    private void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                LOG.error("Cannot rollback transaction", ex);
            }
        }
    }
}
