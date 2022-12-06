/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
import com.dlsc.gmapsfx.GoogleMapView;
import com.dlsc.gmapsfx.MapComponentInitializedListener;
import com.dlsc.gmapsfx.javascript.object.GoogleMap;
import com.dlsc.gmapsfx.javascript.object.LatLong;
import com.dlsc.gmapsfx.javascript.object.MapOptions;
import com.dlsc.gmapsfx.javascript.object.MapTypeIdEnum;
import com.dlsc.gmapsfx.javascript.object.Marker;
import com.dlsc.gmapsfx.javascript.object.MarkerOptions;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author moazo
 */
public class Carometerv10 extends Application implements  MapComponentInitializedListener {
    
    public double LAT =30.070824;
    public double LNG= 31.021621;
    private double[] lat = {31.2001,29.9285,30.0658};
    private double[] lng = {29.9187,30.9188,31.0250};
    int x = 0;
    private Scene scene;
    private GoogleMapView mapView;
    
    private GoogleMap map;
    private float Max_speed=40;
    private float currentSpeed=40;
    private MarkerOptions markerOptions1;
    private Marker myPosition;

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
      launch(args);
  }

    @Override
    public void init() throws Exception {
        super.init();
        mapView = new GoogleMapView();
        
    }

  
  @Override
  public void start(Stage primaryStage) {
      primaryStage.setTitle("Google Map");
      mapView.addMapInitializedListener(new com.dlsc.gmapsfx.MapComponentInitializedListener(){
          @Override
          public void mapInitialized() {
              LatLong ITILocation = new LatLong(30.070824, 31.021621);
              LatLong cairoLocation = new LatLong(30.0444, 31.2357);
        
        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();
        
        mapOptions.center(cairoLocation)
                .mapType(MapTypeIdEnum.HYBRID)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(11);
                   
        map = mapView.createMap(mapOptions);

        //Add markers to the map
        markerOptions1 = new MarkerOptions();
        markerOptions1.position(ITILocation);
        
        myPosition = new Marker(markerOptions1);
        
        map.addMarker(myPosition );
        new Thread(new Runnable(){
                  @Override
                  public void run() {
                      while(true){
                          try {
                              //check
                              Thread.sleep(2000);
                              LAT = lat[x];
                              LNG = lng[x];
                              Platform.runLater(new Runnable(){
                                  @Override
                                  public void run() {
                                      map.removeMarker(myPosition);
                                      markerOptions1 = new MarkerOptions();
                                      markerOptions1.position(new LatLong(LAT, LNG));
                                      myPosition = new Marker(markerOptions1);
                                      map.addMarker(myPosition );
                                      x++;
                                      if(x == lat.length)
                                          x = 0;
                                  }
                              });
                          } catch (InterruptedException ex) {
                              Logger.getLogger(Carometerv10.class.getName()).log(Level.SEVERE, null, ex);
                          }
                          
                      }
                  }
              }).start();
        
          }
      });
      BorderPane bp = new BorderPane();
      bp.setCenter(mapView);
//      FlowPane fp  = new FlowPane(); 
      Label latitude = new Label("  Latitude: ");
      latitude.setFont(new Font("Arial", 25));
      Label latValue = new Label("10.6476832");
      latValue.setFont(new Font("Arial", 25));
      HBox jj = new HBox(20);
      HBox j1 = new HBox();
      HBox j2 = new HBox();
      Label speed = new Label("Speed: ");
      speed.setFont(new Font("Arial", 25));
      Label speedValue = new Label("30 KM/h ");
      speedValue.setFont(new Font("Arial", 25));
      HBox j3 = new HBox();
      Label alarm = new Label("Alarm:");
      alarm.setFont(new Font("Arial", 25));
      Label alarmStatus = new Label("ON");
      alarmStatus.setFont(new Font("Arial", 25));
      
      HBox j4 = new HBox();
      Label longitude = new Label("longitude: ");
      longitude.setFont(new Font("Arial", 25));
      Label longValue = new Label("10.6476832");
      longValue.setFont(new Font("Arial", 25));
     
      j1.getChildren().add(latitude);
      j1.getChildren().add(latValue);
      j2.getChildren().add(longitude);
      j2.getChildren().add(longValue);
      jj.getChildren().add(j1);
      jj.getChildren().add(j2);
      j3.getChildren().add(speed);
      j3.getChildren().add(speedValue);
      j4.getChildren().add(alarm);
      j4.getChildren().add(alarmStatus);
      jj.getChildren().add(j3);
      jj.getChildren().add(j4);
      bp.setBottom(jj);
      
      
      scene = new Scene(bp, 900, 600);
    
      primaryStage.setScene(scene);
      primaryStage.show();
  }

    @Override
    public void mapInitialized() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
