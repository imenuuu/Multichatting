package model;

public class GetRoomRes {
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getBoss() {
        return boss;
    }

    public void setBoss(String boss) {
        this.boss = boss;
    }

    public GetRoomRes(int id, String title, int count, String boss) {
        this.id = id;
        this.title = title;
        this.count = count;
        this.boss = boss;
    }

    private int id;
    private String title;
    private int count;
    private String boss;

}
