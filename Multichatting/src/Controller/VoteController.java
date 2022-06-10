package Controller;


import Server.DBConnector;
import gui.MainGui;
import model.GetVoteDetailRes;
import model.GetVoteUserRes;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;

public class VoteController {
    DBConnector dbConnector=new DBConnector();
    PreparedStatement stmt;
    ResultSet rs;
    Statement st;


    MainGui.UserInfo userInfo = new MainGui.UserInfo();
    MainGui.ChatRoomInfo chatRoomInfo = new MainGui.ChatRoomInfo();

    public void createVote(int chatRoomIdx,String title, String content,int userIdx){

        try {
            Connection con= DBConnector.getConnection();
            st=con.createStatement();
            String sql="insert into Vote(chatRoomIdx,title,content,userIdx)\n" +
                    "VALUES(?,?,?,?)";
            stmt =con.prepareStatement(sql);
            stmt.setInt(1,chatRoomIdx);
            stmt.setString(2,title);
            stmt.setString(3,content);
            stmt.setInt(4, userIdx);

            int r = stmt.executeUpdate();

            st.close();
            stmt.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public DefaultTableModel getVoteList(int chatRoomid , DefaultTableModel model){
        String importFriendSql="select Vote.id,userName,title,content from Vote join User on User.id=Vote.userIdx where chatRoomIdx=?";

        try {
            Connection con= DBConnector.getConnection();
            stmt=con.prepareStatement(importFriendSql);
            stmt.setInt(1,chatRoomid);
            rs=stmt.executeQuery();
            while(rs.next()){
                int id=rs.getInt("id");
                String userName = rs.getString("userName");
                String title=rs.getString("title");
                String content=rs.getString("content");

                Object[] data = {id,title,userName, ""};

                model.addRow(data);
            }
            stmt.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }

    public void insertVote(int voteIdx,int userIdx){
        try {
            Connection con= DBConnector.getConnection();
            st=con.createStatement();
            String sql="insert into VoteUserList(voteIdx,userIdx)\n" +
                    "VALUES(?,?)";
            stmt =con.prepareStatement(sql);
            stmt.setInt(1,voteIdx);
            stmt.setInt(2, userIdx);

            int r = stmt.executeUpdate();

            st.close();
            stmt.close();

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<GetVoteDetailRes> getVoteDetail(int voteId){
        ArrayList<GetVoteDetailRes> arr = new ArrayList<>();
        String readMessageQuery ="select title,content\n" +
                "from Vote where Vote.id=?;";

        try {
            Connection con=DBConnector.getConnection();
            stmt=con.prepareStatement(readMessageQuery);
            stmt.setInt(1,voteId);
            rs=stmt.executeQuery();
            while(rs.next()){
                arr.add(new GetVoteDetailRes(rs.getString(1),rs.getString(2)));
            }
            con.close();
            stmt.close();
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return arr;
    }


    public boolean checkVote(int voteId, int userId) {
        String checkVoteSql="select count(*)'cnt' from VoteUserList where voteIdx=? and userIdx=?";

        try {
            Connection con= DBConnector.getConnection();
            stmt=con.prepareStatement(checkVoteSql);
            stmt.setInt(1,voteId);
            stmt.setInt(2,userId);
            rs=stmt.executeQuery();
            if(rs.next()){
                int cnt=rs.getInt("cnt");
                if(cnt>0){
                    return true;
                }
            }

            stmt.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<GetVoteUserRes> getUser(int voteId) {
        ArrayList<GetVoteUserRes> arr = new ArrayList<>();
        String readMessageQuery ="select concat('이름 : ',userName,' ID :',userId) as 'voteUser' from User  join VoteUserList on User.id=VoteUserList.userIdx\n" +
                "where voteIdx=?;";

        try {
            Connection con=DBConnector.getConnection();
            stmt=con.prepareStatement(readMessageQuery);
            stmt.setInt(1,voteId);
            rs=stmt.executeQuery();
            while(rs.next()){
                arr.add(new GetVoteUserRes(rs.getString(1)));
            }
            stmt.close();
            rs.close();
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return arr;
    }

    public int getVoteMaster(int voteId) {
        String getVoteMasterQuery="select userIdx from Vote where Vote.id=?";
        int userId=0;
        try{
            Connection con=DBConnector.getConnection();
            stmt =con.prepareStatement(getVoteMasterQuery);
            stmt.setInt(1,voteId);
            rs= stmt.executeQuery();
            if(rs.next()) {
                userId=rs.getInt("userIdx");
                return userId;
            }
            stmt.close();
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return userId;
    }
    public void deleteVote(int voteId) {
        try{
            Connection con = DBConnector.getConnection();
            st=con.createStatement();
            String sql="delete from Vote where Vote.id=?";
            stmt=con.prepareStatement(sql);
            stmt.setInt(1,voteId);
            int r=stmt.executeUpdate();
            st.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteVoteUser(int voteId) {
        try{
            Connection con = DBConnector.getConnection();
            st=con.createStatement();
            String sql="delete from VoteUserList where voteIdx=?";
            stmt=con.prepareStatement(sql);
            stmt.setInt(1,voteId);
            int r=stmt.executeUpdate();
            st.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

