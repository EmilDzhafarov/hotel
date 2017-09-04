package ua.nure.dzhafarov.hotel.database.enums;

public enum Role {
    MANAGER(1), CLIENT(2);

    private int roleValue;

    Role(int value) {
        this.roleValue = value;
    }

    public int getRoleValue() {
        return roleValue;
    }

    public static Role valueOf(int val) {
        for (Role role : values()) {
            if (role.roleValue == val) {
                return role;
            }
        }

        return null;
    }
}
