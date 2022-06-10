package gui;

import Controller.UserController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JoinGui extends JFrame {
    public JoinGui() {

        setTitle("객패개패 채팅프로그램");
        JPanel title = new JPanel();
        title.setBackground(Color.gray);
        // 1. 컴포넌트들을 만들어 보자.
        JLabel joinL =
                new JLabel("회원가입", JLabel.CENTER);
        joinL.setBackground(Color.gray);

        joinL.setFont(new Font("휴먼모음", Font.BOLD, 30));
        title.add(joinL);
        JButton checkId=new JButton("아이디 중복확인");

        JButton join = new JButton("회원가입");
        JButton cancel = new JButton("취소");

        JTextField id = new JTextField(10);
        JPasswordField pwd = new JPasswordField(10);
        JTextField name = new JTextField(10);
        JTextField lolRank = new JTextField(10);
        JTextField lolNickName =new JTextField(10);
        JTextField battleRank=new JTextField(10);
        JTextField battleNickName=new JTextField(10);
        JTextField fifaRank=new JTextField(10);
        JTextField fifaNickName=new JTextField(10);
        JTextField starRank=new JTextField(10);
        JTextField starNickName=new JTextField(10);
        JTextField overwatchRank=new JTextField(10);
        JTextField overwatchNickName=new JTextField(10);



        // form panel
        JPanel idPanel = new JPanel();
        idPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        idPanel.add(new JLabel("아이디 : "));
        idPanel.add(id);
        idPanel.setBackground(Color.gray);

        JPanel pwdPanel = new JPanel();
        pwdPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pwdPanel.add(new JLabel("비밀번호 : "));
        pwdPanel.add(pwd);
        pwdPanel.setBackground(Color.gray);

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        namePanel.add(new JLabel("이    름 : "));
        namePanel.add(name);
        namePanel.setBackground(Color.gray);

        JPanel lolNickNamePanel = new JPanel();
        lolNickNamePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        lolNickNamePanel.add(new JLabel("롤 닉네임 : "));
        lolNickNamePanel.add(lolNickName);
        lolNickNamePanel.setBackground(Color.gray);

        JPanel lolRankPanel = new JPanel();
        lolRankPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        lolRankPanel.add(new JLabel("롤 랭크 : "));
        lolRankPanel.add(lolRank);
        lolRankPanel.setBackground(Color.gray);

        JPanel battleNickNamePanel = new JPanel();
        battleNickNamePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        battleNickNamePanel.add(new JLabel("배틀그라운드 닉네임 : "));
        battleNickNamePanel.add(battleNickName);
        battleNickNamePanel.setBackground(Color.gray);

        JPanel battleRankPanel = new JPanel();
        battleRankPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        battleRankPanel.add(new JLabel("배틀그라운드 랭크 : "));
        battleRankPanel.add(battleRank);
        battleRankPanel.setBackground(Color.gray);

        JPanel fifaNickNamePanel = new JPanel();
        fifaNickNamePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        fifaNickNamePanel.add(new JLabel("피파3 닉네임 : "));
        fifaNickNamePanel.add(fifaNickName);
        fifaNickNamePanel.setBackground(Color.gray);

        JPanel fifaRankPanel = new JPanel();
        fifaRankPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        fifaRankPanel.add(new JLabel("피파3 랭크 : "));
        fifaRankPanel.add(fifaRank);
        fifaRankPanel.setBackground(Color.gray);

        JPanel starNickNamePanel = new JPanel();
        starNickNamePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        starNickNamePanel.add(new JLabel("스타크래프트 닉네임 : "));
        starNickNamePanel.add(starNickName);
        starNickNamePanel.setBackground(Color.gray);


        JPanel starRankPanel = new JPanel();
        starRankPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        starRankPanel.add(new JLabel("스타크래프트 랭크 : "));
        starRankPanel.add(starRank);
        starRankPanel.setBackground(Color.gray);

        JPanel overNickNamePanel = new JPanel();
        overNickNamePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        overNickNamePanel.add(new JLabel("오버워치 닉네임 : "));
        overNickNamePanel.add(overwatchNickName);
        overNickNamePanel.setBackground(Color.gray);


        JPanel overwatchRankPanel = new JPanel();
        overwatchRankPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        overwatchRankPanel.add(new JLabel("오버워치 랭크 : "));
        overwatchRankPanel.add(overwatchRank);
        overwatchRankPanel.setBackground(Color.gray);


        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(14, 1));
        formPanel.setBackground(Color.gray);
        formPanel.add(idPanel);
        formPanel.add(pwdPanel);
        formPanel.add(namePanel);
        formPanel.add(lolNickNamePanel);
        formPanel.add(lolRankPanel);
        formPanel.add(battleNickNamePanel);
        formPanel.add(battleRankPanel);
        formPanel.add(fifaNickNamePanel);
        formPanel.add(fifaRankPanel);
        formPanel.add(starNickNamePanel);
        formPanel.add(starRankPanel);
        formPanel.add(overNickNamePanel);
        formPanel.add(overwatchRankPanel);

        // radio + form panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout());
        contentPanel.add(formPanel);
        contentPanel.setBackground(Color.gray);
        // button panel
        JPanel panel = new JPanel();
        panel.add(checkId);
        panel.add(join);
        panel.add(cancel);
        panel.setBackground(Color.gray);


        add(title, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);


        setBounds(200, 200, 300, 550);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
        UserController userController =new UserController();

        checkId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String myId = id.getText();
                if(userController.checkId(myId)){
                    JOptionPane.showMessageDialog
                            (null, "아이디가 중복되었습니다.");

                }
                else {
                    JOptionPane.showMessageDialog
                            (null,"아이디를 사용가능 합니다!");
                }

            }
        });
        // 이벤트 처리
        join.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String myId = id.getText();
                String myPwd = new String(pwd.getPassword());
                String myName = name.getText();
                String myLolNickName = lolNickName.getText();
                String myLolRank=lolRank.getText();
                String myBattleNickname = battleNickName.getText();
                String myBattleRank = battleRank.getText();
                String myfifaNickName = fifaNickName.getText();
                String myfifaRank = fifaRank.getText();
                String mystarNickName = starNickName.getText();
                String mystarRank = starRank.getText();
                String myoverwatchNickName = overwatchNickName.getText();
                String myoverwatchRank = overwatchRank.getText();
                User user =new User(myId,myPwd,myName,myLolNickName,myLolRank,
                        myBattleNickname,myBattleRank,myfifaNickName,myfifaRank,mystarNickName,mystarRank,myoverwatchNickName,myoverwatchRank);
                if(id.getText().isEmpty()){
                    JOptionPane.showMessageDialog
                            (null, "아이디를 입력해주세요.");
                }else if(new String(pwd.getPassword()).isEmpty()){
                    JOptionPane.showMessageDialog
                            (null, "비밀번호를 입력해주세요.");
                }else if(name.getText().isEmpty()){
                    JOptionPane.showMessageDialog
                            (null, "이름을 입력해주세요.");
                }
                else if(userController.checkId(myId)){
                    JOptionPane.showMessageDialog
                            (null, "아이디가 중복되었습니다.");

                }else{
                    userController.UserJoin(user);
                    JOptionPane.showMessageDialog
                            (null, "회원가입 완료 되었습니다.");
                    new LoginGui();
                    dispose();
                }



            }
        });


        // 취소 버튼을 클릭했을 때 이벤트 처리
        cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                new LoginGui();
                dispose();

            }
        });
    }
}