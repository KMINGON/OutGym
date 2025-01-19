/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package outgym;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author LG
 */
public class ReviewViewer extends JFrame {

    JTextField titleText;
    JTextArea mainText;
    RoundedButton deleteButt, editButt;
    ReviewInfo item;
    SubFrame sub;

    public ReviewViewer(ReviewInfo item, SubFrame sub) {
        super("Review");
        this.item = item;
        this.sub = sub;
        this.setLayout(null);
        titleText = new JTextField(20);
        mainText = new JTextArea(10, 30);
        deleteButt = new RoundedButton("삭제");
        editButt = new RoundedButton("수정");

        editButt.addActionListener(new editListener());
        deleteButt.addActionListener(new removeListener());

        titleText.setEditable(false);
        mainText.setEditable(false);
        titleText.setText(item.title);
        mainText.setText(item.mainText);

        titleText.setBounds(50, 20, 250, 30);
        mainText.setBounds(50, 55, 250, 300);
        deleteButt.setBounds(70, 365, 100, 30);
        editButt.setBounds(180, 365, 100, 30);
        
        add(titleText);
        add(mainText);
        if (LoginFrame.userName.equals(item.user) || LoginFrame.userName.equals("admin")) {
            add(deleteButt);
        }
        if (LoginFrame.userName.equals(item.user) || LoginFrame.userName.equals("admin")) {
            add(editButt);
        }

        Color b=new Color(255, 255, 255);
        getContentPane().setBackground(b);
        this.setLocation(974, 0);
        setSize(350, 500);
        setVisible(true);
    }

    class removeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            item.removed = true;
            sub.setReviewList();
            FacilityInfo.saveInfo();
            dispose();
        }

    }

    class editListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (editButt.getText().equals("수정")) {
                titleText.setEditable(true);
                mainText.setEditable(true);
                editButt.setText("저장");
            } else {
                editButt.setText("수정");
                item.title = titleText.getText();
                item.mainText = mainText.getText();
                titleText.setEditable(false);
                mainText.setEditable(false);
                sub.setReviewList();
                FacilityInfo.saveInfo();
            }

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
