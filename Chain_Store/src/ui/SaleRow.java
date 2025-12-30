package ui;

import javafx.beans.property.SimpleIntegerProperty;
import model.Sube;
import model.Urun;

public class SaleRow {

    private Sube sube;
    private Urun urun;
    private SimpleIntegerProperty quantity;

    public SaleRow(Sube sube, Urun urun, int quantity) {
        this.sube = sube;
        this.urun = urun;
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public Sube getSube() {
        return sube;
    }

    public Urun getUrun() {
        return urun;
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }
}
