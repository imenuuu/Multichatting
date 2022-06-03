package model;

public class GetUserRes {
    private String userName;
    private String lolNickName;
    private String lolRank;
    private String battleNickName;
    private String battleRank;
    private String fifaNickName;
    private String fifaRank;
    private String starNickName;
    private String starRank;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLolNickName() {
        return lolNickName;
    }

    public void setLolNickName(String lolNickName) {
        this.lolNickName = lolNickName;
    }

    public String getLolRank() {
        return lolRank;
    }

    public void setLolRank(String lolRank) {
        this.lolRank = lolRank;
    }

    public String getBattleNickName() {
        return battleNickName;
    }

    public void setBattleNickName(String battleNickName) {
        this.battleNickName = battleNickName;
    }

    public String getBattleRank() {
        return battleRank;
    }

    public void setBattleRank(String battleRank) {
        this.battleRank = battleRank;
    }

    public String getFifaNickName() {
        return fifaNickName;
    }

    public void setFifaNickName(String fifaNickName) {
        this.fifaNickName = fifaNickName;
    }

    public String getFifaRank() {
        return fifaRank;
    }

    public void setFifaRank(String fifaRank) {
        this.fifaRank = fifaRank;
    }

    public String getStarNickName() {
        return starNickName;
    }

    public void setStarNickName(String starNickName) {
        this.starNickName = starNickName;
    }

    public String getStarRank() {
        return starRank;
    }

    public void setStarRank(String starRank) {
        this.starRank = starRank;
    }

    public String getOverNickName() {
        return overNickName;
    }

    public void setOverNickName(String overNickName) {
        this.overNickName = overNickName;
    }

    public String getOverwatchRank() {
        return overwatchRank;
    }

    public void setOverwatchRank(String overwatchRank) {
        this.overwatchRank = overwatchRank;
    }

    private String overNickName;
    private String overwatchRank;
    public GetUserRes(String userName, String lolNickName, String lolRank, String battleNickName, String battleRank, String fifaNickName, String fifaRank, String starNickName, String starRank, String overNickName, String overwatchRank) {
        this.userName = userName;
        this.lolNickName = lolNickName;
        this.lolRank = lolRank;
        this.battleNickName = battleNickName;
        this.battleRank = battleRank;
        this.fifaNickName = fifaNickName;
        this.fifaRank = fifaRank;
        this.starNickName = starNickName;
        this.starRank = starRank;
        this.overNickName = overNickName;
        this.overwatchRank = overwatchRank;
    }
}
