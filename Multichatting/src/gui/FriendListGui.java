package gui;

import dao.UserDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

public class FriendListGui extends JPanel {
    // JTable

    BufferedReader in;
    OutputStream out;

    Object[][] ob = new Object[0][5];
    DefaultTableModel model;
    JTable friendListTable;
    JScrollPane jspane;
    String[] colstr = { "id", "이름", "접속 상태", "대화 신청", "정보 보기" };

    // 컴포넌트
    JPanel addPanel, listPanel;
    JLabel idlb;
    JTextField addFriendIDField,updateUserInfoIdfield;
    JButton addFriendButton,updateUserInfoButton, startChatButton, userInfoButton;
    UserDao userDao = new UserDao();

    //Idx
    int userIdx, friendIdx;

    ImageIcon icon;

    public FriendListGui(int id) {

        icon = new ImageIcon("img/main.png"); //이미지 불러오기
        setBounds(0, 0, 500, 500);

        // 친구 추가 패널
        addPanel = new JPanel();
        addPanel.setBounds(0, 0, 500, 500);
        idlb = new JLabel("ID: ");
        addFriendIDField=new JTextField(15);
        updateUserInfoButton=new JButton("내 정보 수정");
        addFriendButton = new JButton("추가");
        add(idlb);
        add(addFriendIDField);
        add(addFriendButton);
        add(updateUserInfoButton);

        add("North", addPanel);

        // 친구 목록 패널
        listPanel = new JPanel();
        listPanel.setBounds(0, 0, 500, 500);
        model = new DefaultTableModel(ob, colstr);
        friendListTable = new JTable(model);
        jspane = new JScrollPane(friendListTable);
        listPanel.add(jspane);
        add("Center", listPanel);


        //table에 버튼 추가
        friendListTable.getColumnModel().getColumn(3).setCellRenderer(new ChatTableCell());
        friendListTable.getColumnModel().getColumn(3).setCellEditor(new ChatTableCell());

        friendListTable.getColumnModel().getColumn(4).setCellRenderer(new InfoTableCell());
        friendListTable.getColumnModel().getColumn(4).setCellEditor(new InfoTableCell());

        //테이블 데이터 예시용(지워야함)

        //userDao


        updateUserInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserInfoGui(id);
            }
        });

        model=userDao.importFriendList(id, model); //친구목록 불러오기


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
                    friendIdx = userDao.getUserPK(friendId);
                    if(userDao.getFriendExists(userIdx,friendIdx)){
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
                        userDao.addFriend(userIdx, friendIdx);
                        userDao.addFriendUser(friendIdx,userIdx);
                        model.setNumRows(0);
                        model=userDao.importFriendList(id, model);
                        JOptionPane.showMessageDialog
                                (null, "친구 추가에 성공했습니다.");
                        addFriendIDField.setText("");
                    }
                }


            }
        });

    }
    //table에 넣을 버튼 만드는 클래스
    class ChatTableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer{

        JButton jb;

        public ChatTableCell() {
            // TODO Auto-generated constructor stub
            jb = new JButton("대화하기");

            jb.addActionListener(e -> { //대화하기 gui연결
                System.out.println("대화하기");
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
            jb = new JButton("정보 보기");

            jb.addActionListener(e -> {
                int friendId= (int) friendListTable.getValueAt(friendListTable.getSelectedRow(), 0);
                new FriendInfoGui(friendId);
                System.out.println(friendListTable.getValueAt(friendListTable.getSelectedRow(), 0));
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


    public String sendMsg(String msg){//서버에게 메시지 보내기

        try {

            out.write(  (msg+"\n").getBytes()  );

        }catch (IOException e) {

            e.printStackTrace();

        }

        return msg;
    }//sendMsg



}