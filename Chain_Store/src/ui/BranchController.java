package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import model.Sube;





public class BranchController {
	@FXML
	private TextField txtBranchName;

	@FXML
	private TableView<Sube> tableBranches;

	@FXML
	private TableColumn<Sube, String> colBranchName;

	private ObservableList<Sube> branchList = GuiData.subeler;


	@FXML
	private void handleAdd() {
		String ad = txtBranchName.getText();

		if (ad == null || ad.isEmpty()) {
		    new Alert(Alert.AlertType.ERROR, "Şube adı boş olamaz!").showAndWait();
		    return;
		}

		Sube sube = new Sube(ad);
		GuiData.subeler.add(sube);

		txtBranchName.clear();
	}

	@FXML
	public void initialize() {
	    colBranchName.setCellValueFactory(
	        data -> new SimpleStringProperty(data.getValue().getAd())
	    );
	    tableBranches.setItems(branchList);
	}

}
