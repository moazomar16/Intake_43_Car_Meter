package Alarm;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author moazo
 */
import carometer_main.Carometerv10;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author moazo
 */


public class Alarm_Test 
{
  
    Clip clip;
    AudioInputStream audioInputStream;
    static String filePath;
  
    // constructor to initialize streams and clip
    public Alarm_Test() throws UnsupportedAudioFileException, IOException, LineUnavailableException 
    {
        // create AudioInputStream object
        audioInputStream = 
                AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile()); 
        // create clip reference
        clip = AudioSystem.getClip();
          
        // open audioInputStream to the clip
        clip.open(audioInputStream);
          
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
  
    public static void Play_Alarm()
    {
        try
        {
            filePath = "/E:/Moaz/Embedded Systems ITI/Java/java project/Alarm_Samples/Alarm-Fast-A1.aiff";
            Alarm_Test audioPlayer = new Alarm_Test();
             while (Carometerv10.Max_speed<=Carometerv10.Current_Speed)
                {
                    audioPlayer.clip.start();
                }
        } 
          
        catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) 
        {
            System.out.println("Error with playing sound.");
          
          }
    }
}

