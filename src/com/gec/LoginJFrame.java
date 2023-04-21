package com.gec;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
 
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.omg.CORBA.portable.Delegate;

import javafx.event.Event;
 
 
 
//import com.jiemian.ValidCode;
 
public class LoginJFrame extends JFrame  implements ActionListener {
	JLabel lb_backgroud;
	JLabel jLabel,jLabel2,jLabel3;
	JTextField jt_usename,jt_code;
	JPasswordField jPasswordField;
	JPanel jPanel,jPanel2,jPanel3;
	JButton Login,regist;
    public static String path = System.getProperty("user.dir") + "/src/images/";
	Icon login = new ImageIcon(path+"login.png");
	Icon password = new ImageIcon(path+"4.png");

	private LoginSystem loginSystem;
	

	private ValidCode vcode = new ValidCode();
	public LoginJFrame() {
		// TODO Auto-generated constructor stub

		Image logo = Toolkit.getDefaultToolkit().getImage(path+"5.png");
		setIconImage(logo);
		TrayIcon icon = new TrayIcon(logo);
		icon.setImageAutoSize(true);
		SystemTray systemTray = SystemTray.getSystemTray();
		try {
			systemTray.add(icon);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		loginSystem = new LoginSystem();
		
		setBackgroudImage();  
		
		jLabel = new JLabel();
		jLabel.setIcon(login);
		jLabel2 = new JLabel();
		jLabel2.setIcon(password);
		jLabel3 = new JLabel("验证码:");
		
		jt_usename = new JTextField();
		jPasswordField = new JPasswordField();
		jt_code = new JTextField();
		
		jLabel.setBounds(80, 90, 60, 40);
		jLabel2.setBounds(80, 140, 60, 40);
		jLabel3.setBounds(80, 190, 60, 40);
		
		jt_usename.setBounds(150, 90, 150, 40);
		jPasswordField.setBounds(150, 140, 150, 40);
		jt_code.setBounds(150, 190, 150, 40);
		vcode.setBounds(310, 190, 100, 40);
		
		Login = new JButton("登录");
		Login.setBounds(120, 250, 80, 40);
		setJButton(Login);
		regist = new JButton("注册");		
		regist.setBounds(250, 250, 80, 40);
		setJButton(regist);
		Login.addActionListener(this);
		
		
		
		
		
		this.setLayout(null);
		this.add(jLabel);
		this.add(jLabel2);
		this.add(jLabel3);
		
		this.add(jt_usename);
		this.add(jPasswordField);
		this.add(jt_code);
		this.add(vcode);
		this.add(Login);
		this.add(regist);
		
		this.setTitle("登录");
		this.setSize(450,350);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
	}
	private void setBackgroudImage() {
		// TODO Auto-generated method stub
		 ((JPanel) this.getContentPane()).setOpaque(false);  
	        ImageIcon img = new ImageIcon(path+"3.jpg"); // 添加图片  
	        lb_backgroud = new JLabel(img);  
	        this.getLayeredPane().add(lb_backgroud, new Integer(Integer.MIN_VALUE));  
	        lb_backgroud.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());  
	}
	
	/*
	 * 设置按钮风格：透明 
	 */
	private void setJButton(JButton btn) {  
		btn.setBackground(new Color(102, 0, 204));// 紫色 
		btn.setFont(new Font("Dialog", Font.BOLD, 24));  
		btn.setOpaque(false);  
		btn.setBorder(BorderFactory.createEmptyBorder());  
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand() == "登录") {
			if(jt_code.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "请输入验证码!","提示消息",JOptionPane.WARNING_MESSAGE);
			}else {
				if(!isValidCodeRight()) {
					JOptionPane.showMessageDialog(null, "验证码错误,请重新输入!","提示消息",JOptionPane.WARNING_MESSAGE);
					clear();
				}else if(isValidCodeRight()) {
					if(loginSystem.LoginByAccountPassword(jt_usename.getText(), jPasswordField.getPassword().toString())) {
						//TODO:将登录成功的Token保存
						loginSystem.LoginSuccess("token");
						JOptionPane.showMessageDialog(null, "登录成功!","提示消息",JOptionPane.WARNING_MESSAGE);
						
						MyFrame gameStart = new MyFrame();
						clear();
						Close();
					}else if(jt_usename.getText().isEmpty()&&jPasswordField.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "请输入用户名和密码!","提示消息",JOptionPane.WARNING_MESSAGE);
						clear();
					}else if(jt_usename.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "请输入用户名!","提示消息",JOptionPane.WARNING_MESSAGE);
						clear();
					}else if(jPasswordField.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "请输入密码!","提示消息",JOptionPane.WARNING_MESSAGE);
						clear();
					}
				}
			}
			
			}
			
	}  
	private void Close(){
		this.setVisible(false);
	}
	
	private void clear() {
		// TODO Auto-generated method stub
		jt_usename.setText("");
		jPasswordField.setText("");
		jt_code.setText("");
	}

	//验证码的确认
	public boolean isValidCodeRight() {
		if(jt_code == null) {
			return false;
		}else if(vcode == null) {
			return true;
		}
		String code = vcode.getCode();
		code =  code.toLowerCase();
		String Incode = jt_code.getText();
		Incode = Incode.toLowerCase();
		if(code .equals(Incode)) {
			return true;
		}else 
			return false;
	}
	public Boolean IsLogin(){
		if(loginSystem.token.isEmpty())
			return true;
		else
			return false;
	}
}
