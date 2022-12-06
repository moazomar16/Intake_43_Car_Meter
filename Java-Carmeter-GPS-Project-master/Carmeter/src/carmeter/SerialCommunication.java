package carmeter;
//import gnu.io.CommPort;
import java.io.*;
import java.io.BufferedReader;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortException;
import jssc.SerialPortList;
import gnu.io.CommPortIdentifier;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Enumeration;
import jssc.SerialPortEventListener;
import java.util.stream.StreamSupport;
import javax.sound.sampled.Port;

import eu.hansolo.medusa.Gauge;
import gnu.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Label;

import com.fazecast.jSerialComm.*;
import org.omg.CORBA.Any;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;

public class SerialCommunication {
	BufferedReader buf;
//        int flag =0;
        String temp = null;
        String portName;
        Thread thread_serial;
        //SerialPort sp;
        SerialPort port;
        InputStream in;

        public SerialCommunication()
    {
        super();
    }
	public void connect(String portname) throws Exception {
		// now we need to serialport
                String portlist[] = SerialPortList.getPortNames();

//		Enumeration e = CommPortIdentifier.getPortIdentifiers();
//                while (e.hasMoreElements()) {
//                        CommPortIdentifier portIdentifier = (CommPortIdentifier) e.nextElement();
//                        
//                        if ( portIdentifier.isCurrentlyOwned() )
//                        {
//                            System.out.println("Error: Port is currently in use");
//                        }
                        
//                        else{
//                            if (portIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL) {//only serial ports will be handled
//                                  portName = portIdentifier.getName();
//                                    
//                                  System.out.println(portName);//print the serial port name
//                                  

                                    

//                                  
//                                    thread_serial = new Thread(new SerialReader(in));
//                                    thread_serial.start();
//                                    System.out.println("serial thread started");
//                            }
//                        }                          
//			}    
    
	port = new SerialPort(portname);
		
		SerialPort port = new SerialPort(portname);
		
		try {
			port.openPort();
//			
			port.setParams(
//					
					SerialPort.BAUDRATE_115200,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE
					);
			port.addEventListener((SerialPortEvent event)->{
//				
				if(event.isRXCHAR()) {
//					
					try {
						String s = port.readString();
						System.out.print(s);
					} catch (SerialPortException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
			});
//
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};	

       public void disconnect() throws SerialPortException {
         if (thread_serial.isAlive())
         {
            thread_serial.stop();
            port.closePort();
            System.out.println("serial thread terminated");
         }
     }}
//
//    
     class SerialReader implements Runnable 
    {
        InputStream in;
        String temp2;
        BufferedReader buf;
        
        public SerialReader ( InputStream in )
        {
            this.in = in;
        }

        @Override
        public void run ()
        {
            byte[] buffer = new byte[1024];
            int len = -1;
            int flag =0;
            try
            {
                while (( len = this.in.read(buffer)) > -1 )
                {
                    String str =new String(buffer,0,len);
                    
                    if (str.equals("$")){flag =1;continue;}// this is beacuse when handling GGA sentence the mobile sends a newline $ then the GGA sentence
                    if (flag ==1){str="$"+str;flag =0;}
                    Reader inputString = new StringReader(str);
                    buf = new BufferedReader(inputString);
                    System.out.println(str);//new String(buffer,int offset,int length)	
                }
                System.out.println("ByeBye");
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            } 
        }}

