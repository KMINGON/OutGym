/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package outgym;

import com.google.gson.Gson;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.*;
import static java.lang.Math.sqrt;
import java.util.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LG
 */
public class MainFrame extends JFrame {

    JPanel listPanel, checkPanel;
    JLabel facilitySelectLabel;
    JCheckBox favoritesCheck;
    RoundJTextField addressText;
    RoundedButton getAddressButt, facilitySelectButt;
    JComboBox<String> facilitySelectCBox;
    JTable facilityList;
    JScrollPane facilityScroll;
    public DefaultTableModel facilityListModel;
    public static FacilityInfo facilityInfo;
    JLabel imageLabel;
    String facility[] = {"야외운동기구", "공공 화장실", "공원"};
    String facilityListHd[] = {"시설 이름", "주소", "거리(km)"};
    String addressX, addressY;
    ArrayList<Integer> exerciseIndexList, restroomIndexList, parkIndexList, favoritesList;
    static public int facType;
    boolean checked = false;
    Font font = new Font("Dialog", Font.BOLD, 15);
    Color b = new Color(255, 255, 255);

    public MainFrame() {
        super("OutGym");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lb = new JLabel();
        this.setLayout(null);

        listPanel = new JPanel();
        checkPanel = new JPanel();

       
        facilitySelectLabel = new JLabel("시설 선택");
        facilitySelectLabel.setFont(font);
        facilitySelectCBox = new JComboBox<>(facility);
        facilitySelectCBox.setFont(font);
        facilitySelectCBox.addActionListener(new comboListener());
        addressText = new RoundJTextField(20);
        getAddressButt = new RoundedButton("검색");
        getAddressButt.setFont(font);
        getAddressButt.addActionListener(new NaverMap(this));

        
        
        imageLabel = new JLabel();
        imageLabel.setSize(new Dimension(300, 300));

        facilityListModel = new DefaultTableModel(null, facilityListHd) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        facilityList = new JTable(facilityListModel);
        facilityList.getTableHeader().setReorderingAllowed(false);
        facilityList.getTableHeader().setResizingAllowed(false);
        facilityList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        facilityList.addMouseListener(new NaverMap(this));

        facilityList.getTableHeader().setFont(font);
        facilityList.getTableHeader().setOpaque(false);
        facilityList.getTableHeader().setBackground(Color.DARK_GRAY);
        facilityList.getTableHeader().setForeground(Color.LIGHT_GRAY);
        
        
        
        facilityScroll = new JScrollPane(facilityList);
        facilityScroll.setPreferredSize(new Dimension(500, 120));
        favoritesCheck = new JCheckBox("즐겨찾기");
        favoritesCheck.setBackground(b);
        favoritesCheck.setFont(font);
        favoritesCheck.addItemListener(new checkListener());
        facilitySelectButt = new RoundedButton("상세 정보");
        facilitySelectButt.addActionListener(new selectListener());
        facilitySelectButt.setFont(font);

        checkPanel.add(favoritesCheck);
        listPanel.add(facilityScroll);

        facilitySelectLabel.setBounds(65, 15, 70, 25);
        facilitySelectCBox.setBounds(140, 15, 120, 25);
        addressText.setBounds(275, 15, 200, 25);
        getAddressButt.setBounds(490, 15, 75, 25);
        imageLabel.setBounds(65, 50, 500, 500);
        checkPanel.setBounds(65, 555, 100, 30);
        listPanel.setBounds(65, 590, 500, 120);
        facilitySelectButt.setBounds(415, 720, 150, 20);
        lb.setBounds(-55, 740, 700, 130);
        
        
        
        imageLabel.setBackground(b);
        checkPanel.setBackground(b);
        listPanel.setBackground(b);

        add(imageLabel);

        add(facilitySelectLabel);
        add(facilitySelectCBox);
        add(addressText);
        
        add(getAddressButt);
        add(checkPanel);
        add(listPanel);
        add(facilitySelectButt);

        add(lb);

        NaverMap n = new NaverMap(this);
        n.mapSetting("엄광로 176");
        facilityInfo = new FacilityInfo();
        try {
            Gson gson = new Gson();
            Reader reader = new FileReader("./facilityInfo.json");
            facilityInfo = gson.fromJson(reader, FacilityInfo.class);
        } catch (FileNotFoundException ex) {
            facilityInfo = new FacilityInfo();
            ExcerciseInfo excerciseInfo = new ExcerciseInfo();
            RestroomInfo restroomInfo = new RestroomInfo();
            ParkInfo parkInfo = new ParkInfo();
            FacilityInfo.setInfo(facilityInfo, excerciseInfo, restroomInfo, parkInfo);
        }
        
        //테이블 테스트
        getContentPane().setBackground(b);
        setSize(650, 800);
        setVisible(true);
    }

