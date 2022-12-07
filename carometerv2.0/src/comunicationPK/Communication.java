/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicationPK;
import static Alarm.Alarm_Test.Play_Alarm;
import carometer_main.Carometerv10;
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



/**
 *
 * @author moazo
 */
public class Communication {
    SerialPort serialPort;
    InputStream in;
    Thread serialReadThread;
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
                        serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
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
                                // _v or i_lat_lng_speed_
                                //Carometerv10.LAT
                                
                                
                            });
                }
            }catch (IOException e){
                System.err.println(e.getMessage());
            }
        }
    }
}


