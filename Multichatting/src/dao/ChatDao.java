package dao;

import model.GetChatMessageRes;
import model.GetRoomRes;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class ChatDao {
    DBConnector dbConnector=new DBConnector();
    PreparedStatement pstmt;
    ResultSet rs;
    Statement st;
    public int createChat(String title, String userName) {
        try {
            Connection con= DBConnector.getConnection();
            st=con.createStatement();
            String sql="insert into ChatRoom(roomName,boss) values(?,?)";
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,title);
            pstmt.setString(2,userName);
            pstmt.executeUpdate();

            String lastInsertIdQuery = "select last_insert_id() as 'chatRoomId'";
            pstmt=con.prepareStatement(lastInsertIdQuery);
            rs=pstmt.executeQuery();

            int chatRoomId =0;

            if(rs.next()) {
                chatRoomId=rs.getInt("chatRoomId");
                    return chatRoomId;
                }
            


            st.close();
            pstmt.close();
            rs.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void exitRoom(int userId, int chatRoomId) {
        try{
            Connection con = DBConnector.getConnection();
            st=con.createStatement();
            String sql="delete from ChatRoomJoin where userIdx=? and chatRoomIdx=?";
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1,userId);
            pstmt.setInt(2,chatRoomId);
            int r=pstmt.executeUpdate();
            st.close();
            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertRoom(int userId,int chatRoomId){
        try {
            Connection con=DBConnector.getConnection();
            st=con.createStatement();
            String sql="insert into ChatRoomJoin(userIdx, chatRoomIdx)\n" +
                    "VALUES(?,?)";
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1,userId);
            pstmt.setInt(2,chatRoomId);

            int r=pstmt.executeUpdate();

            st.close();
            pstmt.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getIdByTitle(String title) {
        try{
        Connection con=DBConnector.getConnection();
        String getIdByTitleQuery = "select id from ChatRoom where roomName=?";
        pstmt=con.prepareStatement(getIdByTitleQuery);
        pstmt.setString(1, title);
        rs=pstmt.executeQuery();

        int chatRoomId =0;

        if(rs.next()) {
            chatRoomId=rs.getInt("id");
            return chatRoomId;
        }

        pstmt.close();
        rs.close();
            con.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }
        return 0;
}

    public void insertMessage(int id, int userId, String msg) {
        try {
            Connection con=DBConnector.getConnection();
            st=con.createStatement();
            String sql="insert into ChatMessage(chatRoomIdx,userIdx, content)\n" +
                    "VALUES(?,?,?)";
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1,id);
            pstmt.setInt(2,userId);
            pstmt.setString(3,msg);

            int r=pstmt.executeUpdate();

            st.close();
            pstmt.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean checkTitle(String title) {
        String checkTitleSql="select count(*)'cnt' from ChatRoom where roomName=?;";

        try {
            Connection con= DBConnector.getConnection();
            pstmt=con.prepareStatement(checkTitleSql);
            pstmt.setString(1,title);
            rs=pstmt.executeQuery();
            if(rs.next()){
                int cnt=rs.getInt("cnt");
                if(cnt>0){
                    return true;
                }

            }

            pstmt.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<GetChatMessageRes> readMessage(int id) {
        ArrayList<GetChatMessageRes> arr = new ArrayList<>();
        String readMessageQuery ="select userName,CM.content from User join ChatMessage CM on User.id = CM.userIdx\n" +
                "join ChatRoom CR on CR.id = CM.chatRoomIdx where chatRoomIdx=? order by CM.messageCreatedDate asc";

        try {
            Connection con=DBConnector.getConnection();
            pstmt=con.prepareStatement(readMessageQuery);
            pstmt.setInt(1,id);
            rs=pstmt.executeQuery();
            while(rs.next()){
                arr.add(new GetChatMessageRes(rs.getString(1),rs.getString(2)));
            }
            pstmt.close();
            rs.close();
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return arr;
    }

    public Vector<GetRoomRes> getRoomList() {
        Vector<GetRoomRes> arr = new Vector<>();
        String getRoomListQuery ="select id,roomName,count,boss from ChatRoom where roomName NOT IN('1:1 채팅')";
        try {
            Connection con=DBConnector.getConnection();
            pstmt=con.prepareStatement(getRoomListQuery);
            rs=pstmt.executeQuery();
            while(rs.next()){
                arr.add(new GetRoomRes(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getString(4)));
            }
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return arr;
    }

    public void increateRoomCount(int id) {
        try {
            Connection con=DBConnector.getConnection();
            st=con.createStatement();
            String sql="UPDATE ChatRoom SET count=count+1 where id=?";
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1,id);
            int r=pstmt.executeUpdate();
            st.close();
            pstmt.close();


            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void decreateRoomCount(int id) {
        try {
            Connection con=DBConnector.getConnection();
            st=con.createStatement();
            String sql="UPDATE ChatRoom SET count=count-1 where id=?";
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1,id);
            int r=pstmt.executeUpdate();
            st.close();
            pstmt.close();

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
