package ua.nure.dzhafarov.hotel.exceptions;

public class HotelAppException extends Exception {
    private static final long serialVersionUID = 8288779062647218916L;

    public HotelAppException() {}

    public HotelAppException(String message) {
        super(message);
    }

    public HotelAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public HotelAppException(Throwable cause) {
        super(cause);
    }

    public HotelAppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
