package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Password {
    private StringProperty password = new SimpleStringProperty();
    private StringProperty hash = new SimpleStringProperty();
    private StringProperty reduction = new SimpleStringProperty();

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

    public void setReduction(String reduction) {
        this.reduction.set(reduction);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getHash() {
        return hash.get();
    }

    public StringProperty hashProperty() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash.set(hash);
    }
}
