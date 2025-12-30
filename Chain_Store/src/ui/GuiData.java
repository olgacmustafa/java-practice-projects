package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Sube;
import model.Urun;
import model.Stok;

public class GuiData {

    public static ObservableList<Sube> subeler =
            FXCollections.observableArrayList();

    public static ObservableList<Urun> urunler =
            FXCollections.observableArrayList();
    
    public static ObservableList<Stok> stoklar =
            FXCollections.observableArrayList();

}
