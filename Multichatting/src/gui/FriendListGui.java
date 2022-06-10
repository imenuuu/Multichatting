package gui;

import Controller.ChatController;
import Controller.UserController;
import model.GetChatMessageRes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class FriendListGui extends JPanel implements Runnable{
    static BufferedReader in;
    static OutputStream out;
    ChatGui cg;
    Object[][] ob = new Object[0][6];
    DefaultTableModel model;
    JTable friendListTable;
    JScrollPane jspane;
    JPanel p;
    String[] colstr = { "아이디", "이름", "접속 상태", "대화", "정보","삭제" };

    // 컴포넌트
    JPanel addPanel, listPanel,friendPanel;

    JLabel idlb;
    JTextField addFriendIDField,updateUserInfoIdfield;
    JButton addFriendButton,updateUserInfoButton,refreshUserInfo, startChatButton, userInfoButton;
    UserController userController = new UserController();

    //Idx
    public static class UserInfo {
        int idx;
        String userId;
        String userName;
    }
    int userIdx, friendIdx;
    UserInfo userInfo= new UserInfo();
    ImageIcon icon;

    public FriendListGui(int id, String userName) {

        userInfo.idx=id;
        cg = new ChatGui(id);
        userInfo.userName=userName;
        icon = new ImageIcon("img/main.png"); //이미지 불러오기
        setBounds(0, 0, 500, 500);

        // 친구 추가 패널

        addPanel = new JPanel();
        addPanel.setBounds(0, 0, 500, 500);
        idlb = new JLabel("ID: ");
        addFriendIDField=new JTextField(15);
        updateUserInfoButton=new JButton("내 정보 수정");
        addFriendButton = new JButton("추가");
        refreshUserInfo=new JButton("새로고침");
        add(idlb);
        add(addFriendIDField);
        add(addFriendButton);
        add(updateUserInfoButton);
        add(refreshUserInfo);

        add("North", addPanel);


        // 친구 목록 패널
        listPanel = new JPanel();
        listPanel.setBounds(0, 0, 500, 500);
        model = new DefaultTableModel(ob, colstr);
        friendListTable = new JTable(model);
        jspane = new JScrollPane(friendListTable);
        listPanel.add(jspane);
        listPanel.setBackground(Color.gray);
        add("Center", listPanel);


        //table에 버튼 추가
        friendListTable.getColumnModel().getColumn(3).setCellRenderer(new ChatTableCell());
        friendListTable.getColumnModel().getColumn(3).setCellEditor(new ChatTableCell());

        friendListTable.getColumnModel().getColumn(4).setCellRenderer(new InfoTableCell());
        friendListTable.getColumnModel().getColumn(4).setCellEditor(new InfoTableCell());

        friendListTable.getColumnModel().getColumn(5).setCellRenderer(new deleteFriendButton());
        friendListTable.getColumnModel().getColumn(5).setCellEditor(new deleteFriendButton());

        //테이블 데이터 예시용(지워야함)

        //userDao
/*
        friendPanel = new JPanel();
        friendPanel.setBounds(0, 0, 500, 500);


        waitInfo=new JList<String>();
        waitInfo.setBorder(new TitledBorder("친구 중 접속자"));

        sp_waitInfo=new JScrollPane(waitInfo);

        sp_waitInfo.setBounds(10, 320, 500, 500);
        friendPanel.add(sp_waitInfo);
        add("South",friendPanel);
*/

        updateUserInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserInfoGui(id);
            }
        });

        model= userController.importFriendList(id, model); //친구목록 불러오기


        //친구 추가 이벤트 처리
        addFriendButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String friendId = addFriendIDField.getText();
                if(friendId.isEmpty()){
                    JOptionPane.showMessageDialog
                            (null, "추가할 아이디를 입력해주세요.");
                    addFriendIDField.setText("");
                }
                else {

                    userIdx = id;
                    friendIdx = userController.getUserPK(friendId);
                    if(userController.getFriendExists(userIdx,friendIdx)){
                        JOptionPane.showMessageDialog
                                (null, "이미 추가한 유저 입니다.");
                        addFriendIDField.setText("");
                    }
                    else if(friendIdx==0) {
                        JOptionPane.showMessageDialog
                                (null, "없는 아이디입니다.");
                        addFriendIDField.setText("");
                    }
                    else {
                        userController.addFriend(userIdx, friendIdx);
                        userController.addFriendUser(friendIdx,userIdx);
                        model.setNumRows(0);
                        model= userController.importFriendList(id, model);
                        JOptionPane.showMessageDialog
                                (null, "친구 추가에 성공했습니다.");
                        addFriendIDField.setText("");
                    }
                }
            }

        });
        refreshUserInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setNumRows(0);
                model= userController.importFriendList(id, model);
                JOptionPane.showMessageDialog
                        (null, "새로고침 완료했습니다.");
            }
        });
        connect();//서버연결시도 (in,out객체생성)

        new Thread(this).start();//서버메시지 대기


    }
    ChatController chatController = new ChatController();
    //table에 넣을 버튼 만드는 클래스
    public class ChatTableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

        JButton jb;

        public ChatTableCell() {
            // TODO Auto-generated constructor stub
            jb = new JButton("대화");
            jb.addActionListener(e -> { //대화하기 gui연결
                String selectRoom="1:1 채팅";
                sendMsg("151|"+userInfo.userName);
                sendMsg("203|"+selectRoom);
                int chatRoomId= chatController.getIdByTitle(selectRoom);
                cg.setTitle("채팅방-[" + selectRoom + "]");
                cg.ta.setText("");
                setVisible(false);
                cg.setVisible(true);
                ArrayList<GetChatMessageRes> arr;
                arr= chatController.readMessage(chatRoomId);

                for(GetChatMessageRes getChatMessageRes :arr){
                    cg.ta.append("["+ getChatMessageRes.getName()+"]▶ "+ getChatMessageRes.getMessage());
                    cg.ta.append("\n");

                }


            });

            cg.bt_exit.addActionListener(e ->
            {
                sendMsg("401");
                cg.dispose();
            });


            cg.sendTF.addActionListener(e->{
                String msg = cg.sendTF.getText();
                    if (msg.length() > 0) {
                        sendMsg("301|" + msg);
                        cg.sendTF.setText("");

                    }});
        }


        @Override
        public Object getCellEditorValue() {
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            // TODO Auto-generated method stub
            return jb;
        }
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                                                     int column) {
            // TODO Auto-generated method stub
            return jb;
        }

    }

    class deleteFriendButton extends AbstractCellEditor implements TableCellEditor, TableCellRenderer{

        JButton jb;

        public deleteFriendButton() { //정보보기 gui연결
            // TODO Auto-generated constructor stub
            jb = new JButton("삭제");

            jb.addActionListener(e -> {
                int friendId= userController.getUserPK((String) friendListTable.getValueAt(friendListTable.getSelectedRow(), 0));
                String friendName=(String) friendListTable.getValueAt(friendListTable.getSelectedRow(), 1);

                userController.deleteUser(userInfo.idx,friendId);
                userController.deleteUser(friendId,userInfo.idx);
                model.setNumRows(0);
                model= userController.importFriendList(userInfo.idx, model);
                JOptionPane.showMessageDialog
                        (null, friendName+"님이 삭제 되었습니다.");
            });

        }

        @Override
        public Object getCellEditorValue() {
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            // TODO Auto-generated method stub
            return jb;
        }
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                                                     int column) {
            // TODO Auto-generated method stub
            return jb;
        }

    }

    class InfoTableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer{

        JButton jb;

        public InfoTableCell() { //정보보기 gui연결
            // TODO Auto-generated constructor stub
            jb = new JButton("정보");

            jb.addActionListener(e -> {
                int friendId= userController.getUserPK((String) friendListTable.getValueAt(friendListTable.getSelectedRow(), 0));
                new FriendInfoGui(friendId);
            });

        }

        @Override
        public Object getCellEditorValue() {
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            // TODO Auto-generated method stub
            return jb;
        }
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                                                     int column) {
            // TODO Auto-generated method stub
            return jb;
        }

    }

    //배경 이미지 넣기
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(icon.getImage(), 0, 0, null);
    }

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



    private static String sendMsg(String msg){//서버에게 메시지 보내기

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

                    case "301":
                        cg.ta.append(msgs[1]+"\n");
                        cg.ta.setCaretPosition(cg.ta.getText().length());
                        break;




                    case "203"://대화방 입장
                        cg.ta.append("=========["+msgs[1]+"]님 입장=========\n");

                        break;


                    case "202"://개설된 방의 타이틀 제목 얻기

                        cg.setTitle("채팅방-["+msgs[1]+"]");


                        break;
                    case "401":
                        cg.ta.append("=========["+msgs[1]+"]님 퇴장=========\n");

                        cg.ta.setCaretPosition(cg.ta.getText().length());

                        break;






                }//클라이언트 switch



            }

        }catch (IOException e) {

            e.printStackTrace();

        }

    }//run
}