package gui;

import Controller.UserController;
import model.GetUserRes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UserInfoGui extends JFrame {
    public UserInfoGui(int userIdx) {
            UserController userController =new UserController();

            setTitle("객패개패 채팅프로그램");

            // 1. 컴포넌트들을 만들어 보자.
            JLabel title = new JLabel("내 정보 수정", JLabel.CENTER);


            title.setFont(new Font("휴먼모음", Font.BOLD, 30));


            JButton join = new JButton("수정 완료");
            JButton cancel = new JButton("취소");

            ArrayList<GetUserRes> arr;
            arr= userController.getFriendInfo(userIdx);

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
            for(GetUserRes getUserRes : arr){

                name=new JTextField(getUserRes.getUserName());
                setTitle(getUserRes.getUserName()+"님 정보수정");
                title.setText(getUserRes.getUserName()+"님 정보");
               if(getUserRes.getLolRank().isEmpty()){
                   lolRank= new JTextField(10);
               }
               else{
                   lolRank=new JTextField(getUserRes.getLolRank());
               }
                if(getUserRes.getLolNickName().isEmpty()){
                    lolNickName=new JTextField(10);
                }
                else{
                    lolNickName=new JTextField(getUserRes.getLolNickName());
                }
                if(getUserRes.getBattleRank().isEmpty()){
                    battleRank=new JTextField(10);
                }
                else{
                    battleRank=new JTextField(getUserRes.getBattleRank());
                }
                if(getUserRes.getBattleNickName().isEmpty()){
                    battleNickName=new JTextField(10);
                }
                else{
                    battleNickName=new JTextField(getUserRes.getBattleNickName());
                }
                if(getUserRes.getFifaRank().isEmpty()){
                    fifaRank=new JTextField(10);
                }
                else{
                    fifaRank=new JTextField(getUserRes.getFifaRank());
                }
                if(getUserRes.getFifaNickName().isEmpty()){
                    fifaNickName=new JTextField(10);
                }
                else{
                    fifaNickName=new JTextField(getUserRes.getFifaNickName());
                }
                if(getUserRes.getStarRank().isEmpty()){
                    starRank=new JTextField(10);
                }
                else{
                    starRank=new JTextField(getUserRes.getStarRank());
                }
                if(getUserRes.getStarNickName().isEmpty()){
                    starNickName=new JTextField(10);
                }
                else{
                    starNickName=new JTextField(getUserRes.getStarNickName());
                }

                if(getUserRes.getOverwatchRank().isEmpty()){
                    overwatchRank=new JTextField(10);
                }
                else{
                    overwatchRank=new JTextField(getUserRes.getOverwatchRank());
                }

                if(getUserRes.getOverNickName().isEmpty()){
                    overwatchNickName=new JTextField(10);
                }
                else{
                    overwatchNickName=new JTextField(getUserRes.getOverNickName());
                }


            }


            // form panel

            JPanel namePanel = new JPanel();
            namePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            namePanel.add(new JLabel("이    름 : "));
            namePanel.add(name);


            JPanel lolNickNamePanel = new JPanel();
            lolNickNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            lolNickNamePanel.add(new JLabel("롤 닉네임 : "));
            lolNickNamePanel.add(lolNickName);

            JPanel lolRankPanel = new JPanel();
            lolRankPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            lolRankPanel.add(new JLabel("롤 랭크 : "));
            lolRankPanel.add(lolRank);

            JPanel battleNickNamePanel = new JPanel();
            battleNickNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            battleNickNamePanel.add(new JLabel("배틀그라운드 닉네임 : "));
            battleNickNamePanel.add(battleNickName);

            JPanel battleRankPanel = new JPanel();
            battleRankPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            battleRankPanel.add(new JLabel("배틀그라운드 랭크 : "));
            battleRankPanel.add(battleRank);

            JPanel fifaNickNamePanel = new JPanel();
            fifaNickNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            fifaNickNamePanel.add(new JLabel("피파3 닉네임 : "));
            fifaNickNamePanel.add(fifaNickName);

            JPanel fifaRankPanel = new JPanel();
            fifaRankPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            fifaRankPanel.add(new JLabel("피파3 랭크 : "));
            fifaRankPanel.add(fifaRank);

            JPanel starNickNamePanel = new JPanel();
            starNickNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            starNickNamePanel.add(new JLabel("스타크래프트 닉네임 : "));
            starNickNamePanel.add(starNickName);



            JPanel starRankPanel = new JPanel();
            starRankPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            starRankPanel.add(new JLabel("스타크래프트 랭크 : "));
            starRankPanel.add(starRank);

            JPanel overNickNamePanel = new JPanel();
            overNickNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            overNickNamePanel.add(new JLabel("오버워치 닉네임 : "));
            overNickNamePanel.add(overwatchNickName);


            JPanel overwatchRankPanel = new JPanel();
            overwatchRankPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            overwatchRankPanel.add(new JLabel("오버워치 랭크 : "));
            overwatchRankPanel.add(overwatchRank);


            JPanel formPanel = new JPanel();
            formPanel.setLayout(new GridLayout(11, 1));
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

            // button panel
            JPanel panel = new JPanel();
            panel.add(join);
            panel.add(cancel);


            add(title, BorderLayout.NORTH);
            add(contentPanel, BorderLayout.CENTER);
            add(panel, BorderLayout.SOUTH);


            setBounds(200, 200, 300, 500);



            setVisible(true);



            // 이벤트 처리
        JTextField finalStarNickName = starNickName;
        JTextField finalFifaRank = fifaRank;
        JTextField finalFifaNickName = fifaNickName;
        JTextField finalBattleRank = battleRank;
        JTextField finalBattleNickName = battleNickName;
        JTextField finalLolRank = lolRank;
        JTextField finalLolNickName = lolNickName;
        JTextField finalStarRank = starRank;
        JTextField finalOverwatchNickName = overwatchNickName;
        JTextField finalOverwatchRank = overwatchRank;
        JTextField finalName = name;
        join.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    String myId= userController.getUserId(userIdx);
                    String myName = finalName.getText();
                    String myLolNickName = finalLolNickName.getText();
                    String myLolRank= finalLolRank.getText();
                    String myBattleNickname = finalBattleNickName.getText();
                    String myBattleRank = finalBattleRank.getText();
                    String myfifaNickName = finalFifaNickName.getText();
                    String myfifaRank = finalFifaRank.getText();
                    String mystarNickName = finalStarNickName.getText();
                    String mystarRank = finalStarRank.getText();
                    String myoverwatchNickName = finalOverwatchNickName.getText();
                    String myoverwatchRank = finalOverwatchRank.getText();
                    GetUserRes getUserRes=new GetUserRes(myId,myName,myLolNickName,myLolRank,
                            myBattleNickname,myBattleRank,myfifaNickName,myfifaRank,mystarNickName,mystarRank,myoverwatchNickName,myoverwatchRank);
                    if(finalName.getText().isEmpty()) {
                        JOptionPane.showMessageDialog
                                (null, "이름을 입력해 주세요.");
                    }
                   else{
                        userController.updateUserInfo(getUserRes,userIdx);
                        JOptionPane.showMessageDialog
                                (null, "정보수정 완료 되었습니다.");
                        dispose();
                    }



                }
            });


            // 취소 버튼을 클릭했을 때 이벤트 처리
            cancel.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();

                }
            });
        }
    }
