/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package outgym;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author pasic
 */
public class Retouch extends JFrame {

    JLabel Title, Etc1, Etc2, Etc3;
    RoundedButton DeleteButt, RetouchButt;
    Items items;
    int index;
    SubFrame subFrame;
    Font font=new Font("고딕체",Font.BOLD,15);
    Font font2=new Font("고딕체",Font.BOLD,13);
    Font font3=new Font("고딕체",Font.BOLD,16);

    public Retouch(Items items, int index, SubFrame subFrame) {
        super("Retouch");
        this.setLayout(null);
        this.subFrame = subFrame;
        this.items = items;
        this.index = index;
        DeleteButt = new RoundedButton("정보 삭제");
        DeleteButt.setFont(font);
        RetouchButt = new RoundedButton("정보 수정");
        RetouchButt.setFont(font);
        Title = new JLabel("                                    정보 관리");
        Title.setFont(font); 
        Etc1 = new JLabel("알고있는 정보와 무엇이 다른가요? ");
        Etc1.setFont(font2);
        Etc2 = new JLabel("없는 장소이거나,수정이필요한정보가 있다면 ");
        Etc2.setFont(font2);
        Etc3 = new JLabel("아래 항목을 선택해서 제보해 주세요.");
        Etc3.setFont(font2);
        DeleteButt.setBounds(65, 220, 200, 30);
        RetouchButt.setBounds(65, 310, 200, 30);
        Title.setBounds(-15, 40, 250, 30);
        Etc1.setBounds(55, 95, 300, 30);
        Etc2.setBounds(30, 130, 280, 30);
        Etc3.setBounds(55, 160, 250, 30);

        add(DeleteButt);
        add(RetouchButt);
        add(Title);
        add(Etc1);
        add(Etc2);
        add(Etc3);

        DeleteButt.addActionListener(new Delete());
        RetouchButt.addActionListener(new Retouch1());
         Color b=new Color(255, 255, 255);
        getContentPane().setBackground(b);
        this.setLocation(974, 0);
        setSize(350, 500);
        setVisible(true);
    }

    

    class Delete implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            items.item.get(index).removed = true;
            showMessageDialog(null, "장소가 삭제 되었습니다.");
            FacilityInfo.saveInfo();
            subFrame.dispose();
            dispose();
        }
    }

    class Retouch1 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new ChangingInformation(items.item.get(index));
            
            subFrame.dispose();
            dispose();
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
