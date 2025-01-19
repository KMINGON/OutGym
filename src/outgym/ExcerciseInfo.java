
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package outgym;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

/**
 *
 * @author LG
 */
public class ExcerciseInfo {
    Root root;
    public class Datum {

        public String 경도; //경도
        public String 담당부서;
        public String 담당부서연락처;
        public String 데이터기준일자;
        public int 수량;
        public String 시설명;   //시설명
        public String 운동기구종류; //기구종류
        public String 위도; //위도
        public String 주소;
    }

    public class Root {

        public int currentCount;    //항목 개수
        public ArrayList<Datum> data;
        public int matchCount;
        public int page;
        public int perPage;
        public int totalCount;
    }

    public ExcerciseInfo() {
        String result;
        try {
            String page = "1";
            String numOfRow = "1000";
            URL url = new URL("https://api.odcloud.kr/api/15103061/v1/uddi:70423390-fa9a-4c68-8720-977a05638f14?page=" + page + "&perPage=" + numOfRow + "&serviceKey=1zv7N7jLvLrFiAWqBNdYCZ3Yayqj8jhyJAzrSJpvZoIfXwDNnrOi8G1ue8ZQfd1%2BSaNemvkyF34TGd5hUVNxoA%3D%3D");
            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            result = bf.readLine();
            Gson gson = new Gson();
            this.root = gson.fromJson(result, Root.class);
        } catch (JsonIOException | JsonSyntaxException | IOException e) {
        }
        //createExerciseJson();
    }

    @Deprecated
    private void createExerciseJson() { //파싱한 제이슨 파일로 저장
        try ( FileWriter writer = new FileWriter("./exerciseInfo.json")) {
            Gson gson = new Gson();
            gson.toJson(this, writer);
            writer.flush();
        } catch (IOException ex1) {
            ex1.getStackTrace();
        }
    }
}
