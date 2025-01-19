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
import java.awt.Shape;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pasic
 */
public class ChangingInformation extends JFrame {

    JLabel Title, Etc2, Etc3;
    RoundJTextField nameChange, machineText;
    RoundedButton OkButt;
    Item item1;
    JComboBox<String> menuComb;
    public DefaultTableModel MenuModel;
    
    Font font2=new Font("고딕체",Font.BOLD,15);

    public ChangingInformation(Item item) {
        super("ChangingInformation");
        this.setLayout(null);
        this.item1 = item;
        OkButt = new RoundedButton("수정하기");
        Title = new JLabel("              정보 수정");
        Title.setFont(font2);
        Etc2 = new JLabel("시설 이름 변경");
        Etc2.setFont(font2);
        Etc3 = new JLabel("기구 이름 변경");
        Etc3.setFont(font2);
        nameChange = new RoundJTextField(20);
        machineText = new RoundJTextField(20);
        OkButt.addActionListener(new ChangeListener1());

        menuComb = new JComboBox<>(item.machineName.toArray(new String[item.machineName.size()]));

        menuComb.setBounds(65, 150, 200, 30);
        machineText.setBounds(65, 185, 200, 30);
        OkButt.setBounds(110, 230, 95, 30);
        nameChange.setBounds(65, 90, 200, 30);
        Title.setBounds(-30, 0, 250, 30);
        Etc2.setBounds(75, 55, 250, 30);
        Etc3.setBounds(75, 120, 250, 30);
        if(MainFrame.facType == 0) add(machineText);
        if(MainFrame.facType == 0) add(menuComb);
        add(OkButt);
        add(nameChange);
        add(Title);
        add(Etc2);
        if(MainFrame.facType == 0) add(Etc3);
        Color b=new Color(255, 255, 255);
        this.setLocation(637, 0);
        getContentPane().setBackground(b);
        setSize(350, 400);
        setVisible(true);
    }

    class ChangeListener1 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(!nameChange.getText().isEmpty()) item1.name = nameChange.getText();
            if(MainFrame.facType == 0 && !machineText.getText().isEmpty()) item1.machineName.set(menuComb.getSelectedIndex(), machineText.getText());
            FacilityInfo.saveInfo();
            showMessageDialog(null, "정보를 수정했습니다.");
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
}
