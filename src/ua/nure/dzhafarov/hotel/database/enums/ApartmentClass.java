package ua.nure.dzhafarov.hotel.database.enums;

public enum ApartmentClass {
    PRESIDENT(1, "President"),
    BUSINESS(2, "Business"),
    APARTMENT(3, "Apartment"),
    DE_LUX(4, "De Lux");

    int apartmentClassValue;
    String title;

    ApartmentClass(int value, String title) {
        this.apartmentClassValue = value;
        this.title = title;
    }

    public int getApartmentClassValue() {
        return apartmentClassValue;
    }

    public String getTitle() {
        return title;
    }

    public static ApartmentClass valueOf(int val) {
        for (ApartmentClass ac : values()) {
            if (ac.apartmentClassValue == val) {
                return ac;
            }
        }

        return null;
    }
}
