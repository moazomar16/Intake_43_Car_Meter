/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicationPK;

import static Alarm.Alarm_Test.Play_Alarm;
import carometer_main.Carometerv10;
import static carometer_main.Carometerv10.LAT;
import static carometer_main.Carometerv10.LNG;
import static carometer_main.Carometerv10.alarmStatus;
import static carometer_main.Carometerv10.latValue;
import static carometer_main.Carometerv10.longValue;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Label;



/**
 *
 * @author moazo
 */
public class Communication {
     SerialPort serialPort;
      InputStream in;
    Thread serialReadThread;
    String GPSdata[] ;
    public void connect()
    {
        Enumeration e = CommPortIdentifier.getPortIdentifiers();
        System.out.println("There are any ports?" + e.hasMoreElements());
        while (e.hasMoreElements()) {
            CommPortIdentifier portIdentifier = (CommPortIdentifier) e.nextElement();

            if (portIdentifier.isCurrentlyOwned()) {
                System.out.println("Error!!!... Port is Busy");
            } else {
                if (portIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL){
                    try{
                        String portName = portIdentifier.getName();
                        System.out.println(portName);
                        serialPort = (SerialPort) portIdentifier.open(this.getClass().getName(), 2000);
                        serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                        serialReadThread = new Thread(new SerialReader(in));
                        System.out.println("serial thread started");
                        try{
                            in = serialPort.getInputStream();
                        }catch (IOException ex) {
                            System.err.println(ex.getMessage());
                        }
                    }catch (PortInUseException ex) {
                        Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedCommOperationException ex) {
                        Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }
    }
    
  public void disconnect()
  {
        try {
            serialPort.close();
            in.close();
            serialReadThread.stop();
        } catch (IOException ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
    class SerialReader implements Runnable{
        
        private SerialReader(InputStream in) {            
        }
        @Override
        public void run() {
            byte[] buffer = new byte[1024]; 
            int len = -1;
            
            try{
                while ((len = in.read(buffer)) > -1) {
                   
                    String str = new String(buffer, 0, len);
                    System.out.println(str);
					 Platform.runLater(() -> {
                                             Play_Alarm();
                                //here update
                                // v or i_lat_lng_speed
                                //Carometerv10.LAT
                                if(str.charAt(0)== 'V')
                                { 
                                     System.out.println("Record is valid, GPS is CONNECTED");
                                     System.out.println("string after validation" + str );
                                GPSdata = new String[3];
                               
                                 String  AfterV = str.substring(2);
                                GPSdata = AfterV.split("_");
                                Carometerv10.LAT = Double.parseDouble(GPSdata[0]);
                                 Carometerv10.LNG = Double.parseDouble(GPSdata[1]);
                                 Carometerv10.Current_Speed = Double.parseDouble(GPSdata[2]);
                                 Carometerv10.gauge.setValue(Carometerv10.Current_Speed);
                                 Carometerv10.latValue = new Label(Double.toString(LAT));
                                 Carometerv10.speedValue = new Label(Double.toString(Carometerv10.Current_Speed));
                                 Carometerv10.longValue = new Label(Double.toString(LNG));
                                 if(Carometerv10.Current_Speed > 30.0)
                                       Carometerv10.alarmStatus = new Label("ON");
                                 else
                                     Carometerv10.alarmStatus = new Label("OFF");
                                }
                                else{
                                    disconnect();
                                }
                            });
                }
            }catch (IOException e){
                System.err.println(e.getMessage());
            }
        }
    }
}