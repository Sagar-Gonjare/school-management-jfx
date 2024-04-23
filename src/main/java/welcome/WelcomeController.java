package welcome;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import login.Login;
import registration.RegistrationInfo;


public class WelcomeController implements Initializable{
	@FXML
	private Button login;
	
	@FXML
	private Button registration;
	
	@FXML
	private ImageView logo;
	
	@FXML
	private ImageView background_image;
	
	@FXML
	private ImageView login_button;
	
	@FXML
	private ImageView registration_button;
	
	@FXML
	private Label quote_line_1;
	
	@FXML
	private Label quote_line_2;
	
	@FXML
	private Label title;
	
	@FXML
	private Button close;
	

	
	public void close(ActionEvent event) {
		 System.exit(0);
	}
	
	public void insert(ActionEvent event) throws IOException {
	
	}
	
	public void login(ActionEvent event) throws IOException {
		new Login().show();
        
	}
	
	public void registration(ActionEvent event) {
		new RegistrationInfo().show();
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
	  
	  // translate
	  TranslateTransition translate = new TranslateTransition();
	  translate.setDelay(Duration.millis(1200));
	  translate.setNode(logo);
	  translate.setDuration(Duration.millis(1000));
	  translate.setCycleCount(1);
	  //translate.setByX(500);
	  translate.setByY(-210);
	  //translate.setAutoReverse(true);
	  translate.play();
	  
	  // translate
	  TranslateTransition translate2 = new TranslateTransition();
	  translate2.setDelay(Duration.millis(2200));
	  translate2.setNode(title);
	  translate2.setDuration(Duration.millis(1000));
	  translate2.setCycleCount(1);
	  //translate.setByX(500);
	  translate2.setByY(314);
	  //translate2.setAutoReverse(true);
	  translate2.play();
	  
	  // rotate
	  RotateTransition rotate = new RotateTransition();
	  rotate.setNode(logo);
	  rotate.setDuration(Duration.millis(450));
	  rotate.setCycleCount(2);
	  rotate.setInterpolator(Interpolator.LINEAR);
	  rotate.setByAngle(360);
	  rotate.setAxis(Rotate.Z_AXIS);
	  rotate.play();
	   
	  TranslateTransition translate3 = new TranslateTransition();
	  translate3.setDelay(Duration.millis(2200));
	  translate3.setNode(login);
	  translate3.setDuration(Duration.millis(1000));
	  translate3.setCycleCount(1);
	  //translate.setByX(500);
	  translate3.setByY(420);
	  //translate2.setAutoReverse(true);
	  translate3.play();
	  
	  TranslateTransition translate4 = new TranslateTransition();
	  translate4.setDelay(Duration.millis(2200));
	  translate4.setNode(login_button);
	  translate4.setDuration(Duration.millis(1000));
	  translate4.setCycleCount(1);
	  //translate.setByX(500);
	  translate4.setByY(412);
	  //translate2.setAutoReverse(true);
	  translate4.play();
	  
	  TranslateTransition translate5 = new TranslateTransition();
	  translate5.setDelay(Duration.millis(2200));
	  translate5.setNode(registration_button);
	  translate5.setDuration(Duration.millis(1000));
	  translate5.setCycleCount(1);
	  //translate.setByX(500);
	  translate5.setByY(492);
	  //translate2.setAutoReverse(true);
	  translate5.play();
	  
	  TranslateTransition translate6 = new TranslateTransition();
	  translate6.setDelay(Duration.millis(2200));
	  translate6.setNode(registration);
	  translate6.setDuration(Duration.millis(1000));
	  translate6.setCycleCount(1);
	  //translate.setByX(500);
	  translate6.setByY(500);
	  //translate2.setAutoReverse(true);
	  translate6.play();

	  
	  TranslateTransition translate7 = new TranslateTransition();
	  translate7.setDelay(Duration.millis(2200));
	  translate7.setNode(quote_line_1);
	  translate7.setDuration(Duration.millis(1000));
	  translate7.setCycleCount(1);
	  //translate.setByX(500);
	  translate7.setByY(-90);
	  //translate2.setAutoReverse(true);
	  translate7.play();
	  
	  TranslateTransition translate8 = new TranslateTransition();
	  translate8.setDelay(Duration.millis(2200));
	  translate8.setNode(quote_line_2);
	  translate8.setDuration(Duration.millis(1000));
	  translate8.setCycleCount(1);
	  //translate.setByX(500);
	  translate8.setByY(-60);
	  //translate2.setAutoReverse(true);
	  translate8.play();
	  
	  // scale
	  ScaleTransition scale = new ScaleTransition();
	  scale.setDelay(Duration.millis(200));
	  scale.setNode(logo);
	  scale.setDuration(Duration.millis(1000));
	  scale.setCycleCount(2);
	  scale.setInterpolator(Interpolator.LINEAR);
	  scale.setByX(2.0);
	  scale.setByY(2.0);
	  scale.setAutoReverse(true);
	  scale.play();
	  
	  ScaleTransition scale2 = new ScaleTransition();
	  scale2.setDelay(Duration.millis(4200));
	  scale2.setNode(quote_line_1);
	  scale2.setDuration(Duration.millis(100));
	  scale2.setCycleCount(2);
	  scale2.setInterpolator(Interpolator.LINEAR);
	  scale2.setByX(0.05);
	  scale2.setByY(0.05);
	  scale2.setAutoReverse(true);
	  scale2.play();
	  
	  ScaleTransition scale3 = new ScaleTransition();
	  scale3.setDelay(Duration.millis(4200));
	  scale3.setNode(quote_line_2);
	  scale3.setDuration(Duration.millis(100));
	  scale3.setCycleCount(2);
	  scale3.setInterpolator(Interpolator.LINEAR);
	  scale3.setByX(0.05);
	  scale3.setByY(0.05);
	  scale3.setAutoReverse(true);
	  scale3.play();
	  
	 }
}
