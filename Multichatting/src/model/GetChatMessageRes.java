package model;

public class GetChatMessageRes {
    private String name;
    private String message;
    public GetChatMessageRes(String name, String message) {
        this.name = name;
        this.message = message;

    }
    public String getName() {
        return name;
    }


    public String getMessage() {
        return message;
    }


}
