package outgym;

import javax.swing.*;

public class GetDirectionsFrame extends JFrame {
	 JLabel Name, Add, Type, Photo, Why;
	    JButton GetDirectionsButt;
	    JTextField TypeT, PhotoT, WhyT;
	    
	    public GetDirectionsFrame(){
	    super("GetDirections");
	    this.setLayout(null);
	        
	    Name = new JLabel("고장/제보하기");
	    Add = new JLabel("기구에 대해서 제보할 사항이 있나요?");
	    Type = new JLabel("기구 종류");
	    Photo = new JLabel("사진 첨부");
	    Why = new JLabel("제보 내용");
	    
	    GetDirectionsButt = new JButton("제보 하기");
	    
	    TypeT = new JTextField(20);
	    PhotoT = new JTextField(20);
	    WhyT = new JTextField(20);
	    
	    Name.setBounds(5, 10, 340, 20);
	    Add.setBounds(5, 35, 325, 20);
	    Type.setBounds(15, 60, 300, 20);
	    Photo.setBounds(15, 120, 300, 20);
	    Why.setBounds(15, 180, 300, 20);
	    GetDirectionsButt.setBounds(0, 450, 340, 50);
	    TypeT.setBounds(15, 85, 300, 30);
	    PhotoT.setBounds(15, 145, 300, 30);
	    WhyT.setBounds(15, 205, 300, 100);
	    
	    add(Name);
	    add(Add);
	    add(Type);
	    add(Photo);
	    add(Why);
	    add(GetDirectionsButt);
	    add(TypeT);
	    add(PhotoT);
	    add(WhyT);
	    
	    setSize(350, 550);
	    setVisible(true);
	    }
}
