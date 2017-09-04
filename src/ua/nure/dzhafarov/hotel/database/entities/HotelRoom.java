package ua.nure.dzhafarov.hotel.database.entities;


import ua.nure.dzhafarov.hotel.database.enums.ApartmentClass;
import ua.nure.dzhafarov.hotel.database.enums.RoomStatus;

import java.math.BigDecimal;

/**
 * HotelRoom entities in database
 */

public class HotelRoom extends AbstractEntity {

    private static final long serialVersionUID = 16889036257658388L;

    private int number;

    private int numberOfPlaces;

    private ApartmentClass apartmentClass;

    private BigDecimal pricePerDay;

    private RoomStatus status;


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public void setNumberOfPlaces(int numberOfPlaces) {
        this.numberOfPlaces = numberOfPlaces;
    }

    public ApartmentClass getApartmentClass() {
        return apartmentClass;
    }

    public void setApartmentClass(ApartmentClass apartmentClass) {
        this.apartmentClass = apartmentClass;
    }

    public BigDecimal getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(BigDecimal pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "HotelRoom{" +
                "number=" + number +
                ", numberOfPlaces=" + numberOfPlaces +
                ", apartmentClass=" + apartmentClass +
                ", pricePerDay=" + pricePerDay +
                ", status=" + status +
                '}';
    }
}
