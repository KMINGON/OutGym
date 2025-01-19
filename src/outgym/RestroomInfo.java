/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package outgym;

import com.google.gson.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author LG
 */
public class RestroomInfo {
    Root root;
    public class GetPubToiletList {

        public Header header;
        public ArrayList<Item> item;
        public int numOfRows;
        public int pageNo;
        public int totalCount;  //항목 개수
    }

    public class Header {

        public String code;
        public String message;
    }

    public class Item {

        public String toiletName;   //화장실명
        public Object tel;
        public String address;  //주소
        public String charge;
        public String lat;  //위도
        public String lng;  //경도
    }

    public class Root {

        public GetPubToiletList getPubToiletList;
    }

    public RestroomInfo() {
        String result;
        try {
            String page = "1";
            String numOfRow = "1000";
            URL url = new URL("http://apis.data.go.kr/3330000/HeaundaePubToiletInfoService/getPubToiletList?serviceKey=1zv7N7jLvLrFiAWqBNdYCZ3Yayqj8jhyJAzrSJpvZoIfXwDNnrOi8G1ue8ZQfd1%2BSaNemvkyF34TGd5hUVNxoA%3D%3D&pageNo="+page+"&numOfRows=" + numOfRow + "&resultType=json");
            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            result = bf.readLine();
            Gson gson = new Gson();
            this.root = gson.fromJson(result, RestroomInfo.Root.class);
        } catch (JsonIOException | JsonSyntaxException | IOException e) {
        }
        //createExerciseJson();
    }

    @Deprecated
    private void createExerciseJson() { //파싱한 제이슨 파일로 저장
        try ( FileWriter writer = new FileWriter("./restroomInfo.json")) {
            Gson gson = new Gson();
            gson.toJson(this, writer);
            writer.flush();
        } catch (IOException ex1) {
            ex1.getStackTrace();
        }
    }
}
