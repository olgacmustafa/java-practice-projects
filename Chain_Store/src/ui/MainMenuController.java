package ui;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Parent;

public class MainMenuController {

    public void openSale(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                MainMenuController.class.getResource("/fxml/sale.fxml")
        );
        Scene scene = new Scene(loader.load(), 500, 500);

        Stage stage = new Stage();
        stage.setTitle("Satış Yap");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void openProduct() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/product.fxml")
            );
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Ürün Yönetimi");
            stage.setScene(new Scene(root, 500, 500));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openBranch() throws Exception {
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                MainMenuController.class.getResource("/fxml/branch.fxml")
        );
        javafx.scene.Scene scene = new javafx.scene.Scene(loader.load(), 500, 400);

        javafx.stage.Stage stage = new javafx.stage.Stage();
        stage.setTitle("Şube Yönetimi");
        stage.setScene(scene);
        stage.show();
    }
    
    
    @FXML
    private void openStock() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stock.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Stok Ekle");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    


}
