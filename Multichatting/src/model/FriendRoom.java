package model;

import Server.ServerController;

import java.util.Vector;

public class FriendRoom {
    int id;
    public String title;//방제목
    public int count;//방 인원수

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }





    public String boss;//방장(방 개설자)

    public Vector<ServerController> userV;//userV: 같은 방에 접속한 Client정보 저장

    public FriendRoom(int id, String title, int count, String boss) {
        this.id=id;
        this.title = title;
        this.count = count;
        this.boss = boss;
    }

    public FriendRoom() {
        userV = new Vector<>();
    }
}
