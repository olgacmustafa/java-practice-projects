package model;

import javafx.beans.property.SimpleIntegerProperty;

public class Stok {

    private Sube sube;
    private Urun urun;
    private SimpleIntegerProperty miktar;

    public Stok(Sube sube, Urun urun, int miktar) {
        this.sube = sube;
        this.urun = urun;
        this.miktar = new SimpleIntegerProperty(miktar);
    }

    public Sube getSube() {
        return sube;
    }

    public Urun getUrun() {
        return urun;
    }

    public int getMiktar() {
        return miktar.get();
    }

    public SimpleIntegerProperty miktarProperty() {
        return miktar;
    }

    public void arttir(int adet) {
        miktar.set(miktar.get() + adet);
    }

    public boolean azalt(int adet) {
        if (adet > miktar.get()) {
            return false;
        }
        miktar.set(miktar.get() - adet);
        return true;
    }
}
