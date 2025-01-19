/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package outgym;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.Date;
import javax.swing.*;

/**
 *
 * @author LG
 */
public class ReviewFrame extends JFrame{
    RoundJTextField titleText;
    JTextArea mainText;
    RoundedButton saveButt;
    Item item;
    SubFrame subFrame;
    JLabel label,label2,label3;
    Font font=new Font("고딕체",Font.BOLD,14);
    
    public ReviewFrame(Item item, SubFrame subFrame) {
        super("Review");
        this.setLayout(null);
        this.item = item;
        this.subFrame = subFrame;
        
        label=new JLabel("후기작성");
        label.setFont(font);
        label2=new JLabel("제목");
        label2.setFont(font);
        label3=new JLabel("내용 작성");
        label3.setFont(font);
        titleText = new RoundJTextField(15);
        mainText = new JTextArea(10, 30);
        saveButt = new RoundedButton("저장");
        saveButt.setFont(font);
        saveButt.addActionListener(new SaveListener());
        
//        add(label);
//        add(titleText);
//        add(mainText);
//        add(saveButt);

        label.setBounds(0, -30,100,100);
        label2.setBounds(40,30,100,30);
        titleText.setBounds(40, 70,230, 30);
        label3.setBounds(40, 110, 100,30);
        mainText.setBounds(40, 150, 230, 200);
        saveButt.setBounds(110, 360, 100, 50);
        
        add(label);
        add(label2);
        add(titleText);
        add(label3);
        add(mainText);
        add(saveButt);
        
        this.setLocation(974, 0);
        setSize(330, 480);
        setVisible(true);
    }
    class SaveListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            ReviewInfo review = new ReviewInfo();
            review.title = titleText.getText();
            review.mainText = mainText.getText();
            review.user = LoginFrame.userName;
            review.date = new Date();
            item.review.add(review);
            String[] str = {review.user, review.title, review.date.toString()};
            subFrame.reviewModel.addRow(str);
            FacilityInfo.saveInfo();
            subFrame.setReviewList();
            System.out.println(subFrame.reviewIndex.size());
            dispose();
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
            g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
            super.paintComponent(g);
       }
       protected void paintBorder(Graphics g) {
            g.setColor(getForeground());
            g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
       }
       public boolean contains(int x, int y) {
            if (shape == null || !shape.getBounds().equals(getBounds())) {
                shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
            }
            return shape.contains(x, y);
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
