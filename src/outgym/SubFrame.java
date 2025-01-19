package outgym;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SubFrame extends JFrame {

    RoundedButton ReviewButt, RatButt, favoritsButt;
    JTable Menu, reviewList;
    JLabel Exercisename, imgLabel, nameLabel, addressLabel;
    JPanel MenuBar, related1;
    public DefaultTableModel MenuModel, reviewModel;
    JScrollPane MenuScroll, reviewScroll;
    String MenuHd[] = {"운동기구 이름"};
    String reviewHd[] = {"작성자", "제목", "작성일"};
    Items items;
    int index;
    ImageIcon bsImg;
    SubFrame subFrame;
    ArrayList<Integer> reviewIndex;
    Font font=new Font("고딕체",Font.BOLD,16);
    Font font2=new Font("고딕체",Font.BOLD,16);

    public SubFrame(Items items, int index) {
        super("SubFrame");
        subFrame = this;
        this.items = items;
        this.index = index;
        this.setLayout(null);
        MenuBar = new JPanel();
        related1 = new JPanel();

        MenuModel = new DefaultTableModel(null, MenuHd) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        nameLabel = new JLabel(items.item.get(index).name);
        nameLabel.setFont(font2);
        addressLabel = new JLabel(items.item.get(index).address);
        addressLabel.setFont(font2);

        Menu = new JTable(MenuModel);
        Menu.getTableHeader().setReorderingAllowed(false);
        Menu.getTableHeader().setResizingAllowed(false);
        Menu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Menu.getTableHeader().setOpaque(false);
        Menu.getTableHeader().setFont(font);
        Menu.getTableHeader().setBackground(Color.DARK_GRAY);
        Menu.getTableHeader().setForeground(Color.LIGHT_GRAY);
        MenuScroll = new JScrollPane(Menu);
        MenuScroll.setPreferredSize(new Dimension(450, 100));

        ReviewButt = new  RoundedButton("후기");
        ReviewButt.setFont(font2);
        RatButt = new  RoundedButton("정보관리");
        RatButt.setFont(font2);
        favoritsButt = new  RoundedButton();
        Exercisename = new JLabel("<html> 관련 운동기구: <br>➥</html> ");
        Exercisename.setFont(font2);
        imgLabel = new JLabel();
        if (items.item.get(index).favorite) {
            favoritsButt.setText("즐겨찾기 해제");
            favoritsButt.setFont(font2);
        } else {
            favoritsButt.setText("즐겨찾기 추가");
            favoritsButt.setFont(font2);
        }

        if (MainFrame.facType == 0) {
            bsImg = new ImageIcon("../OutGym/src/img/운동기구.png");
            imgLabel.setBounds(10, 40, 320, 180);
        }
        else if(MainFrame.facType == 1){
            bsImg = new ImageIcon("../OutGym/src/img/화장실.png");
            imgLabel.setBounds(10, 60, 320, 180);
        }
        else if(MainFrame.facType == 2){
            bsImg = new ImageIcon("../OutGym/src/img/그림5.png");
            imgLabel.setBounds(10, 40, 320, 180);
        }
        imgLabel.setIcon(bsImg);

        reviewModel = new DefaultTableModel(null, reviewHd) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        reviewList = new JTable(reviewModel);
        reviewList.getTableHeader().setReorderingAllowed(false);
        reviewList.getTableHeader().setResizingAllowed(false);
        reviewList.getTableHeader().setOpaque(false);
        reviewList.getTableHeader().setFont(font);
        reviewList.getTableHeader().setBackground(Color.DARK_GRAY);
        reviewList.getTableHeader().setForeground(Color.LIGHT_GRAY);
        reviewList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        reviewList.addMouseListener(new reviewListener());
        reviewScroll = new JScrollPane(reviewList);
        reviewScroll.setPreferredSize(new Dimension(450, 100));

        ReviewButt.addActionListener(new ReviewListen());
        RatButt.addActionListener(new retouch());
        favoritsButt.addActionListener(new favoritesListen());

        MenuBar.add(favoritsButt);
        MenuBar.add(ReviewButt);
        MenuBar.add(RatButt);
        if (MainFrame.facType == 0) {
            related1.add(Exercisename);
        }

        addressLabel.setBounds(10, 0, 200, 20);
        nameLabel.setBounds(10, 20, 200, 20);
        
        MenuBar.setBounds(0, 510, 340, 50);
        related1.setBounds(10, 230, 100, 50);
        MenuScroll.setBounds(120, 230, 150, 100);
        reviewScroll.setBounds(10, 350, 320, 150);
        imgLabel.setHorizontalAlignment(JLabel.CENTER);
        Color b=new Color(255, 255, 255);
        
        add(imgLabel);
        add(addressLabel);
        add(nameLabel);
        
        if (MainFrame.facType == 0) {
            add(MenuScroll);
        }
        setReviewList();
        System.out.println(reviewIndex.size());
        reviewScroll.setBackground(b);
        related1.setBackground(b);
        MenuBar.setBackground(b);
        add(reviewScroll);
        add(related1);
        add(MenuBar);
        showExerciseInfo();
        
        
        this.setLocation(637, 0);
        getContentPane().setBackground(b);
        setSize(350, 600);
        setVisible(true);
    }

    public void setReviewList() {
        reviewIndex = new ArrayList();
        Integer i = 0;
        reviewModel.setRowCount(0);
        for (ReviewInfo review : items.item.get(index).review) {
            if (!review.removed) {
                String[] str = {review.user, review.title, review.date.toString()};
                reviewModel.addRow(str);

                reviewIndex.add(i);
            }

            i++;
        }
    }

    final public void showExerciseInfo() {
        for (String s : items.item.get(index).machineName) {
            String str[] = {s};
            MenuModel.addRow(str);
        }

    }

    class ReviewListen implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new ReviewFrame(items.item.get(index), subFrame);
        }
    }

    class retouch implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new Retouch(items, index, subFrame);
        }
    }

    class reviewListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                new ReviewViewer(items.item.get(index).review.get(reviewIndex.get(reviewList.getSelectedRow())), subFrame);
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

    class favoritesListen implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (items.item.get(index).favorite) {
                items.item.get(index).favorite = false;
                favoritsButt.setText("즐겨찾기 추가");
            } else {

                items.item.get(index).favorite = true;
                favoritsButt.setText("즐겨찾기 해제");
            }
            FacilityInfo.saveInfo();
        }
    }
    public class RoundedButton extends JButton {
      public RoundedButton() { super(); decorate(); } 
      public RoundedButton(String text) { super(text); decorate(); } 
      public RoundedButton(Action action) { super(action); decorate(); } 
      public RoundedButton(Icon icon) { super(icon); decorate(); } 
      public RoundedButton(String text, Icon icon) { super(text, icon); decorate(); } 
      protected void decorate() { setBorderPainted(false); setOpaque(false); }
      @Override 
      protected void paintComponent(Graphics g) {
         Color c=new Color(244, 245, 245); //배경색 결정
         Color o=new Color(29,28,26); //글자색 결정
         int width = getWidth(); 
         int height = getHeight(); 
         Graphics2D graphics = (Graphics2D) g; 
         graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
         if (getModel().isArmed()) { graphics.setColor(c.darker()); } 
         else if (getModel().isRollover()) { graphics.setColor(c.brighter()); } 
         else { graphics.setColor(c); } 
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
}
