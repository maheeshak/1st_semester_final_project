package lk.ijse.hrms.util;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class TimeDate {
    public static void localDateAndTime( Label lblDate,Label lblTime) {

        lblDate.setText("  "+new SimpleDateFormat("dd : MM : 20yy ")
                .format(
                        new Date()
                ));


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),
                event -> lblTime.setText("  "+new SimpleDateFormat("hh:mm:ss").format(Calendar.getInstance().getTime()))),
                new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }
    public static void setGreeting(Label lblGreetings, ImageView wishImageView){
        Calendar c = Calendar.getInstance();
        LocalDate now = LocalDate.now();
        c.setTime(new Date());
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);

        //set image and label....
        if (hours >= 0 && hours < 12) {
            lblGreetings.setText("Good Morning !!!");
            wishImageView.setImage(new Image(TimeDate.class.getResourceAsStream("/assets/morning.png")));
        } else if (hours >= 12 && hours <= 18) {
            lblGreetings.setText("Good Afternoon !!!");
            wishImageView.setImage(new Image(TimeDate.class.getResourceAsStream("/assets/afternoon.png")));
        }else if(hours >= 19 && hours <= 22) {
            lblGreetings.setText("Good Evening !!!");
           wishImageView.setImage(new Image(TimeDate.class.getResourceAsStream("/assets/afternoon.png")));
        }else {
            lblGreetings.setText("Good Night !!!");
            wishImageView.setImage(new Image(TimeDate.class.getResourceAsStream("/assets/night.png")));
        }

    }
    public static void localTime( Label lblTime) {

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),
                event -> lblTime.setText("  "+new SimpleDateFormat("hh:mm:ss").format(Calendar.getInstance().getTime()))),
                new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }


}

