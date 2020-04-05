package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * Password which is a part of te rainbowTable.
 * each Password contains the password and the
 * reduction.
 */
class Password {
    /**
     * The password as plainText.
     */
    private final StringProperty
            password = new SimpleStringProperty();
    /**
     * The reduction of the MD-Hash of the password.
     */
    private final StringProperty reduction
            = new SimpleStringProperty();

    public Password(String password, String reduction) {
        setPassword(password);
        setReduction(reduction);
    }

    public String getReduction() {
        return reduction.get();
    }

    private void setReduction(String reduction) {
        this.reduction.set(reduction);
    }

    public String getPassword() {
        return password.get();
    }

    private void setPassword(String password) {
        this.password.set(password);
    }
}
