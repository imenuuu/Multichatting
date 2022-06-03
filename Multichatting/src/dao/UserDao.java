package dao;

import model.GetFriendRes;
import model.UserDto;

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
            System.out.println(id);
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

            addFriendQuery="insert into Friend(userIdx,friendIdx)\n" +
                    "VALUES(?,?)";
            stmt =con.prepareStatement(addFriendQuery);
            stmt.setInt(1,friendIdx);
            stmt.setInt(2,userIdx);
            stmt =con.prepareStatement(addFriendQuery);
            stmt.executeQuery();


            stmt.close();


        } catch (SQLException e) {
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






}
