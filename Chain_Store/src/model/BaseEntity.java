package model;

public abstract class BaseEntity {

    private String ad;

    public BaseEntity(String ad) {
        this.ad = ad;
    }

    public String getAd() {
        return ad;
    }

    @Override
    public String toString() {
        return ad;
    }
}
