package carmeter;


import com.dlsc.gmapsfx.GoogleMapView;
import com.dlsc.gmapsfx.MapComponentInitializedListener;
import com.dlsc.gmapsfx.javascript.object.GoogleMap;
import com.dlsc.gmapsfx.javascript.object.LatLong;
import com.dlsc.gmapsfx.javascript.object.MapOptions;
import com.dlsc.gmapsfx.javascript.object.MapTypeIdEnum;
import com.dlsc.gmapsfx.javascript.object.Marker;
import com.dlsc.gmapsfx.javascript.object.MarkerOptions;
import com.sun.prism.PhongMaterial.MapType;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


public class JavaFX_GoogleMaps extends Application implements  MapComponentInitializedListener  {

    public double LAT =30.070824;
    public double LNG= 31.021621;
    private double[] lat = {31.2001,29.9285,30.0658};
    private double[] lng = {29.9187,30.9188,31.0250};
    int x = 0;
  private Scene scene;
  private GoogleMapView mapView;
    
    private GoogleMap map;
    private MarkerOptions markerOptions1;
    private Marker myPosition;



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
                              Logger.getLogger(JavaFX_GoogleMaps.class.getName()).log(Level.SEVERE, null, ex);
                          }
                          
                      }
                  }
              }).start();
        
          }
      });
    
      scene = new Scene(mapView, 800, 600);
    
      primaryStage.setScene(scene);
      primaryStage.show();
  }

//  class MyBrowser extends Region{
//    
//      HBox toolbar;
//    
//      WebView webView = new WebView();
//      WebEngine webEngine = webView.getEngine();
//    
//      public MyBrowser(){
//        
//          final URL urlGoogleMaps = getClass().getResource("googlemaps.html");
//          webEngine.load(urlGoogleMaps.toExternalForm());
//
//          getChildren().add(webView);
//        
//      }
//    
//  }

    @Override
    public void mapInitialized() {
        try {
            super.init();
            mapView = new GoogleMapView();
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (Exception ex) {
            Logger.getLogger(JavaFX_GoogleMaps.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
     /**
   * @param args the command line arguments
   */
//  public static void main(String[] args) {
//      launch(args);
//  }
}