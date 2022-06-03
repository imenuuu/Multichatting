package Chatting;

import dao.ChatDao;
import model.ChatRoomDto;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;



public class Server implements Runnable{

    //Server클래스: 소켓을 통한 접속서비스, 접속클라이언트 관리



    Vector<Controller> allV;//모든 사용자(대기실사용자 + 대화방사용자)

    Vector<Controller> waitV;//대기실 사용자

    Vector<Room> roomV;//개설된 대화방 Chatting.Room-vs(Vector) : 대화방사용자
    ChatDao chatDao=new ChatDao();
    Room myRoom;



    public Server() {

        allV = new Vector<>();

        waitV = new Vector<>();

        roomV = new Vector<>();


        Vector<ChatRoomDto> arr;
        arr=chatDao.getRoomList();
        for (ChatRoomDto chatRoomDto:arr){
            myRoom=new Room();
            myRoom.title=chatRoomDto.getTitle();
            myRoom.count=chatRoomDto.getCount();
            myRoom.boss=chatRoomDto.getBoss();
            roomV.add(myRoom);
        }





        //Thread t = new Thread(run메소드의 위치);  t.start();

        new Thread(this).start();

    }//생성자





    @Override

    public void run(){

        try {

            ServerSocket ss = new ServerSocket(5000);

            //현재 실행중인 ip + 명시된 port ----> 소켓서비스



            System.out.println("객패개패팀 채팅 서버 시작");

            while(true){

                Socket s = ss.accept();//클라이언트 접속 대기

                //s: 접속한 클라이언트의 소켓정보

                Controller ser = new Controller(s, this);

                //allV.add(ser);//전체사용자에 등록

                //waitV.add(ser);//대기실사용자에 등록

            }



        } catch(SocketException se){
            System.out.println("사용자와의 접속이 끊겼습니다.");
        } catch (IOException e) {

            e.printStackTrace();

        }

    }//run



    public static void main(String[] args) {

        new Server();

    }





}