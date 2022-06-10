package gui;





import Controller.VoteController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddVoteGui extends JFrame {

    JTextArea title;
    JTextArea contents;

    JLabel la_title;
    JLabel la_contents;

    JButton insert;

    JPanel p = new JPanel();

    int userIdx;
    int roomIdx;


    VoteController voteController =new VoteController();

    public AddVoteGui(int userIdx, int roomIdx){

        this.userIdx = userIdx;
        this.roomIdx = roomIdx;

        setTitle("투표 생성");
        setBounds(300,200,300,350);

        title = new JTextArea();
        la_title = new JLabel("제목 입력");

        contents = new JTextArea();
        la_contents= new JLabel("내용 입력");

        insert = new JButton("생성");

        la_title.setBounds(13,5,80,20);
        title.setBounds(10,25,265,40);
        title.setLineWrap(true);

        la_contents.setBounds(13,80,80,20);
        contents.setBounds(10,100,265,150);
        contents.setLineWrap(true);

        insert.setBounds(100,260,80,30);

        p.setLayout(null);

        p.add(title);
        p.add(la_title);
        p.add(contents);
        p.add(la_contents);
        p.add(insert);

        add(p);
        setVisible(true);

        insert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String voteTitle = title.getText();
                String voteContent = contents.getText();

                if(voteTitle.isEmpty()){
                    JOptionPane.showMessageDialog
                            (null, "제목을 입력해주세요.");
                }
                else if(voteContent.isEmpty()){
                    JOptionPane.showMessageDialog
                            (null, "내용을 입력해주세요.");
                } else{
                    voteController.createVote(roomIdx,voteTitle,voteContent,userIdx);
                    JOptionPane.showMessageDialog
                            (null, "투표 생성 완료");
                    setVisible(false);
                }
            }
        });
    }
}
