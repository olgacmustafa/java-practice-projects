package ui;

import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Urun;



public class ProductController {
	@FXML
	private TextField txtProductName;

	@FXML
	private TextField txtPrice;

	@FXML
	private TableView<ProductRow> tableProducts;

	@FXML
	private TableColumn<ProductRow, String> colName;

	@FXML
	private TableColumn<ProductRow, Double> colPrice;

	private ObservableList<ProductRow> productList = FXCollections.observableArrayList();


	@FXML
	private void handleAdd() {
	    String name = txtProductName.getText();
	    String priceText = txtPrice.getText();

	    if (name == null || name.isEmpty() || priceText.isEmpty()) {
	        new Alert(Alert.AlertType.ERROR, "Tüm alanları doldurun!").show();
	        return;
	    }

	    double price;
	    try {
	        price = Double.parseDouble(priceText);
	        if (price <= 0) throw new NumberFormatException();
	    } catch (NumberFormatException e) {
	        new Alert(Alert.AlertType.ERROR, "Fiyat pozitif sayı olmalı!").show();
	        return;
	    }

	    productList.add(new ProductRow(name, price));
	    txtProductName.clear();
	    txtPrice.clear();
	    GuiData.urunler.add(new Urun(name));

	}

    @FXML
    public void initialize() {
        colName.setCellValueFactory(data -> data.getValue().nameProperty());
        colPrice.setCellValueFactory(data -> data.getValue().priceProperty().asObject());
        tableProducts.setItems(productList);
    }
    @FXML
    private void handleDelete() {

        ProductRow selected = tableProducts.getSelectionModel().getSelectedItem();

        if (selected == null) {
            new Alert(Alert.AlertType.WARNING,
                    "Lütfen silmek için bir ürün seçin!")
                    .showAndWait();
            return;
        }

        String urunAdi = selected.getName();

        // 1️⃣ Ürünü ana listeden sil
        GuiData.urunler.removeIf(u -> u.getAd().equals(urunAdi));

        // 2️⃣ Ürün tablosundan sil
        productList.remove(selected);

        // 3️⃣ Bu ürüne ait stokları sil
        GuiData.stoklar.removeIf(
                stok -> stok.getUrun().getAd().equals(urunAdi)
        );

        new Alert(Alert.AlertType.INFORMATION,
                "Ürün ve ilgili stoklar silindi.")
                .showAndWait();
    }


}
