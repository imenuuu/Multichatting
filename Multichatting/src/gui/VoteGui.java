package gui;

import dao.VoteDao;
import model.GetVoteDetailRes;
import model.GetVoteUser;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class VoteGui extends JFrame {
    JTextArea title;
    JTextArea contents;
    JTextArea userList;

    JLabel la_title;
    JLabel la_contents;
    JLabel la_userList;


    JRadioButton voteRadio;


    JPanel p = new JPanel();
    public static class Vote{
        int userId;
        int voteId;

    }
    Vote vote=new Vote();
    VoteDao voteDao=new VoteDao();
    public VoteGui(int userId,int voteId){
        vote.userId=userId;
        vote.voteId=voteId;

        setTitle("투표 생성");
        setBounds(300,200,300,450);

        title = new JTextArea();
        la_title = new JLabel("제목");

        contents = new JTextArea();
        la_contents= new JLabel("내용");

        userList = new JTextArea();
        la_userList= new JLabel("참여 유저");

        voteRadio = new JRadioButton("참여");

        la_title.setBounds(13,5,80,20);
        title.setBounds(10,25,265,40);
        title.setLineWrap(true);

        la_contents.setBounds(13,80,80,20);
        contents.setBounds(10,100,265,120);
        contents.setLineWrap(true);

        la_userList.setBounds(13,240,80,20);
        userList.setBounds(10,260,265,120);
        userList.setLineWrap(true);



        voteRadio.setBounds(100,380,80,30);

        p.setLayout(null);

        p.add(title);
        p.add(la_title);
        p.add(contents);
        p.add(la_contents);
        p.add(userList);
        p.add(la_userList);
        p.add(voteRadio);


        add(p);

        ArrayList<GetVoteDetailRes> arr;
        arr=voteDao.getVoteDetail(voteId);
        for(GetVoteDetailRes getVoteDetailRes :arr){
            title.append(getVoteDetailRes.getTitle());
            contents.append(getVoteDetailRes.getContent());
        }

        ArrayList<GetVoteUser> userArray;
        userArray=voteDao.getUser(voteId);
        for(GetVoteUser getVoteUser:userArray){
            userList.append(getVoteUser.getUser());
            userList.append("\n");
        }
        setVisible(true);

        eventUp();



    }
    private void eventUp(){
        voteRadio.addItemListener(action);
    }

    ItemListener action = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if(voteRadio.isSelected()){
                if(voteDao.checkVote(vote.voteId,vote.userId)) {
                    JOptionPane.showMessageDialog
                            (null, "이미 투표하였습니다.");
                }else{

                    JOptionPane.showMessageDialog
                            (null, "투표완료");
                    voteDao.insertVote(vote.voteId, vote.userId);
                    dispose();
                }
            }
        }
    };


}
