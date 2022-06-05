package dao;

import model.GetFriendRes;
import model.GetUserRes;
import model.UserDto;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;

public class UserDao {
    DBConnector dbConnector=new DBConnector();
    PreparedStatement stmt;
    ResultSet rs;
    Statement st;
    public Boolean UserLogin(String userId,String password) {
        String logInSql="select userPassword from User where userId=?;";

        try {
            Connection con= DBConnector.getConnection();
            stmt =con.prepareStatement(logInSql);
            stmt.setString(1,userId);
            rs= stmt.executeQuery();
            if(rs.next()) {
                if (rs.getString(1).contentEquals(password)) {
                    return true;
                }
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void UserUpdateStatus(String userId){
        try{
            Connection con = DBConnector.getConnection();
            st=con.createStatement();
            String sql="update User SET userLogin='로그인 중' where userId=?";
            stmt =con.prepareStatement(sql);
            stmt.setString(1,userId);
            stmt.executeUpdate();
            st.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void userLogOut(String userId) {
        try{
            System.out.println(userId);
            Connection con = DBConnector.getConnection();
            st=con.createStatement();
            String sql="update User SET userLogin='로그아웃' where userId=?";
            stmt =con.prepareStatement(sql);
            stmt.setString(1,userId);
            stmt.executeUpdate();
            st.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void UserJoin(UserDto userDto){
        try {
            Connection con= DBConnector.getConnection();
            st=con.createStatement();
            String sql="insert into User(userId,userPassword,userName,lolNickName,lolRank,battleNickName,battleRank,fifaNickName,\n" +
                    "                 fifaRank,starNickName,starRank,overWatchNickName,overwatchRank)\n" +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            stmt =con.prepareStatement(sql);
            stmt.setString(1,userDto.getUserId());
            stmt.setString(2,userDto.getPassword());
            stmt.setString(3,userDto.getUserName());
            stmt.setString(4,userDto.getLolNickName());
            stmt.setString(5,userDto.getLolRank());
            stmt.setString(6,userDto.getBattleNickName());
            stmt.setString(7,userDto.getBattleRank());
            stmt.setString(8,userDto.getFifaNickName());
            stmt.setString(9,userDto.getFifaRank());
            stmt.setString(10,userDto.getStarNickName());
            stmt.setString(11,userDto.getStarRank());
            stmt.setString(12,userDto.getOverNickName());
            stmt.setString(13,userDto.getOverwatchRank());
            stmt.executeUpdate();

            st.close();
            stmt.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkId(String myId) {
        String checkIdSql="select count(*)'cnt' from User where userId=?;";

        try {
            Connection con= DBConnector.getConnection();
            stmt =con.prepareStatement(checkIdSql);
            stmt.setString(1,myId);
            rs= stmt.executeQuery();
            if(rs.next()){
                int cnt=rs.getInt("cnt");
                if(cnt>0){
                    return true;
                }
                System.out.println(cnt);
            }

            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUserName(String myId) {
        String getUserNameQuery="select userName from User where userId=?";
        String myName="";
        try{
            Connection con=DBConnector.getConnection();
            stmt =con.prepareStatement(getUserNameQuery);
            stmt.setString(1,myId);
            rs= stmt.executeQuery();
            if(rs.next()) {
                myName=rs.getString("userName");
                return myName;

            }
            System.out.println(myName);
            stmt.close();
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return myName;
    }
    public int getUserPK(String myId){
        String getUserNameQuery="select id from User where userId=?";
        int id=0;
        try{
            Connection con=DBConnector.getConnection();
            stmt =con.prepareStatement(getUserNameQuery);
            stmt.setString(1,myId);
            rs= stmt.executeQuery();
            if(rs.next()) {
                id=rs.getInt("id");
                return id;
            }
            stmt.close();
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return id;
    }

    public void addFriend(int userIdx,int friendIdx){
        String addFriendQuery;

        try {
            Connection con=DBConnector.getConnection();


            addFriendQuery="insert into Friend(userIdx,friendIdx)\n" +
                    "VALUES(?,?)";
            stmt =con.prepareStatement(addFriendQuery);
            stmt.setInt(1,userIdx);
            stmt.setInt(2,friendIdx);
            stmt.executeUpdate();



            stmt.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void addFriendUser(int friendIdx, int userIdx) {
        String addFriendQuery;

        try {
            Connection con = DBConnector.getConnection();
            addFriendQuery="insert into Friend(friendIdx,userIdx)\n" +
                    "VALUES(?,?)";
            stmt =con.prepareStatement(addFriendQuery);
            stmt.setInt(1,userIdx);
            stmt.setInt(2,friendIdx);
            stmt.executeUpdate();

            stmt.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<GetFriendRes> getFriendList(int userIdx){
        ArrayList<GetFriendRes> resList = new ArrayList<>();
        String getFriendQuery ="select userId,userName,userLogin from User join Friend on Friend.friendIdx=User.id " +
                "where Friend.userIdx=?";

        try {
            Connection con=DBConnector.getConnection();
            stmt =con.prepareStatement(getFriendQuery);
            stmt.setInt(1,userIdx);
            rs= stmt.executeQuery();
            while(rs.next()){
                resList.add(new GetFriendRes(rs.getString(1),rs.getString(2),rs.getString(3)));
            }
            stmt.close();
            rs.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return resList;
    }


    public DefaultTableModel importFriendList(int id, DefaultTableModel model) {
        String importFriendSql="select u.id,u.userName, u.userLogin from User AS u join Friend AS f ON u.id=f.friendIdx WHERE f.userIdx=? order by u.id asc";

        try {
            Connection con= DBConnector.getConnection();
            stmt=con.prepareStatement(importFriendSql);
            stmt.setInt(1,id);
            rs=stmt.executeQuery();
            while(rs.next()){
                int idx = rs.getInt("id");
                String name=rs.getString("userName");
                String status=rs.getString("userLogin");

                Object data[]= {idx,name, status, "", ""};
                model.addRow(data);
                System.out.println(idx+name+status);
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }


    public ArrayList<GetUserRes> getFriendInfo(int userId) {
        String getUserInfoQuery = "";
        ArrayList<GetUserRes> arr=new ArrayList<>();
        String readMessageQuery ="select userId,username, lolnickname, lolrank, battlenickname, battlerank, fifanickname, fifarank, starnickname, starrank, overwatchnickname, overwatchrank from User where id=?";

        try {
            Connection con=DBConnector.getConnection();
            stmt=con.prepareStatement(readMessageQuery);
            stmt.setInt(1,userId);
            rs=stmt.executeQuery();
            while(rs.next()) {
                arr.add(new GetUserRes(rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return arr;

    }

    public boolean getFriendExists(int userIdx, int friendIdx) {
        String checkTitleSql="select count(*)'cnt' from Friend where userIdx=? and friendIdx=?";

        try {
            Connection con= DBConnector.getConnection();
            stmt=con.prepareStatement(checkTitleSql);
            stmt.setInt(1,userIdx);
            stmt.setInt(2,friendIdx);
            rs=stmt.executeQuery();
            if(rs.next()){
                int cnt=rs.getInt("cnt");
                if(cnt>0){
                    return true;
                }
                System.out.println(cnt);
            }

            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateUserInfo(GetUserRes getUserRes, int id) {
        try {
            Connection con= DBConnector.getConnection();
            st=con.createStatement();
            String sql="Update User SET userName=?,lolNickName=?,lolRank=?,battleNickName=?,battleRank=?,fifaNickName=?,\n" +
                    "                 fifaRank=?,starNickName=?,starRank=?,overWatchNickName=?,overwatchRank=? where User.id=?\n";
            stmt =con.prepareStatement(sql);
            stmt.setString(1,getUserRes.getUserName());
            stmt.setString(2,getUserRes.getLolNickName());
            stmt.setString(3,getUserRes.getLolRank());
            stmt.setString(4,getUserRes.getBattleNickName());
            stmt.setString(5,getUserRes.getBattleRank());
            stmt.setString(6,getUserRes.getFifaNickName());
            stmt.setString(7,getUserRes.getFifaRank());
            stmt.setString(8,getUserRes.getStarNickName());
            stmt.setString(9,getUserRes.getStarRank());
            stmt.setString(10,getUserRes.getOverNickName());
            stmt.setString(11,getUserRes.getOverwatchRank());
            stmt.setInt(12,id);
            stmt.executeUpdate();

            st.close();
            stmt.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getUserId(int userIdx) {
        String getUserNameQuery="select userId from User where userId=?";
        String userId="";
        try{
            Connection con=DBConnector.getConnection();
            stmt =con.prepareStatement(getUserNameQuery);
            stmt.setInt(1,userIdx);
            rs= stmt.executeQuery();
            if(rs.next()) {
                userId=rs.getString("userName");
                return userId;

            }
            System.out.println(userId);
            stmt.close();
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return userId;
    }
}
