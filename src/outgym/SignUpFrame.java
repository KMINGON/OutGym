/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package outgym;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import com.google.gson.*;
import java.awt.geom.RoundRectangle2D;
import java.io.*;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.text.*;

/**
 *
 * @author LG
 */
public class SignUpFrame extends JFrame {

    JLabel idLabel, pwLabel, pwCheckLabel, pwSameLabel, nameLabel;
    JTextField idText, nameText;
    JPasswordField pwText, pwCheckText;
    RoundedButton signUpButt, doubleCheckButt, findPwButt;
    JPanel idPanel, pwPanel, pwCheckPanel, signUpPanel, namePanel;
    MemberManagement memberInfo;
    boolean useId = false, checkPw = false;
    Font font = new Font("Aharoni 굵게", Font.BOLD, 15);

    public SignUpFrame() {  //컴포넌트 부착
        super("SIGN UP");
        setLayout(new FlowLayout(FlowLayout.LEFT));

        idPanel = new JPanel();
        pwPanel = new JPanel();
        signUpPanel = new JPanel();
        pwCheckPanel = new JPanel();
        namePanel = new JPanel();
        nameLabel = new JLabel("이름          ");
        nameLabel.setFont(font);
        nameText = new JTextField(10);

        idLabel = new JLabel("ID              ");
        idLabel.setFont(font);
        idText = new JTextField(20);
        idText.addKeyListener(new IdTextListener());
        doubleCheckButt = new RoundedButton("ID 확인");
        doubleCheckButt.setFont(font);
        doubleCheckButt.addActionListener(new DoubleCheckListener());

        pwLabel = new JLabel("PW            ");
        pwLabel.setFont(font);
        pwText = new JPasswordField(20);
        pwCheckLabel = new JLabel("PW 확인   ");
        pwCheckLabel.setFont(font);
        pwCheckText = new JPasswordField(20);
        pwSameLabel = new JLabel("불일치");
        pwSameLabel.setFont(font);
        pwCheckText.addKeyListener(new pwCheckListener());
        pwText.addKeyListener(new pwCheckListener());

        findPwButt = new RoundedButton("PW 찾기");
        findPwButt.setFont(font);
        signUpButt = new RoundedButton("회원가입");
        signUpButt.setFont(font);
        signUpButt.addActionListener(new SignUpListener());

        namePanel.add(nameLabel);
        namePanel.add(nameText);

        idPanel.add(idLabel);
        idPanel.add(idText);
        idPanel.add(doubleCheckButt);

        pwPanel.add(pwLabel);
        pwPanel.add(pwText);

        pwCheckPanel.add(pwCheckLabel);
        pwCheckPanel.add(pwCheckText);
        pwCheckPanel.add(pwSameLabel);

        signUpPanel.add(signUpButt);
        signUpPanel.add(findPwButt);

        Color b = new Color(255, 255, 255);
        namePanel.setBackground(b);
        idPanel.setBackground(b);
        pwPanel.setBackground(b);
        pwCheckPanel.setBackground(b);
        signUpPanel.setBackground(b);
        add(namePanel);
        add(idPanel);
        add(pwPanel);
        add(pwCheckPanel);
        add(signUpPanel);

        getContentPane().setBackground(b);
        this.setLocation(427, 0);
        setSize(470, 250);
        setVisible(true);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        memberInfo = MemberManagement.setMemberInfo();
    }

    class SignUpListener implements ActionListener {    //회원가입 버튼 클릭 시 이벤트

        @Override
        public void actionPerformed(ActionEvent e) {
            if (useId && checkPw && !nameText.getText().isEmpty()) {
                String pwString = new String(pwText.getPassword());
                memberInfo.pushUserInfo(nameText.getText(), idText.getText(), pwString);

                Gson gson = new Gson();
                try ( FileWriter writer = new FileWriter("./member.json")) {
                    gson.toJson(memberInfo, writer);
                    writer.flush();
                } catch (IOException ex1) {
                    ex1.getStackTrace();
                }
                showMessageDialog(null, "Successed");
                dispose();
            } else {
                showMessageDialog(null, "Impossible");
            }
        }
    }

    class IdTextListener implements KeyListener {   //id 텍스트 수정 시 이벤트

        @Override
        public void keyTyped(KeyEvent e) {
            setTextField();
        }

        @Override
        public void keyPressed(KeyEvent e) {
            setTextField();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            setTextField();
        }

        void setTextField() {
            useId = false;
            doubleCheckButt.setEnabled(true);
        }
    }

    class DoubleCheckListener implements ActionListener {   //중복확인 버튼 클릭 시 이벤트

        @Override
        public void actionPerformed(ActionEvent e) {
            if (memberInfo.containsId(idText.getText()) || idText.getText().isEmpty()) {
                showMessageDialog(null, "ID를 입력해주세요");
            } else {
                showMessageDialog(null, "사용 가능한 ID입니다.");
                useId = true;
                doubleCheckButt.setEnabled(false);
            }
        }

    }

    class pwCheckListener implements KeyListener {  //비밀번호 텍스트 수정 시 이벤트

        @Override
        public void keyTyped(KeyEvent e) {
            ComparePw();
        }

        @Override
        public void keyPressed(KeyEvent e) {
            ComparePw();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            ComparePw();
        }

        void ComparePw() {  //두 비밀번호가 같은지 확인
            String pwStr = new String(pwText.getPassword());
            if (Arrays.equals(pwText.getPassword(), pwCheckText.getPassword()) && !pwStr.isEmpty()) {
                pwSameLabel.setText("일치");
                checkPw = true;
            } else {
                pwSameLabel.setText("불일치");
                checkPw = false;
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
