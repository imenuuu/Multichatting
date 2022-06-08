package Chatting;

import dao.ChatDao;
import dao.UserDao;
import gui.*;
import model.GetChatMessageRes;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class ChatClient extends JFrame implements ActionListener, Runnable{

    JList<String> roomInfo,roomInwon,waitInfo;

    JScrollPane sp_roomInfo, sp_roomInwon, sp_waitInfo;

    JButton bt_create, bt_enter, bt_exit;



    JPanel p;

    ChatGui cc;

    FriendListGui friendPanel;

    ChatGui cg;


    //소켓 입출력객체

    static BufferedReader in;

    static OutputStream out;



    String selectedRoom;


    ImageIcon icon;

    UserDao userDao=new UserDao();
    ChatDao chatDao=new ChatDao();

    public static class UserInfo{
        public int id;
        String userId;
        public String userName;
    }
    public static class ChatRoomInfo{
        public int id;
        String roomName;
    }
    public class ChatRoomJoin{
        int id;
    }


    ChatRoomJoin chatRoomJoin=new ChatRoomJoin();
    ChatRoomInfo chatRoomInfo=new ChatRoomInfo();
    UserInfo userInfo=new UserInfo();
    public ChatClient(String myName, String myId, int id) {
        setTitle("객패개패팀 채팅프로그램");

        icon = new ImageIcon("img/main.png"); //이미지 불러오기

        userInfo.id=id;
        userInfo.userId=myId;
        userInfo.userName=myName;



        cc = new ChatGui(id);
        cg=new ChatGui(id);

        roomInfo = new JList<String>();

        roomInfo.setBorder(new TitledBorder("방정보"));



        roomInfo.addMouseListener(new MouseAdapter() {

            @Override

            public void mouseClicked(MouseEvent e) {

                String str = roomInfo.getSelectedValue(); //"자바방--1"

                if(str==null)return;

                System.out.println("STR="+str);

                selectedRoom = str.substring(0, str.indexOf("-"));

                //"자바방"  <----  substring(0,3)



                //대화방 내의 인원정보

                sendMsg("170|"+selectedRoom);

            }

        });







        sp_roomInfo = new JScrollPane(roomInfo);




        bt_create = new JButton("방 생성");

        bt_enter = new JButton("방 입장");

        bt_exit = new JButton("로그아웃");







        p = new JPanel();



        sp_roomInfo.setBounds(10, 10, 460, 400);





        bt_create.setBounds(10,410,130,30);

        bt_enter.setBounds(170,410,130,30);

        bt_exit.setBounds(330,410,130,30);



        p.setLayout(null);

        p.setBackground(Color.gray);

        p.add(sp_roomInfo);



        p.add(bt_create);

        p.add(bt_enter);

        p.add(bt_exit);


        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(Color.gray);
        FriendListGui friendPanel=new FriendListGui(id,userInfo.userName);

        tabbedPane.addTab("메인화면",p);

        tabbedPane.addTab("친구목록",friendPanel);
        this.add(tabbedPane);

        setBounds(400,200, 500, 510);

        setVisible(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);



        connect();//서버연결시도 (in,out객체생성)

        new Thread(this).start();//서버메시지 대기

        sendMsg("100|"+ id);//(대기실)접속 알림

        sendMsg("150|"+ myName);//대화명 전달



        eventUp();

    }//생성자



    private void eventUp(){//이벤트소스-이벤트처리부 연결

        //대기실(Chatting.MainChat)

        bt_create.addActionListener(this);

        bt_enter.addActionListener(this);

        bt_exit.addActionListener(this);



        //대화방(gui.ChatClient)

        cc.sendTF.addActionListener(this);

        cc.bt_exit.addActionListener(this);
        cc.bt_addVote.addActionListener(this);
        cc.bt_voteList.addActionListener(this);

        cg.sendTF.addActionListener(this);

        cg.bt_exit.addActionListener(this);
        cg.bt_addVote.addActionListener(this);
        cg.bt_voteList.addActionListener(this);

    }



    @Override
    public void actionPerformed(ActionEvent e) {

        Object ob = e.getSource();

        if(ob==bt_create){//방만들기 요청

            while(true) {
                String title = JOptionPane.showInputDialog(this, "방제목:");
                if(title.isEmpty()){
                    JOptionPane.showMessageDialog
                            (null, "채팅방 이름을 입력해 주세요.");

                    break;
                }
                else if (chatDao.checkTitle(title)) {
                    JOptionPane.showMessageDialog
                            (null, "채팅방 이름이 중복 되었습니다.");
                    break;

                } else {
                    chatRoomInfo.id = chatDao.createChat(title,userInfo.userName);
                    //방 이름 설정 후 저장
                    chatRoomInfo.roomName = title;

                    //방제목을 서버에게 전달

                    sendMsg("160|" + title);


                    cc.setTitle("채팅방-[" + title + "]");


                    sendMsg("175|");//대화방내 인원정보 요청


                    setVisible(false);

                    cc.ta.setText("");
                    cc.setVisible(true); //대화방이동
                    cc.chatRoomId=chatRoomInfo.id;
                    chatDao.insertRoom(userInfo.id, chatRoomInfo.id);
                    chatDao.increateRoomCount(chatRoomInfo.id);
                    break;

                }
            }
            //
            //방 생성 PK 값 받아오고 저장



        }else if(ob==bt_enter){//방들어가기 요청



            if(selectedRoom == null){

                JOptionPane.showMessageDialog(this, "방을 선택!!");


                return;

            }





            sendMsg("200|"+ selectedRoom);

            //방정보 객체에 저장
            String title=sendMsg(selectedRoom);

            chatRoomInfo.id=chatDao.getIdByTitle(title);

            cc.chatRoomId=chatRoomInfo.id;

            sendMsg("175|");//대화방내 인원정보 요청

            setVisible(false);

            cc.ta.setText("");
            cc.setVisible(true);
            chatDao.insertRoom(userInfo.id,chatRoomInfo.id);
            ArrayList<GetChatMessageRes> arr;
            arr=chatDao.readMessage(chatRoomInfo.id);

            chatDao.increateRoomCount(chatRoomInfo.id);

            for(GetChatMessageRes getChatMessageRes :arr){
                cc.ta.append("["+ getChatMessageRes.getName()+"]▶ "+ getChatMessageRes.getMessage());
                cc.ta.append("\n");

            }

        }else if(ob==cc.bt_exit){//대화방 나가기 요청



            sendMsg("400|");

            cc.setVisible(false);

            setVisible(true);
            chatDao.exitRoom(userInfo.id,chatRoomInfo.id);
            chatDao.decreateRoomCount(chatRoomInfo.id);

        }
        else if(ob==cc.sendTF){//(TextField입력)메시지 보내기 요청

            String msg = cc.sendTF.getText();
            chatDao.insertMessage(chatRoomInfo.id,userInfo.id,msg);

            if(msg.length()>0){

                sendMsg("300|"+msg);

                cc.sendTF.setText("");

            }

        }
        else if (ob == cc.bt_addVote) {
            new AddVoteGui(userInfo.id,chatRoomInfo.id);
        }
        else if (ob==cc.bt_voteList){
            new VoteListGui(userInfo.id,chatRoomInfo.id);
        }
        else if(ob==bt_exit){//나가기(프로그램종료) 요청
            chatDao.exitRoom(userInfo.id,chatRoomInfo.id);
            userDao.userLogOut(userInfo.userId);
            dispose();
            new LoginGui();

        }



    }//actionPerformed



    public static void connect(){//(소켓)서버연결 요청

        try {

            //Socket s = new Socket(String host<서버ip>, int port<서비스번호>);

            Socket s = new Socket("localhost", 5000);//연결시도

            in = new BufferedReader(new InputStreamReader(s.getInputStream()));

            //in: 서버메시지 읽기객체    서버-----msg------>클라이언트

            out = s.getOutputStream();

            //out: 메시지 보내기, 쓰기객체    클라이언트-----msg----->서버

        } catch (IOException e) {

            e.printStackTrace();

        }

    }//connect



    public static String sendMsg(String msg){//서버에게 메시지 보내기

        try {

            out.write(  (msg+"\n").getBytes()  );

        }catch (IOException e) {

            e.printStackTrace();

        }

        return msg;
    }//sendMsg



    public void run(){//서버가 보낸 메시지 읽기

        //왜 run메소드 사용? GUI프로그램실행에 영향 미치지않는 코드 작성.

//메소드호출은 순차적인 실행!!  스레드메소드는 동시실행(기다리지 않는 별도 실행)!!

        try {

            while(true){

                String msg = in.readLine();//msg: 서버가 보낸 메시지

                //msg==> "300|안녕하세요"  "160|자바방--1,오라클방--1,JDBC방--1"

                String[] msgs = msg.split("\\|");

                String protocol = msgs[0];

                switch(protocol){
                    case "300":
                        cc.ta.append(msgs[1]+"\n");
                        cc.ta.setCaretPosition(cc.ta.getText().length());

                        break;



                    case "160"://방만들기

                        //방정보를 List에 뿌리기

                        if(msgs.length > 1){

                            //개설된 방이 한개 이상이었을때 실행

                            //개설된 방없음 ---->  msg="160|" 였을때 에러

                            String[] roomNames = msgs[1].split(",");

                            //"자바방--1,오라클방--1,JDBC방--1"

                            roomInfo.setListData(roomNames);

                        }

                        break;



                    case "175"://(대화방에서) 대화방 인원정보

                        String[] myRoomInwons = msgs[1].split(",");

                        cc.li_inwon.setListData(myRoomInwons);

                        break;


                    case "200"://대화방 입장

                        cc.ta.append("");

                        break;



                    case "400"://대화방 퇴장

                        cc.ta.append("=========["+msgs[1]+"]님 퇴장=========\n");

                        cc.ta.setCaretPosition(cc.ta.getText().length());

                        break;



                    case "202"://개설된 방의 타이틀 제목 얻기

                        cc.setTitle("채팅방-["+msgs[1]+"]");


                        break;





                }//클라이언트 switch



            }

        }catch (IOException e) {

            e.printStackTrace();

        }

    }//run





    public static void main(String[] args) {

        new LoginGui();

    }



}

