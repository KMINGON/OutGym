/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package outgym;

import com.google.gson.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author LG
 */
public class MemberManagement {

    private List<Map<String, Map<String, String>>> id;
    
    static public String userName;
    
    
    public List<Map<String, Map<String, String>>> getId() {
        return id;
    }

    public void setId(List<Map<String, Map<String, String>>> id) {
        this.id = id;
    }

    public String macthPw(String idText) {   //아이디와 매치되는 비밀번호 리턴
        Map<String, String> temp = this.id.get(0).get(idText);
        return temp.get("pw");
    }

    public boolean containsId(String idKey) {    //아이디가 JSON 파일에 존재하는지 확인
        for (Map<String, Map<String, String>> idList : this.id) {
            if (idList.containsKey(idKey)) {
                return true;
            }
        }
        return false;
    }
    
    public String getName(String idText){
        return id.get(0).get(idText).get("name");
    }

    public void pushUserInfo(String name, String id, String pw) {    //새로운 id, pw, name 정보 추가
        Map<String, String> temp = new HashMap<>();
        temp.put("pw", pw);
        temp.put("name", name);
        this.id.get(0).put(id, temp);
    }

    public static MemberManagement setMemberInfo() { //JSON 파일을 열어서 MomberManagement 클래스로 저장 및 파일 생성
        MemberManagement memberInfo;
        try {
            Gson gson = new Gson();
            Reader reader = new FileReader("./member.json");
            memberInfo = gson.fromJson(reader, MemberManagement.class);

        } catch (FileNotFoundException ex) {    //JSON 파일이 없을경우 JSON 생성
            Gson gson = new Gson();
            JsonObject member = new JsonObject();
            JsonArray id = new JsonArray();
            JsonObject obj = new JsonObject();
            JsonObject idObj = new JsonObject();

            obj.addProperty("pw", "admin");
            obj.addProperty("name", "admin");
            idObj.add("admin", obj);
            id.add(idObj);
            member.add("id", id);
            memberInfo = gson.fromJson(member, MemberManagement.class);

            try ( FileWriter writer = new FileWriter("./member.json")) {
                gson.toJson(member, writer);
                writer.flush();
            } catch (IOException ex1) {
                ex1.getStackTrace();
            }
        }
        return memberInfo;
    }
}
