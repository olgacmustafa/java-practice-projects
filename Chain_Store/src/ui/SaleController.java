package ui;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Sube;
import model.Urun;
import model.Stok;
import javafx.beans.property.SimpleStringProperty;





public class SaleController {

	
	 @FXML
	 private TextField txtQuantity;

	 

	 @FXML
	 public void initialize() {

	     // ComboBox'lar artık NESNE listesi alıyor
	     cmbBranch.setItems(GuiData.subeler);
	     cmbProduct.setItems(GuiData.urunler);

	     // TableView kolonları (şimdilik String gösteriyoruz)
	     colBranch.setCellValueFactory(data ->
	             new SimpleStringProperty(data.getValue().getSube().getAd())
	     );

	     colProduct.setCellValueFactory(data ->
	             new SimpleStringProperty(data.getValue().getUrun().getAd())
	     );

	     colQuantity.setCellValueFactory(
	             data -> data.getValue().quantityProperty().asObject()
	     );

	     tableSales.setItems(saleList);
	 }



	 @FXML
	 private ComboBox<Sube> cmbBranch;
	 @FXML
	 private ComboBox<Urun> cmbProduct;

	 @FXML
	 private void handleClick() {
		 
	     try {
	         Sube sube = cmbBranch.getValue();
	         Urun urun = cmbProduct.getValue();
	         String qtyText = txtQuantity.getText();

	         // 1️⃣ Boş kontrol
	         if (sube == null || urun == null || qtyText == null || qtyText.isEmpty()) {
	             new Alert(Alert.AlertType.ERROR, "Lütfen tüm alanları doldurun!").showAndWait();
	             return;
	         }

	         // 2️⃣ Sayıya çevirme
	         int qty = Integer.parseInt(qtyText);
	      // 🔹 Stok kontrolü
	         Stok bulunanStok = null;

	         for (Stok s : GuiData.stoklar) {
	             if (s.getSube().equals(sube) && s.getUrun().equals(urun)) {
	                 bulunanStok = s;
	                 break;
	             }
	         }

	         // Eğer stok yoksa, ilk kez ekle (başlangıç stok: 0)
	         if (bulunanStok == null) {
	             new Alert(Alert.AlertType.ERROR,
	                     "Bu şubede bu ürün için stok tanımlı değil!")
	                     .showAndWait();
	             return;
	         }

	         // Stok yeterli mi?
	         if (!bulunanStok.azalt(qty)) {
	             new Alert(Alert.AlertType.ERROR,
	                     "Yetersiz stok! Mevcut: " + bulunanStok.getMiktar())
	                     .showAndWait();
	             return;
	         }


	         if (qty <= 0) {
	             new Alert(Alert.AlertType.ERROR, "Adet pozitif bir sayı olmalı!").showAndWait();
	             return;
	         }

	         // 3️⃣ Tabloya ekle
	         saleList.add(new SaleRow(sube, urun, qty));

	         // 4️⃣ Alanları temizle
	         cmbBranch.getSelectionModel().clearSelection();
	         cmbProduct.getSelectionModel().clearSelection();
	         txtQuantity.clear();

	     } catch (NumberFormatException e) {
	         new Alert(Alert.AlertType.ERROR, "Adet sayısal bir değer olmalıdır!").showAndWait();
	     } catch (Exception e) {
	         e.printStackTrace();
	         new Alert(Alert.AlertType.ERROR, "Beklenmeyen hata: " + e.getMessage()).showAndWait();
	     }
	 }


	 @FXML
	 private TableView<SaleRow> tableSales;

	 @FXML
	 private TableColumn<SaleRow, String> colBranch;

	 @FXML
	 private TableColumn<SaleRow, String> colProduct;

	 @FXML
	 private TableColumn<SaleRow, Integer> colQuantity;

	 private ObservableList<SaleRow> saleList = FXCollections.observableArrayList();

	 @FXML
	 private void handleDelete() {

	     SaleRow selected = tableSales.getSelectionModel().getSelectedItem();

	     if (selected == null) {
	         new Alert(Alert.AlertType.WARNING,
	                 "Lütfen iade etmek için bir satış seçin!")
	                 .showAndWait();
	         return;
	     }

	     // 1️⃣ Satış bilgileri
	     Sube sube = selected.getSube();
	     Urun urun = selected.getUrun();
	     int adet = selected.quantityProperty().get();

	     // 2️⃣ Stoku geri ekle
	     for (Stok s : GuiData.stoklar) {
	         if (s.getSube().equals(sube) && s.getUrun().equals(urun)) {
	             s.arttir(adet);
	             break;
	         }
	     }

	     // 3️⃣ Satışı listeden kaldır
	     saleList.remove(selected);

	     // 4️⃣ Bilgilendirme (opsiyonel ama güzel)
	     new Alert(Alert.AlertType.INFORMATION,
	             "Satış silindi, stok güncellendi.")
	             .showAndWait();
	 }


	 }


