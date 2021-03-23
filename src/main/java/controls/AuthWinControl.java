package controls;

import dao.DAO;
import dao.impl.userImpl;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.SneakyThrows;
import model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jboss.threads.JBossExecutors;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AuthWinControl {
    @FXML
    private AnchorPane ancure;
    @FXML
    private TextField loginText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private Button enterButt;
    @FXML
    private Button newUserButt;
    @FXML
    private Label statusBar;

    private List<User> users;

    private final AtomicInteger failCount = new AtomicInteger(0);

    private int seconds = 15;

    private MyThread thread = new MyThread();



    @FXML
    public void initialize(){

        thread.start();
        enterButt.setOnAction(actionEvent -> {
            for (User user : users){
                User logUser;
                if (loginText.getText().equals(user.getLogin())){
                    logUser = user;
                    if (logUser.getPassword().equals(passwordText.getText())){
                        passwordText.getScene().getWindow().hide();
                        Stage stage = new Stage();
                        Parent root = new Pane();
                        stage.setScene(new Scene(root));
                        stage.show();
                    }else {
                        statusBar.setText("Не верный пароль");
                        failCount.set(failCount.intValue()+1);
                        if (failCount.get() > 3){
                            for (int i = 0; i <seconds ; i++) {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                statusBar.setText("введите правильный пароль");
                            }

                        }
                    }
                }
            }

        });
        newUserButt.setOnAction(actionEvent -> {

        });
    }
    public void initDB(){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        DAO<User, Integer> dao = new userImpl(factory);
        users = dao.readAll();
        factory.close();
    }

    class MyThread extends Thread{
        @Override
        public void run(){
            initDB();
        }
    }

}
