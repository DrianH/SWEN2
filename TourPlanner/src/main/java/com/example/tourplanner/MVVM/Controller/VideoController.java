package com.example.tourplanner.MVVM.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class VideoController implements Initializable {

    private static final String videoPath = Paths.get("src",
            "main",
            "resources",
            "video").toFile().getAbsolutePath()+"/java-video.mp4";

    @FXML
    private MediaView mediaView;

    private File file;
    private Media media;
    private MediaPlayer mediaPlayer;
    private boolean videoIsPlaying = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("-> "+videoPath);
        file = new File(videoPath);
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);
    }

    public void playPauseClicked(ActionEvent actionEvent) {
        if(videoIsPlaying)
            mediaPlayer.pause();
        else
            mediaPlayer.play();

        videoIsPlaying=!videoIsPlaying;
    }
}
