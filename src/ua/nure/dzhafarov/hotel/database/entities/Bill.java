package ua.nure.dzhafarov.hotel.database.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Bill entities in database
 */

public class Bill extends AbstractEntity {

    private static final long serialVersionUID = -8439261657882742L;

    private BigDecimal price;

    private boolean isPaid;

    private LocalDateTime creationTime;

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "orderId=" + getId() +
                ", isPaid=" + isPaid +
                ", price=" + price +
                ", creationTime=" + creationTime +
                '}';
    }
}
