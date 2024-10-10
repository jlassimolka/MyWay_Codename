/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ToastBar;
import com.codename1.googlemaps.MapContainer;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.maps.Coord;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Style;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author molka
 */
public class MapForm2 extends Form {

    Form formMap = new Form();
    MapContainer cnt = null;
    Form previous;

    public MapForm2() {
        this.previous = previous;

        try {
            cnt = new MapContainer("AIzaSyD5fI9G6HJRyIxMngqOz6M24sVVGDHxUrs");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

        Button btnMoveCamera = new Button("Mon Pays");
        btnMoveCamera.addActionListener(e -> {
            cnt.setCameraPosition(new Coord(36.8189700, 10.1657900));
        });
        Style s = new Style();
        s.setFgColor(0xff0000);
        s.setBgTransparency(0);
        FontImage markerImg = FontImage.createMaterial(FontImage.MATERIAL_PLACE, s, Display.getInstance().convertToPixels(3));

        cnt.addTapListener(e -> {

            cnt.clearMapLayers();
            cnt.addMarker(
                    EncodedImage.createFromImage(markerImg, false),
                    cnt.getCoordAtPosition(e.getX(), e.getY()),
                    "" + cnt.getCameraPosition().toString(),
                    "",
                    e3 -> {
                        ToastBar.showMessage("You clicked " + cnt.getName(), FontImage.MATERIAL_PLACE);
                    }
            );
            ConnectionRequest r = new ConnectionRequest();
            r.setPost(false);
            r.setUrl("http://maps.google.com/maps/api/geocode/json?latlng=" + cnt.getCameraPosition().getLatitude() + "," + cnt.getCameraPosition().getLongitude() + "&oe=utf8&sensor=false");
            NetworkManager.getInstance().addToQueueAndWait(r);

            JSONParser jsonp = new JSONParser();
            try {
                java.util.Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(r.getResponseData()).toCharArray()));
                System.out.println("roooooot:" + tasks.get("results"));
                List<java.util.Map<String, Object>> list1 = (List<java.util.Map<String, Object>>) tasks.get("results");
//                              java.util.Map<String, Object> list = (java.util.Map<String, Object>) list1.get(0);

                //                             List<java.util.Map<String, Object>> listf = (List<java.util.Map<String, Object>>) list.get("address_components");
//String ch="";
                //                       for (java.util.Map<String, Object> obj : listf) {
                //             ch=ch+obj.get("long_name").toString();
                //                   }
                //
                // b.setAdresse(ch);
                  // https://github.com/Chadi7781/CodeNameOne-Formation-Youtube
                  //https://www.youtube.com/watch?v=OtrmwmdLIrM
               
                
            } catch (IOException ex) {
            }

        });
        Container root = new Container();
        this.setLayout(new BorderLayout());
        this.addComponent(BorderLayout.CENTER, cnt);
        this.addComponent(BorderLayout.SOUTH, btnMoveCamera);
        this.show();
        //f.getToolbar().addCommandToRightBar("back", null, (ev)->{ new AjoutReclamationForm(f).show()});

    }

}
