/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package outgym;

import java.util.ArrayList;


/**
 *
 * @author LG
 */
class Item {

    public String name;
    public String address;
    public String lat;  //위도
    public String lng;  //경도
    public ArrayList<String> machineName;
    
    public boolean removed;
    public boolean favorite;
    
    public ArrayList<ReviewInfo> review;
    
    transient Double distance;
    
    public Item() {
        machineName = new ArrayList<>();
        review = new ArrayList();
    }
    
}
