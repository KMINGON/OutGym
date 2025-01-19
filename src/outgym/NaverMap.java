package outgym;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.ImageIcon;
import org.json.*;

public class NaverMap implements ActionListener, MouseListener {

    MainFrame naverMap;

    public NaverMap(MainFrame naverMap) {
        this.naverMap = naverMap;
    }

    @Override
    //Geocording
    public void actionPerformed(ActionEvent e) {
        mapSetting(naverMap.addressText.getText());
    }

    //StaticMap 즉, 지도 이미지를 생성하는 API  / 매개변수로 vo 넘겨줌
    public void mapSetting(String address) {
        String clientId = "idx6p03s3a"; //발급받은 아이디값
        String clientSecret = "54lrPZEsesDLEmIw6IqoW251VWzU3bHAWi5GBmSZ"; //발급받은 키값
        AddressVO vo = null;

        //주소를 입력할때 공백이 들어있는 경우 인코딩 필요
        //공백이 %20이라는 유니코드로 인코딩해야 서버로 잘 넘어감
        try {
            //String address = naverMap.addressText.getText();
            String addr = URLEncoder.encode(address, "UTF-8");
            //공백 인코딩 유니코드 문자처리 UTF-8 형식

            String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + addr;
            //API 요청 주소 + query에 인코딩된 주소입력값 대입

            URL url = new URL(apiURL);
            //URL 객체 생성

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            //url.openConnection -> 요청 URL을 가지고 Naver서버와 연결
            //con -> 연결 정보를 담는 객체

            con.setRequestMethod("GET");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
            //연결정보에 넣어주어야할 정보들.
            //GET방식, API 발급 아이디, 발급 키값 set

            int responseCode = con.getResponseCode(); //연결상태를 get하는 함수
            //연결상태 200 -> 정상적 연결
            //연결상태 나머지 오류메세지(경우에 따라 다름. ex) 404 Error)
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                //getInputStream() -> 바이트스트림 정보 받아옴 / UTF-8로 한글깨짐 방지
                //new InputStreamReader() -> 문자단위 스트림으로 변환
                //new BufferedReader() -> 라인 단위로 정보를 읽어오기 위해 변환
                //br에 완성된 스트림 대입
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                //에러메세지 처리 문구
            }

            String inputLine;
            StringBuffer response = new StringBuffer();
            //StringBuffer 객체 생성

            while ((inputLine = br.readLine()) != null) {
                //br.readLine으로 한줄씩읽어와서 inputLine 스트링변수에 대입
                response.append(inputLine);
                //스트링버퍼 response객체에 한줄씩 읽어온 string append (요청받은 데이터가 NULL일때까지)
                //response에 모든 JSON형태가 저장됨.
            }
            br.close();
            //GSON은 서버에서 바로 값을 가져오기에 부적합.
            //JSON.simple 사용
            //JSONParser jpr = new JSONParser();
            //JSONObject jarr = (JSONObject)jpr.parse(response.toString());
            //JSONArray arr = (JSONArray)jarr.get("addresses");

            JSONTokener tokener = new JSONTokener(response.toString());
            //JSONTokener() -> 받아져있는 JSON 문자열을 메모리에 로딩
            JSONObject object = new JSONObject(tokener);
            //JSONObject() -> JSONTokner 객체를  JSONObject 객체로 변환

            //System.out.println(object);
            //콘솔 출력용
            JSONArray arr = object.getJSONArray("addresses");
            //JSONObject 객체에서 키값 "addresses" 인 JSONArray 꺼내서 arr에 대입

            for (int i = 0; i < arr.length(); i++) {
                //배열 길이 만큼 반복
                //반복문을 쓰는이유 -> 배열구조이며, 한 주소에 두개 이상의 주소 있는 경우 (거의 없음)
                JSONObject temp = (JSONObject) arr.get(i);
                //JSONArray의 arr에서 하나씩 꺼내옴
                //temp에 JSONObject로 캐스팅해서 삽입

                vo = new AddressVO();
                vo.setRoadAddress((String) temp.get("roadAddress"));
                vo.setJibunAddress((String) temp.get("jibunAddress"));
                vo.setX((String) temp.get("x"));
                vo.setY((String) temp.get("y"));
                //System.out.println(vo);
            }

            map_service(vo); //Static Map 함수 호출

        } catch (Exception err) {
            System.out.println(err);
        }
    }

    public void map_service(AddressVO vo) {
        String URL_STATICMAP = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?";
        //네이버 서버는 API요청이 들어오면 raster라는 함수 호출
        //center -> 위도 경도
        //level -> 확대 레벨
        String clientId = "idx6p03s3a";
        String clientSecret = "54lrPZEsesDLEmIw6IqoW251VWzU3bHAWi5GBmSZ";

        try {

            //마커에 삽입할 pos 인코딩, 위도와 경도 사이에는 띄어쓰기가 들어감.(그냥 공백이면 잘리고, 꼭 인코딩 필요)
            String pos = URLEncoder.encode(vo.getX() + " " + vo.getY(), "UTF-8");
            //요청 API 주소 만들기
            URL_STATICMAP += "center=" + vo.getX() + "," + vo.getY();
            //위도 경도 설정
            URL_STATICMAP += "&level=16&w=500&h=500";
            //사진 크기 설정
            URL_STATICMAP += "&markers=type:t|size:mid|pos:" + pos + "|label:" + URLEncoder.encode(vo.getRoadAddress(), "UTF-8");
            //마커 옵션 설정
            //마커는 최대 20개
            //여러가지 옵션 적용 가능 raster 함수 api 요청 예제 참고

            //연결세팅
            URL url = new URL(URL_STATICMAP);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

            int responseCode = con.getResponseCode();
            BufferedReader br;

            // 정상호출인 경우.
            if (responseCode == 200) {
                //위의 Geocoding의 JSON 형식과는 다르게 이미지 .jpg 또는 .png를 받아옴
                //따라서 InputStream 선언
                InputStream is = con.getInputStream();

                int read = 0;
                //이미지 버퍼 역할
                byte[] bytes = new byte[1024];

                // 날짜값 가져옴
                String tempName = Long.valueOf(new Date().getTime()).toString();

                // 날짜로 이미지파일이름 설정
                File file = new File("./test/" + tempName + ".jpg");
                file.createNewFile();
                // 이미지 생성
                //출력스트림
                OutputStream out = new FileOutputStream(file);

                //받아온 이미지 정보
                //버퍼의 크기만큼 읽음
                while ((read = is.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                    // 읽어들인 바이트수만큼 bytes에 씀
                }
                is.close();

                //GUI는 걍 복붙해옴
                ImageIcon img = new ImageIcon("./test/" + file.getName());
                naverMap.imageLabel.setIcon(img);
                naverMap.addressX = vo.getX();
                naverMap.addressY = vo.getY();
                naverMap.sortFacility();

                naverMap.facilityListModel.setNumRows(0);
                switch (naverMap.facilitySelectCBox.getSelectedIndex()) {
                    case 0 ->
                        naverMap.showExerciseInfo();
                    case 1 ->
                        naverMap.showRestroomInfo();
                    case 2 ->
                        naverMap.showParkInfo();
                }

            } else { //에러코드 출력 (에러코드도 JSON형식으로 날라옴)
                System.out.println(responseCode);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            Item item = new Item();
            switch (naverMap.facilitySelectCBox.getSelectedIndex()) {
                case 0 ->
                    item = naverMap.facilityInfo.excersiseItems.item.get(naverMap.exerciseIndexList.get(naverMap.facilityList.getSelectedRow()));
                case 1 ->
                    item = naverMap.facilityInfo.restroomItems.item.get(naverMap.restroomIndexList.get(naverMap.facilityList.getSelectedRow()));
                case 2 ->
                    item = naverMap.facilityInfo.parkItems.item.get(naverMap.parkIndexList.get(naverMap.facilityList.getSelectedRow()));
            }
            mapSetting(item.address);
            naverMap.facilityList.setRowSelectionInterval(0, 0);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
