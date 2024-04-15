package uta.cse3310;
public class WebSocketMessage {
    private String type;
    private Object data;

    public WebSocketMessage(String type, Object data) {
        this.type = type;
        this.data = data;
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
}
/*int[] grid= {1,2,4};
    String name = "jimmy";  
    WebSocketMessage message = new WebSocketMessage("grid", grid);
    WebSocketMessage message1 = new WebSocketMessage("waitingPlayers", name);
    // Convert the message object to JSON string using Gson
    Gson gson3 = new Gson();
    String jsonString2 = gson3.toJson(message);
    String jsonString1 = gson3.toJson(message1);

    // Send the JSON string through the WebSocket connection
    conn.send(jsonString2);
    conn.send(jsonString1);
      
    HTML SCRIPT
    socket.onmessage = function(event) {
    let message = JSON.parse(event.data);
    
    // Check the type of message
    if (message.type === "grid") {
        // Handle array data
        let receivedArray = message.data;
        console.log("array recieved");
        // Set the inner text of the h1 element to the array data
        document.querySelector("h1").innerText = "The WordSearchGame: " + receivedArray.join(", ");
        
    }
    if (message.type === "waitingPlayers") {
        // Handle waiting players data
        console.log("names");
        let players = message.data;
        displayWaitingPlayers(players);
    }
};
    
    */
