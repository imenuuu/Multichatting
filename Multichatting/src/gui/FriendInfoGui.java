package gui;

import Controller.UserController;
import model.GetUserRes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class FriendInfoGui extends JFrame {

    UserController userController = new UserController();
    public FriendInfoGui(int userId) {
        ArrayList<GetUserRes> arr;
        arr= userController.getFriendInfo(userId);
        String userLoginId= userController.getUserId(userId);
        setTitle(userController.getUserName(userLoginId)+"님 정보");

        // 1. 컴포넌트들을 만들어 보자.
        JLabel title =
                new JLabel("유저정보", JLabel.CENTER);


        title.setFont(new Font("휴먼모음", Font.BOLD, 30));


        JButton cancel = new JButton("닫기");

        JPanel userPanel=new JPanel();
        JPanel namePanel = new JPanel();
        JPanel lolNickNamePanel = new JPanel();
        JPanel lolRankPanel = new JPanel();
        JPanel battleNickNamePanel = new JPanel();
        JPanel battleRankPanel = new JPanel();
        JPanel fifaNickNamePanel = new JPanel();
        JPanel fifaRankPanel = new JPanel();
        JPanel starNickNamePanel = new JPanel();
        JPanel starRankPanel = new JPanel();
        JPanel overNickNamePanel = new JPanel();
        JPanel overwatchRankPanel = new JPanel();


        for(GetUserRes getUserRes : arr) {
            setTitle(getUserRes.getUserName()+"님 정보");

            userPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            userPanel.add(new JLabel("유저 아이디 : "+getUserRes.getUserId()));

            namePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            namePanel.add(new JLabel("이    름 : "+getUserRes.getUserName()));



            lolNickNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            lolNickNamePanel.add(new JLabel("롤 닉네임 : "+getUserRes.getLolNickName()));



            lolRankPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            lolRankPanel.add(new JLabel("롤 랭크 : "+getUserRes.getLolRank()));



            battleNickNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            battleNickNamePanel.add(new JLabel("배틀그라운드 닉네임 : "+getUserRes.getBattleNickName()));



            battleRankPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            battleRankPanel.add(new JLabel("배틀그라운드 랭크 : "+getUserRes.getBattleRank()));



            fifaNickNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            fifaNickNamePanel.add(new JLabel("피파3 닉네임 : "+getUserRes.getFifaNickName()));



            fifaRankPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            fifaRankPanel.add(new JLabel("피파3 랭크 : "+getUserRes.getFifaRank()));



            starNickNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            starNickNamePanel.add(new JLabel("스타크래프트 닉네임 : "+getUserRes.getStarNickName()));



            starRankPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            starRankPanel.add(new JLabel("스타크래프트 랭크 : "+getUserRes.getStarRank()));


            overNickNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            overNickNamePanel.add(new JLabel("오버워치 닉네임 : "+getUserRes.getOverNickName()));



            overwatchRankPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            overwatchRankPanel.add(new JLabel("오버워치 랭크 : "+getUserRes.getOverwatchRank()));


        }

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(12, 1));
        formPanel.add(userPanel);
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
        panel.add(cancel);


        add(title, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);


        setBounds(200, 200, 300, 500);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);


        // 이벤트 처리

        cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();

            }
        });

    }




}