    public void sortFacility() {
        //운동기구
        Map<Double, Integer> map = new HashMap<>();
        ArrayList<Double> distanceList = new ArrayList<>();
        for (int i = 0; i < facilityInfo.excersiseItems.item.size(); i++) {
            Item item = facilityInfo.excersiseItems.item.get(i);
            Double distanceX = Double.parseDouble(addressY) - Double.parseDouble(item.lat);
            Double distanceY = Double.parseDouble(addressX) - Double.parseDouble(item.lng);
            distanceY *= 111.0;
            distanceX *= 89.0;
            Double distance = distanceX * distanceX + distanceY * distanceY;
            distance = sqrt(distance);
            map.put(distance, i);
            distanceList.add(distance);
            i++;

        }
        distanceList.sort((s1, s2) -> s1.compareTo(s2));

        exerciseIndexList = new ArrayList<>();
        for (Double d : distanceList) {
            exerciseIndexList.add(map.get(d));
            facilityInfo.excersiseItems.item.get(map.get(d)).distance = d;
        }
        //화장실
        map = new HashMap<>();
        distanceList = new ArrayList<>();
        for (int i = 0; i < facilityInfo.restroomItems.item.size(); i++) {
            Item item = facilityInfo.restroomItems.item.get(i);
            Double distanceX = Double.parseDouble(addressY) - Double.parseDouble(item.lat);
            Double distanceY = Double.parseDouble(addressX) - Double.parseDouble(item.lng);
            distanceY *= 111.0;
            distanceX *= 89.0;
            Double distance = distanceX * distanceX + distanceY * distanceY;
            distance = sqrt(distance);
            map.put(distance, i);
            distanceList.add(distance);
            i++;

        }
        distanceList.sort((s1, s2) -> s1.compareTo(s2));

        restroomIndexList = new ArrayList<>();
        for (Double d : distanceList) {
            restroomIndexList.add(map.get(d));
            facilityInfo.restroomItems.item.get(map.get(d)).distance = d;
        }
        //공원
        map = new HashMap<>();
        distanceList = new ArrayList<>();
        for (int i = 0; i < facilityInfo.parkItems.item.size(); i++) {
            Item item = facilityInfo.parkItems.item.get(i);
            Double distanceX = Double.parseDouble(addressY) - Double.parseDouble(item.lat);
            Double distanceY = Double.parseDouble(addressX) - Double.parseDouble(item.lng);
            distanceY *= 111.0;
            distanceX *= 89.0;

            Double distance = distanceX * distanceX + distanceY * distanceY;
            distance = sqrt(distance);
            map.put(distance, i);
            distanceList.add(distance);
            i++;
        }
        distanceList.sort((s1, s2) -> s1.compareTo(s2));

        parkIndexList = new ArrayList<>();
        for (Double d : distanceList) {
            parkIndexList.add(map.get(d));
            facilityInfo.parkItems.item.get(map.get(d)).distance = d;
        }
    }

