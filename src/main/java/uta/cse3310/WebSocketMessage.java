package uta.cse3310;
public class WebSocketMessage {
    private String type;
    private Object data;
    private int gameId;
    private String username;


    public WebSocketMessage(String type, Object data, int gameId, String username) {
        this.type = type;
        this.data = data;
        this.gameId = gameId;
        this.username = username;
    }

    // Getters and setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }
}
