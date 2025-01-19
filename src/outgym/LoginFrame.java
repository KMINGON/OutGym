package outgym;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.text.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author LG
 */
public class LoginFrame extends JFrame {

    JLabel idLabel, pwLabel, lb;
    JTextField idText;
    JPasswordField pwText;
    RoundedButton loginButt, signUpButt;
    MemberManagement memberInfo;
    Font font = new Font("Aharoni 굵게", Font.BOLD, 15);
    public static String userName;

    public LoginFrame() {   //컴포넌트 부착
        super("LOGIN");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        this.setLayout(null);

        ImageIcon logo = new ImageIcon("../OutGym/src/img/logo1.png");

        lb = new JLabel();
        lb.setIcon(logo);
        idLabel = new JLabel("ID");
        idLabel.setFont(font);
        pwLabel = new JLabel("PW");
        pwLabel.setFont(font);
        idText = new JTextField(20);
        pwText = new JPasswordField(20);
        loginButt = new RoundedButton("로그인");
        loginButt.setFont(font);
        signUpButt = new RoundedButton("회원가입");
        signUpButt.setFont(font);

        loginButt.addActionListener(new LoginListener());
        signUpButt.addActionListener(new SignUpListener());
        
        lb.setBounds(80, 50, 280, 100);
        idLabel.setBounds(80, 180, 30, 25);
        idText.setBounds(115, 180, 200, 25);
        pwLabel.setBounds(80, 230, 30, 25);
        pwText.setBounds(115, 230, 200, 25);
        loginButt.setBounds(120, 300, 80, 30);
        signUpButt.setBounds(220, 300, 80, 30);
        
        add(lb);
        add(idLabel);
        add(idText);
        add(pwLabel);
        add(pwText);
        add(loginButt);
        add(signUpButt);
        
        
        Color b = new Color(255, 255, 255);
        getContentPane().setBackground(b);
        setSize(440, 500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    class LoginListener implements ActionListener { //로그인 버튼 클릭 시 이벤트

        @Override
        public void actionPerformed(ActionEvent e) {
            memberInfo = MemberManagement.setMemberInfo();

            String pwString = new String(pwText.getPassword());
            if (memberInfo.containsId(idText.getText())) {
                if (memberInfo.macthPw(idText.getText()).equals(pwString)) {    //비밀번호가 일치하는지 확인
                    showMessageDialog(null, "로그인 성공");
                    userName = memberInfo.getName(idText.getText());
                    new MainFrame();
                    
                    dispose();
                } else {
                    showMessageDialog(null, "PW가 일치하지 않습니다.");
                }
            } else {
                showMessageDialog(null, "ID가 일치하지 않습니다.");
            }
        }
    }

    class SignUpListener implements ActionListener {    //회원가입 버튼 클릭 시 이벤트

        @Override
        public void actionPerformed(ActionEvent e) {
            new SignUpFrame();
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

        public char[] getPassword() {
            Document doc = getDocument();
            Segment txt = new Segment();
            try {
                doc.getText(0, doc.getLength(), txt); // use the non-String API
            } catch (BadLocationException e) {
                return null;
            }
            char[] retValue = new char[txt.count];
            System.arraycopy(txt.array, txt.offset, retValue, 0, txt.count);
            return retValue;
        }
    }

}
