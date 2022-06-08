package gui;

import javax.swing.*;
import java.awt.*;


public class ChatGui extends JFrame{

    //채팅방

    public JTextField sendTF;

    JLabel la_msg;



    public JTextArea ta;

    JScrollPane sp_ta,sp_list;



    public JList<String> li_inwon;
    public JButton bt_voteList;
    public JButton bt_addVote;
    public JButton bt_exit;



    JPanel p;
    public int chatRoomId;
    ImageIcon icon;

    public ChatGui(int id) {
        setTitle("객패개패팀 채팅프로그램");
        icon = new ImageIcon("img/main.png"); //이미지 불러오기

        sendTF = new JTextField(15);

        la_msg = new JLabel("Message");




        ta = new JTextArea();

        ta.setLineWrap(true);//TextArea 가로길이를 벗어나는 text발생시 자동 줄바꿈

        li_inwon = new JList<String>();



        sp_ta = new JScrollPane(ta);

        sp_list = new JScrollPane(li_inwon);


        bt_exit = new JButton("나가기");
        bt_addVote = new JButton("투표 생성");
        bt_voteList = new JButton("투표리스트");



        p = new JPanel();



        sp_ta.setBounds(10,10,515,360);

        la_msg.setBounds(10,380,60,30);

        sendTF.setBounds(70,380,450,30);





        bt_voteList.setBounds(10,420,120,30);
        bt_addVote.setBounds(210,420,120,30);
        bt_exit.setBounds(405,420,120,30);



        p.setLayout(null);

        p.setBackground(Color.gray);

        p.add(sp_ta);

        p.add(la_msg);

        p.add(sendTF);

        p.add(sp_list);

        p.add(bt_addVote);
        p.add(bt_exit);
        p.add(bt_voteList);



        add(p);

        setBounds(300,200,550,500);



        sendTF.requestFocus();





    }//생성자


}