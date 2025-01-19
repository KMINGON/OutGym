/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package outgym;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author LG
 */
public class FacilityInfo {

    public Items excersiseItems;
    public Items parkItems;
    public Items restroomItems;

    public FacilityInfo() {
        excersiseItems = new Items();
        parkItems = new Items();
        restroomItems = new Items();
    }

    public static void saveInfo() {

        try ( FileWriter writer = new FileWriter("./facilityInfo.json")) {
            Gson gson = new Gson();
            gson.toJson(MainFrame.facilityInfo, writer);
            writer.flush();
            
        } catch (IOException ex1) {
            ex1.getStackTrace();
        }
    }

    public static void setInfo(FacilityInfo facInfo, ExcerciseInfo exerInfo, RestroomInfo restInfo, ParkInfo parkInfo) {

        int exerCount = exerInfo.root.currentCount;
        int restCount = facInfo.restroomItems.totalCount = restInfo.root.getPubToiletList.totalCount;
        int parkCount = facInfo.parkItems.totalCount = parkInfo.root.getParkList.totalCount;
        String name = "";
        Item item = new Item();
        int cnt = 0;
        for (int i = 0; i < exerCount; i++) {
            item.machineName.add(exerInfo.root.data.get(i).운동기구종류);
            if (!name.equals(exerInfo.root.data.get(i).시설명)) {
                name = exerInfo.root.data.get(i).시설명;
                item.address = exerInfo.root.data.get(i).주소;
                item.name = exerInfo.root.data.get(i).시설명;
                item.lat = exerInfo.root.data.get(i).위도;
                item.lng = exerInfo.root.data.get(i).경도;
                facInfo.excersiseItems.item.add(item);
                item = new Item();
                cnt++;
            }
        }
        facInfo.excersiseItems.totalCount = cnt;
        for (int i = 0; i < parkCount; i++) {
            item.address = parkInfo.root.getParkList.item.get(i).address;
            item.name = parkInfo.root.getParkList.item.get(i).parkName;
            item.lat = parkInfo.root.getParkList.item.get(i).lat;
            item.lng = parkInfo.root.getParkList.item.get(i).lng;
            facInfo.parkItems.item.add(item);
            item = new Item();
        }
        for (int i = 0; i < restCount; i++) {
            item.address = restInfo.root.getPubToiletList.item.get(i).address;
            item.name = restInfo.root.getPubToiletList.item.get(i).toiletName;
            item.lat = restInfo.root.getPubToiletList.item.get(i).lat;
            item.lng = restInfo.root.getPubToiletList.item.get(i).lng;
            facInfo.restroomItems.item.add(item);
            item = new Item();
        }
        try ( FileWriter writer = new FileWriter("./facilityInfo.json")) {
            Gson gson = new Gson();
            gson.toJson(facInfo, writer);
            writer.flush();
        } catch (IOException ex1) {
            ex1.getStackTrace();
        }
    }
}
