/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import static javafx.animation.Animation.INDEFINITE;
import javafx.scene.media.AudioClip;

/**
 * This is AudioPlayUtil, getAudioClip is used to create a handle of AudioClip,
 * so that this instance of audioClip can be played/stopped during certain 
 * scenario.
 * e.g. if user is in 
 * SplashScreen, LoginFormProtoType, RegisterForm, ForgetPasswordForm window
 * music will be played, other windows, music will be stopped.
 * @author Timothy
 */
public final class AudioPlayUtil {

    private AudioPlayUtil() {

    }

    private static AudioClip audio = null;
    
    // boolean variable to keep track of the status of music, i.e.
    // whether it is in play or not
    // isPlay = false means music stops/does not play
    // isPlay - true mean music plays/does not stop
    private static boolean isPlay;

    public static AudioClip getAudioClip() {
        if (audio == null) {
            audio = new AudioClip(AudioPlayUtil.class.getResource("../music/play.wav").toExternalForm());
            audio.setVolume(0.5f);
            audio.setCycleCount(INDEFINITE);
            isPlay = false;
        }
        return audio;
    }

    public static void play() {
        // if music is not playing, play it
        // if music is playing, skipping if
        if (!isPlay) {
            audio.play();
            isPlay = true;
        }
    }
    
    public static void stop() {
        // if music is playing, stop it
        // if music is stopped, skipping if
        if (isPlay) {
            audio.stop();
            isPlay = false;
        }
    }
}
