package ua.nure.dzhafarov.hotel.database.utils;

public final class DBSchema {

    private DBSchema() {}

    // database
    public static final String DATABASE_NAME = "hotel";

    // tables
    public static final String USERS = "users";
    public static final String BILLS = "bills";
    public static final String ORDERS = "orders";
    public static final String HOTEL_ROOMS = "hotel_rooms";

    public static final String ENTITY_ID = "id";

    // users table
    public static final String USER_FIRST_NAME = "first_name";
    public static final String USER_LAST_NAME = "last_name";
    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD = "password";
    public static final String USER_EMAIL = "email";
    public static final String USER_ROLE = "role";
    public static final String USER_REGISTRATION_TIME = "registration_time";

    // hotel_rooms table
    public static final String HOTEL_ROOM_NUMBER = "number";
    public static final String HOTEL_ROOM_NUMBER_OF_PLACES = "number_of_places";
    public static final String HOTEL_ROOM_APARTMENT_CLASS = "apartment_class";
    public static final String HOTEL_ROOM_PRICE_PER_DAY = "price_per_day";
    public static final String HOTEL_ROOM_STATUS = "status";

    // orders table
    public static final String ORDER_HOTEL_ROOM_ID = "hotel_room_id";
    public static final String ORDER_USER_ID = "user_id";
    public static final String ORDER_STAYING_DAYS = "staying_days";

    // bills table
    public static final String BILL_ORDER_ID = "order_id";
    public static final String BILL_PRICE = "price";
    public static final String BILL_IS_PAID = "is_paid";
    public static final String BILL_CREATION_TIME = "creation_time";
}
