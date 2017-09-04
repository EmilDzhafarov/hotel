package ua.nure.dzhafarov.hotel.database.enums;

public enum  RoomStatus {
    FREE(1, "Free"),
    RESERVED(2, "Reserved"),
    BUSY(3, "Busy"),
    UNAVAILABLE(4, "Unavailable");

    private int roomStatusValue;
    private String title;

    RoomStatus(int value, String title) {
        this.roomStatusValue = value;
    }

    public int getRoomStatusValue() {
        return roomStatusValue;
    }

    public String getTitle() {
        return title;
    }

    public static RoomStatus valueOf(int val) {
        for (RoomStatus rs : values()) {
            if (rs.roomStatusValue == val) {
                return rs;
            }
        }

        return null;
    }
}
