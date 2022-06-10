package gui;

import Controller.VoteController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class VoteListGui extends JFrame {
    JPanel p = new JPanel();
    JTable voteListTable;
    JScrollPane js;
    DefaultTableModel model;
    String[] header = {"id","제목","게시자","자세히 보기","삭제"};
    Object[][] ob = new Object[0][4];


    VoteController voteController =new VoteController();
    public static class Chat{
        int userId;
        int roomId;

    }
    Chat chat=new Chat();
    ImageIcon icon;
    public VoteListGui(int userId, int roomId){
        icon = new ImageIcon("img/main.png"); //이미지 불러오기

        chat.userId=userId;
        chat.roomId=roomId;
        setTitle("투표 리스트");
        setBounds(300,200,500,500);
        setLayout(null);


        model = new DefaultTableModel(ob,header);
        voteListTable = new JTable(model);
        js = new JScrollPane(voteListTable);
        /*
        voteListTable.getColumn("id").setPreferredWidth(0);
        voteListTable.getColumn("제목").setPreferredWidth(300);
        voteListTable.getColumn("게시자").setPreferredWidth(50);
        voteListTable.getColumn("자세히 보기").setPreferredWidth(50);

         */
        js.setBounds(5,5,480,400);
        add(js);

        voteListTable.getColumnModel().getColumn(3).setCellRenderer(new VoteTableCell());
        voteListTable.getColumnModel().getColumn(3).setCellEditor(new VoteTableCell());

        voteListTable.getColumnModel().getColumn(4).setCellRenderer(new VoteDeleteCell());
        voteListTable.getColumnModel().getColumn(4).setCellEditor(new VoteDeleteCell());

        model= voteController.getVoteList(roomId,model);

        setVisible(true);
    }

    class VoteTableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

        JButton jb;


        public VoteTableCell() {
            // TODO Auto-generated constructor stub
            jb = new JButton("내용보기");

            jb.addActionListener(e -> {
                new VoteGui(chat.userId, (Integer) voteListTable.getValueAt(voteListTable.getSelectedRow(),0));
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
    public class VoteDeleteCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

        JButton jb;


        public VoteDeleteCell() {
            // TODO Auto-generated constructor stub
            jb = new JButton("삭제");

            jb.addActionListener(e -> {
                int userId= voteController.getVoteMaster((Integer) voteListTable.getValueAt(voteListTable.getSelectedRow(),0));
                if(userId==chat.userId){
                    voteController.deleteVoteUser((Integer) voteListTable.getValueAt(voteListTable.getSelectedRow(),0));
                    voteController.deleteVote((Integer) voteListTable.getValueAt(voteListTable.getSelectedRow(),0));
                    JOptionPane.showMessageDialog
                            (null, "투표가 삭제되었습니다.");

                    model.setNumRows(0);
                    model= voteController.getVoteList(chat.roomId,model);
                }
                else{
                    JOptionPane.showMessageDialog
                            (null, "투표 삭제 권한이 없습니다.");
                }
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
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        g.drawImage(icon.getImage(), 0, 0, null);
    }

}
