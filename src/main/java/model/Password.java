package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Password {
    private final StringProperty password = new SimpleStringProperty();
    private final StringProperty hash = new SimpleStringProperty();
    private final StringProperty reduction = new SimpleStringProperty();

    public Password(String password, String hash, String reduction) {
        setPassword(password);
        setHash(hash);
        setReduction(reduction);
    }

    public String getReduction() {
        return reduction.get();
    }

    public StringProperty reductionProperty() {
        return reduction;
    }

    private void setReduction(String reduction) {
        this.reduction.set(reduction);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    private void setPassword(String password) {
        this.password.set(password);
    }

    public String getHash() {
        return hash.get();
    }

    public StringProperty hashProperty() {
        return hash;
    }

    private void setHash(String hash) {
        this.hash.set(hash);
    }
}
