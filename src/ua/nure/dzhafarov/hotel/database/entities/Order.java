package ua.nure.dzhafarov.hotel.database.entities;

/**
 *  Order entities in database
 */

public class Order extends AbstractEntity {

    private static final long serialVersionUID = -5463362561657882742L;

    private long hotelRoomId;

    private long userId;

    private int stayingDays;

    public long getHotelRoomId() {
        return hotelRoomId;
    }

    public void setHotelRoomId(long hotelRoomId) {
        this.hotelRoomId = hotelRoomId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getStayingDays() {
        return stayingDays;
    }

    public void setStayingDays(int stayingDays) {
        this.stayingDays = stayingDays;
    }

    @Override
    public String toString() {
        return "Order{" +
                "hotelRoomId=" + hotelRoomId +
                ", userId=" + userId +
                ", stayingDays=" + stayingDays +
                '}';
    }
}
