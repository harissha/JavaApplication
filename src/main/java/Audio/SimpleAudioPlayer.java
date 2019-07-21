package Audio;

// Java program to play an Audio
// file using Clip Object
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.*;

public class SimpleAudioPlayer
{

    // to store current position
    Long currentFrame;
    Clip clip;

    //Clip clip1;

    // current status of clip
    String status;

    AudioInputStream audioInputStream;
    static String filePath;
    double durationInSeconds;

    AudioInputStream din = null;

    // constructor to initialize streams and clip
    public SimpleAudioPlayer()
            throws UnsupportedAudioFileException,
            IOException, LineUnavailableException
    {
        // create AudioInputStream object
        audioInputStream =
                AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());


        // for play function
        AudioFormat baseFormat  = audioInputStream.getFormat();
        long frames = audioInputStream.getFrameLength();
        durationInSeconds = (frames+0.0) / baseFormat.getFrameRate();

        // for mp3 play
        AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(),
                16,
                baseFormat.getChannels(),
                baseFormat.getChannels() * 2,
                baseFormat.getSampleRate(),
                false);
        din = AudioSystem.getAudioInputStream(decodedFormat, audioInputStream);



        // create clip reference
        clip = AudioSystem.getClip();
      //  clip1 = AudioSystem.getClip();

        // open audioInputStream to the clip
        clip.open(audioInputStream);
        //clip1.open(din);
        clip.loop(0);
        //clip1.loop(1);
        //clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void main(String[] args)
    {
        try
        {
            filePath = "C:\\Users\\harissha\\Desktop\\Old.wav";
            SimpleAudioPlayer audioPlayer =
                    new SimpleAudioPlayer();

            audioPlayer.play();
            Scanner sc = new Scanner(System.in);

            while (true)
            {
                System.out.println("1. play");
                System.out.println("2. pause");
                System.out.println("3. resume");
                System.out.println("4. restart");
                System.out.println("5. stop");
                System.out.println("6. Jump to specific time");
                System.out.println("7. Loop continuously");
                int c = sc.nextInt();
                audioPlayer.gotoChoice(c);
                if (c == 5)
                    break;
            }
            sc.close();
        }

        catch (Exception ex)
        {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();

        }
    }

    // Work as the user enters his choice

    private void gotoChoice(int c)
            throws IOException, LineUnavailableException, UnsupportedAudioFileException
    {
        switch (c)
        {
            case 1:
                resumeAudio();
                break;
            case 2:
                pause();
                break;
            case 3:
                resumeAudio();
                break;
            case 4:
                restart();
                break;
            case 5:
                stop();
                break;
            case 6:
                System.out.println("Enter time (" + 0 +
                        ", " + clip.getMicrosecondLength() + ")");
                Scanner sc = new Scanner(System.in);
                long c1 = sc.nextLong();
                jump(c1);
                break;
            case 7:
                loopAudioContinuously();
                break;

        }

    }

    // Method to play the audio
    public void play()throws UnsupportedAudioFileException,
            IOException, LineUnavailableException
    {
        //start the clip
        clip.start();
        //clip1.start();
        /*try{
            TimeUnit.SECONDS.sleep((long)durationInSeconds);
        }catch (InterruptedException ie){
            ie.getMessage();
        }
        clip.stop();*/
        //clip.setMicrosecondPosition(0);


        status = "play";
    }

    // Method to pause the audio
    public void pause()
    {
        if (status.equals("paused"))
        {
            System.out.println("audio is already paused");
            return;
        }
        this.currentFrame =
                this.clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }

    // Method to resume the audio
    public void resumeAudio() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException
    {
        if (status.equals("play"))
        {
            System.out.println("Audio is already "+
                    "being played");
            return;
        }
        clip.close();
        resetAudioStream();
        clip.setMicrosecondPosition(currentFrame);
        this.play();
    }

    // Method to restart the audio
    public void restart() throws IOException, LineUnavailableException,
            UnsupportedAudioFileException
    {
        clip.stop();
        clip.close();
        resetAudioStream();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        this.play();
    }

    // Method to stop the audio
    public void stop() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException
    {
        currentFrame = 0L;
        clip.stop();
        clip.close();
    }

    // Method to jump over a specific part
    public void jump(long c) throws UnsupportedAudioFileException, IOException,
            LineUnavailableException
    {
        if (c > 0 && c < clip.getMicrosecondLength())
        {
            clip.stop();
            clip.close();
            resetAudioStream();
            currentFrame = c;
            clip.setMicrosecondPosition(c);
            this.play();
        }
    }

    // Method to resume the audio
    public void loopAudioContinuously() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException
    {
        /*this.currentFrame =
                this.clip.getMicrosecondPosition();
        clip.stop();
        clip.close();
        resetAudioStream();
        clip.setMicrosecondPosition(currentFrame);*/
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        //this.play();

    }

    // Method to reset audio stream
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException,
            LineUnavailableException
    {
        audioInputStream = AudioSystem.getAudioInputStream(
                new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(0);
        //clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}
