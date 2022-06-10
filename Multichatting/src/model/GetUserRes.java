package model;

public class GetUserRes {
    public String getUserId() {
        return userId;
    }

    public String userId;
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


    public String getLolNickName() {
        return lolNickName;
    }


    public String getLolRank() {
        return lolRank;
    }


    public String getBattleNickName() {
        return battleNickName;
    }


    public String getBattleRank() {
        return battleRank;
    }


    public String getFifaNickName() {
        return fifaNickName;
    }


    public String getFifaRank() {
        return fifaRank;
    }


    public String getStarNickName() {
        return starNickName;
    }


    public String getStarRank() {
        return starRank;
    }


    public String getOverNickName() {
        return overNickName;
    }


    public String getOverwatchRank() {
        return overwatchRank;
    }


    private String overNickName;
    private String overwatchRank;
    public GetUserRes(String userId, String userName, String lolNickName, String lolRank, String battleNickName, String battleRank, String fifaNickName, String fifaRank, String starNickName, String starRank, String overNickName, String overwatchRank) {
        this.userId=userId;
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