    class selectListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Items items = new Items();
            int index = 0;
            facType = facilitySelectCBox.getSelectedIndex();
            switch (facilitySelectCBox.getSelectedIndex()) {
                case 0 -> {
                    items = facilityInfo.excersiseItems;
                    if (checked) {
                        index = favoritesList.get(facilityList.getSelectedRow());
                    } else {
                        index = exerciseIndexList.get(facilityList.getSelectedRow());
                    }

                }
                case 1 -> {
                    items = facilityInfo.restroomItems;
                    if (checked) {
                        index = favoritesList.get(facilityList.getSelectedRow());
                    } else {
                        index = restroomIndexList.get(facilityList.getSelectedRow());
                    }
                }
                case 2 -> {
                    items = facilityInfo.parkItems;
                    if (checked) {
                        index = favoritesList.get(facilityList.getSelectedRow());
                    } else {
                        index = parkIndexList.get(facilityList.getSelectedRow());
                    }
                }
            }
            new SubFrame(items, index);
        }
    }

    public void showRestroomInfo() {
        favoritesList = new ArrayList();
        for (Integer i : restroomIndexList) {
            String str[] = {facilityInfo.restroomItems.item.get(i).name, facilityInfo.restroomItems.item.get(i).address, facilityInfo.restroomItems.item.get(i).distance.toString()};
            if (facilityInfo.restroomItems.item.get(i).removed == false) {
                if (checked && facilityInfo.restroomItems.item.get(i).favorite) {
                    facilityListModel.addRow(str);
                    favoritesList.add(i);
                }
                if (!checked) {
                    facilityListModel.addRow(str);
                }
            }
        }
    }

    public void showExerciseInfo() {

        favoritesList = new ArrayList();
        for (Integer i : exerciseIndexList) {
            String str[] = {facilityInfo.excersiseItems.item.get(i).name, facilityInfo.excersiseItems.item.get(i).address, facilityInfo.excersiseItems.item.get(i).distance.toString()};
            if (facilityInfo.excersiseItems.item.get(i).removed == false) {
                if (checked && facilityInfo.excersiseItems.item.get(i).favorite) {
                    facilityListModel.addRow(str);
                    favoritesList.add(i);
                }
                if (!checked) {
                    facilityListModel.addRow(str);
                }
            }
        }
    }

    public void showParkInfo() {
        favoritesList = new ArrayList();
        for (Integer i : parkIndexList) {
            String str[] = {facilityInfo.parkItems.item.get(i).name, facilityInfo.parkItems.item.get(i).address, facilityInfo.parkItems.item.get(i).distance.toString()};
            if (facilityInfo.parkItems.item.get(i).removed == false) {
                if (checked && facilityInfo.parkItems.item.get(i).favorite) {
                    facilityListModel.addRow(str);
                    favoritesList.add(i);
                }
                if (!checked) {
                    facilityListModel.addRow(str);
                }
            }
        }
    }

    class comboListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!addressText.getText().isEmpty()) {
                facilityListModel.setNumRows(0);
                switch (facilitySelectCBox.getSelectedIndex()) {
                    case 0 ->
                        showExerciseInfo();
                    case 1 ->
                        showRestroomInfo();
                    case 2 ->
                        showParkInfo();
                }
            }
        }

    }

    class checkListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            checked = e.getStateChange() == ItemEvent.SELECTED;
            facilityListModel.setNumRows(0);
            switch (facilitySelectCBox.getSelectedIndex()) {
                case 0 ->
                    showExerciseInfo();
                case 1 ->
                    showRestroomInfo();
                case 2 ->
                    showParkInfo();
            }
        }

    }

    public class RoundedButton extends JButton {

        public RoundedButton() {
            super();
            decorate();
        }

        public RoundedButton(String text) {
            super(text);
            decorate();
        }

        public RoundedButton(Action action) {
            super(action);
            decorate();
        }

        public RoundedButton(Icon icon) {
            super(icon);
            decorate();
        }

        public RoundedButton(String text, Icon icon) {
            super(text, icon);
            decorate();
        }

        protected void decorate() {
            setBorderPainted(false);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {

            Color c = new Color(244, 245, 245); //배경색 결정
            Color o = new Color(29,28,26); //글자색 결정
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (getModel().isArmed()) {
                graphics.setColor(c.darker());
            } else if (getModel().isRollover()) {
                graphics.setColor(c.brighter());
            } else {
                graphics.setColor(c);
            }
            graphics.fillRoundRect(0, 0, width, height, 10, 10);
            FontMetrics fontMetrics = graphics.getFontMetrics();
            Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds();
            int textX = (width - stringBounds.width) / 2;
            int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent();
            graphics.setColor(o);
            graphics.setFont(getFont());
            graphics.drawString(getText(), textX, textY);
            graphics.dispose();
            super.paintComponent(g);
        }
    }

    public class RoundJTextField extends JTextField {

        private Shape shape;

        public RoundJTextField(int size) {
            super(size);
            setOpaque(false); // As suggested by @AVD in comment.
        }

        protected void paintComponent(Graphics g) {
            g.setColor(getBackground());
            g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            super.paintComponent(g);
        }

        protected void paintBorder(Graphics g) {
            g.setColor(getForeground());
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        }

        public boolean contains(int x, int y) {
            if (shape == null || !shape.getBounds().equals(getBounds())) {
                shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            }
            return shape.contains(x, y);
        }
    }
}
