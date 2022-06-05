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
    JButton bt_vote;
    public JButton bt_exit;



    JPanel p;

    public ChatGui() {

        setTitle("객패개패팀 채팅프로그램");

        sendTF = new JTextField(15);

        la_msg = new JLabel("Message");



        ta = new JTextArea();

        ta.setLineWrap(true);//TextArea 가로길이를 벗어나는 text발생시 자동 줄바꿈

        li_inwon = new JList<String>();



        sp_ta = new JScrollPane(ta);

        sp_list = new JScrollPane(li_inwon);




        bt_exit = new JButton("나가기");



        p = new JPanel();



        sp_ta.setBounds(10,10,380,390);

        la_msg.setBounds(10,410,60,30);

        sendTF.setBounds(70,410,320,30);



        sp_list.setBounds(400,10,120,350);

        bt_exit.setBounds(400,410,120,30);



        p.setLayout(null);

        p.setBackground(Color.DARK_GRAY);

        p.add(sp_ta);

        p.add(la_msg);

        p.add(sendTF);

        p.add(sp_list);

        p.add(bt_exit);



        add(p);

        setBounds(300,200,550,550);


        sendTF.requestFocus();



    }//생성자



}