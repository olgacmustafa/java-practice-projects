package ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Sube;
import model.Urun;
import model.Stok;

public class StockController {

    @FXML
    private ComboBox<Sube> cmbSube;

    @FXML
    private ComboBox<Urun> cmbUrun;

    @FXML
    private TextField txtMiktar;
    @FXML
    private TableView<Stok> tableStock;

    @FXML
    private TableColumn<Stok, String> colSube;

    @FXML
    private TableColumn<Stok, String> colUrun;

    @FXML
    private TableColumn<Stok, Integer> colMiktar;
    
    @FXML
    private void handleRefresh() {
        tableStock.refresh();
    }



    @FXML
    public void initialize() {
        cmbSube.setItems(GuiData.subeler);
        cmbUrun.setItems(GuiData.urunler);

        colSube.setCellValueFactory(
            data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getSube().getAd()
            )
        );

        colUrun.setCellValueFactory(
            data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getUrun().getAd()
            )
        );

        colMiktar.setCellValueFactory(
            data -> new javafx.beans.property.SimpleIntegerProperty(
                data.getValue().getMiktar()
            ).asObject()
        );

        tableStock.setItems(GuiData.stoklar);
    }


    @FXML
    private void handleAdd() {
        Sube sube = cmbSube.getValue();
        Urun urun = cmbUrun.getValue();
        String miktarText = txtMiktar.getText();

        if (sube == null || urun == null || miktarText.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Tüm alanları doldurun!").showAndWait();
            return;
        }

        int miktar;
        try {
            miktar = Integer.parseInt(miktarText);
            if (miktar <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Miktar pozitif sayı olmalı!").showAndWait();
            return;
        }

        // Aynı şube+ürün için stok var mı?
        for (Stok s : GuiData.stoklar) {
            if (s.getSube().equals(sube) && s.getUrun().equals(urun)) {
                s.arttir(miktar);
                txtMiktar.clear();
                return;
            }
        }

        // Yoksa yeni stok oluştur
        GuiData.stoklar.add(new Stok(sube, urun, miktar));
        txtMiktar.clear();
    }
}
