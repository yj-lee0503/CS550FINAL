package application;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainController {

	// login variables
	@FXML
	private Label lblStatus;
	@FXML
	private TextField txtSSN;
	@FXML
	private javafx.scene.control.Button closeButton;
	// Main variables
	@FXML
	private Label lblStatus2;
	@FXML
	private TextField txtFN;
	@FXML
	private TextField txtMN;
	@FXML
	private TextField txtLN;
	@FXML
	private TextField txtEmpSSN;
	@FXML
	private DatePicker dpBD;
	@FXML
	private TextField txtAdd;
	@FXML
	private TextField txtSex;
	@FXML
	private TextField txtSalary;
	@FXML
	private TextField txtSupSSN;
	@FXML
	private TextField txtDNO;
	@FXML
	private TextField txtEmail;
	@FXML
	private javafx.scene.control.Button insertButton;
	// Main 2 variables
	private ChoiceBox<String> cbPname;
	private TableView tblProjects;
	private Label lblStatus3;
	private TextField Hours;
	// Main 3

	// Database Connection Info
	private String DBIINFO = "jdbc:oracle:thin:@apollo.vse.gmu.edu:1521:ite10g";
	private String DBUSERNAME = "ylee71";
	private String DBPASSW = "oadsoo";

	// login
	public void Login(ActionEvent event) throws Exception {

		// creating connection to database
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException x) {
			System.out.println("Driver could not be loaded");
		}
		Connection conn = DriverManager.getConnection(DBIINFO, DBUSERNAME, DBPASSW);
		Statement stmt = conn.createStatement();
		String sql;
		sql = "SELECT MGRSSN FROM DEPARTMENT";
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			if (txtSSN.getText().equals(rs.getString(1))) {
				lblStatus.setText("Login Success");
				// close current window
				Stage stage = (Stage) closeButton.getScene().getWindow();
				stage.close();
				// open a window for new employee insertion
				Stage primaryStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));
				Scene scene = new Scene(root, 600, 600);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();
			} else {
				lblStatus.setText("Login Failed. Only Department Managers can login.");
			}
		}
		conn.close();
		/*
		 * try { if (stmt != null) conn.close(); } catch (SQLException se) { } // do
		 * nothing try { if (conn != null) conn.close(); } catch (SQLException se) {
		 * se.printStackTrace(); }
		 */
	}

	// add employee
	public void Insert(ActionEvent event) throws Exception {
		// connecting to the database

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException x) {
			System.out.println("Driver could not be loaded");
		}
		Connection conn = DriverManager.getConnection(DBIINFO, DBUSERNAME, DBPASSW);
		Statement stmt = conn.createStatement();
		String sql;
		// SQL Code Generation
		sql = "INSERT INTO EMPLOYEE VALUES ('" + txtFN.getText() + "','" + txtMN.getText() + "','" + txtLN.getText()
				+ "','" + txtEmpSSN.getText() + "'," + "DATE '" + dpBD.getValue() + "','" + txtAdd.getText() + "','"
				+ txtSex.getText() + "'," + txtSalary.getText() + ",'" + txtSupSSN.getText() + "','" + txtDNO.getText()
				+ "','" + txtEmail.getText() + "')";
		stmt.executeUpdate(sql);
		conn.close();
		// open a window for new employee insertion
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Main2.fxml"));
		Scene scene = new Scene(root, 600, 600);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
@FXML
	public void retreive(ActionEvent event) throws Exception {

		// retrieve the project names
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException x) {
			System.out.println("Driver could not be loaded");
		}
		Connection conn = DriverManager.getConnection(DBIINFO, DBUSERNAME, DBPASSW);
		Statement stmt = conn.createStatement();
		String sql;
		sql = "SELECT PNAME FROM PROJECT";
		ResultSet rs = stmt.executeQuery(sql);
		List<String> pnames = new ArrayList<String>();
		while (rs.next()) {
			pnames.add(rs.getString(1));
			cbPname.getItems().addAll(pnames);
		};
		conn.close();
	}
}
