package gui;

import Controller.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGui extends JFrame {


    UserController userController =new UserController();
    public LoginGui() {

        setTitle("객패개패 채팅프로그램");

        JPanel title = new JPanel();
        title.setBackground(Color.gray);

        // title 컨테이너에 들어갈 컴포넌트를 만들어 보자.
        JLabel login = new JLabel("로그인 화면");

        // Color color = new Color(5, 0, 153)



        // Font font = new Font("휴먼편지체", Font.BOLD, 25);

        login.setFont(new Font("휴먼모음", Font.BOLD, 30));

        // 컴포넌트를 title 컨테이너에 올려 주자.
        title.add(login);


        JPanel jp1 = new JPanel();
        jp1.setLayout(new GridLayout(3, 2));

        JPanel idPanel =
                new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel jlb1 = new JLabel("아이디 : ", JLabel.CENTER);
        idPanel.add(jlb1);
        idPanel.setBackground(Color.gray);
        JPanel idPanel2 =
                new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField jtf1 = new JTextField(10);
        idPanel2.setBackground(Color.gray);
        idPanel2.add(jtf1);

        jp1.add(idPanel);
        jp1.add(idPanel2);


        JPanel pwdPanel =
                new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pwdPanel.setBackground(Color.gray);
        JLabel jlb2 = new JLabel("비밀번호 : ", JLabel.CENTER);
        JPanel pwdPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pwdPanel2.setBackground(Color.gray);
        JPasswordField jtf2 = new JPasswordField(10);

        pwdPanel.add(jlb2); pwdPanel2.add(jtf2);

        jp1.add(pwdPanel); jp1.add(pwdPanel2);


        JPanel loginPanel =
                new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton jLogin = new JButton("로그인");
        loginPanel.setBackground(Color.gray);


        JPanel joinPanel =
                new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton join = new JButton("회원가입");
        joinPanel.setBackground(Color.gray);
        loginPanel.add(jLogin);
        joinPanel.add(join);

        jp1.add(loginPanel); jp1.add(joinPanel);


        JPanel jp2 = new JPanel();
        jp2.setBackground(Color.gray);
        jp2.setLayout(new FlowLayout());
        jp2.add(jp1);

        setLayout(new BorderLayout());

        add(title, BorderLayout.NORTH);
        add(jp2, BorderLayout.CENTER);

        setBounds(200, 200, 400, 250);

        setResizable(false);  // 화면 크기 고정하는 작업

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);


        // 이벤트 처리
        jLogin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String myId = jtf1.getText();
                String myPwd = new String(jtf2.getPassword());
                Boolean logIn= userController.UserLogin(myId,myPwd);
                if(myId.isEmpty()){
                    JOptionPane.showMessageDialog
                            (null, "아이디를 입력해주세요.");
                }
                else if(myPwd.isEmpty()){
                    JOptionPane.showMessageDialog
                            (null, "비밀번호를 입력해주세요.");
                }


                else if(logIn) {
                    userController.UserUpdateStatus(myId);
                    JOptionPane.showMessageDialog
                            (null, "로그인 성공");
                    String myName= userController.getUserName(myId);
                    int id= userController.getUserPK(myId);
                    new MainGui(myName,myId,id);
                    dispose();
                }
                else if(logIn==false) {
                    JOptionPane.showMessageDialog
                            (null, "아이디와 비밀번호 둘 중 하나가 올바르지 않습니다.");
                }



            }
        });


        join.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                new JoinGui();
                dispose();  // 현재의 frame을 종료시키는 메서드.

            }
        });


    }
}


