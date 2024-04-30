

// This is example code provided to CSE3310 Fall 2022
// You are free to use as is, or changed, any of the code provided


// Please comply with the licensing requirements for the
// open source packages being used.


// This code is based upon, and derived from the this repository
//            https:/thub.com/TooTallNate/Java-WebSocket/tree/master/src/main/example


// http server include is a GPL licensed package from
//            http://www.freeutils.net/source/jlhttp/


/*
 * Copyright (c) 2010-2020 Nathan Rajlich
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without
 *  restriction, including without limitation the rights to use,
 *  copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following
 *  conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 *  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *  OTHER DEALINGS IN THE SOFTWARE.
 */


 package uta.cse3310;


 import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.InputStreamReader;
 import java.net.InetSocketAddress;
 import java.net.UnknownHostException;
 import java.nio.ByteBuffer;
 import java.util.Collections;
 import java.util.Set;
 import java.util.ArrayList;

import org.java_websocket.WebSocket;
 import org.java_websocket.drafts.Draft;
 import org.java_websocket.drafts.Draft_6455;
 import org.java_websocket.handshake.ClientHandshake;
 import org.java_websocket.server.WebSocketServer;
 import java.util.Timer;
 import java.util.TimerTask;
 import java.util.Vector;
 
 
 import com.google.gson.Gson;
 import com.google.gson.GsonBuilder;
 
 
 public class App extends WebSocketServer {
   // All games currently underway on this server are stored in
   // the vector ActiveGames
   Vector<Game> ActiveGames = new Vector<Game>();
   ArrayList<String> playerList = new ArrayList<String>();
 
 
   int GameId = 1;
 
 
   public App(int port) {
     super(new InetSocketAddress(port));
   }
 
 
   public App(InetSocketAddress address) {
     super(address);
   }
 
 
   public App(int port, Draft_6455 draft) {
     super(new InetSocketAddress(port), Collections.<Draft>singletonList(draft));
   }
   public int getConcurrentGames() {
     return ActiveGames.size();
 }
 
 
   @Override
   public void onOpen(WebSocket conn, ClientHandshake handshake) {
 
 
     System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " connected");
 
 
     ServerEvent E = new ServerEvent();
 
     //int[] grid= {1,2,4};
     //String name = "jimmy";  
     Game game = new Game();
        game.initializeGrid();
        game.populateGridWithWords();
        char[][] arr = game.getGrid();
        // Assuming game.uniqueWords is a Set<String>
        Set<String> uniqueWords = game.uniqueWords;

        // Create a String array of the same size as the set
        String[] usedWords = new String[uniqueWords.size()];

        // Convert the set to an array
        uniqueWords.toArray(usedWords);

        game.PrintGame(0);
     WebSocketMessage message = new WebSocketMessage("grid", arr);
     WebSocketMessage message2 = new WebSocketMessage("WordList", usedWords);
     WebSocketMessage message1 = new WebSocketMessage("userNames", playerList);
     // Convert the message object to JSON string using Gson
     Gson gson3 = new Gson();
     String jsonString2 = gson3.toJson(message);
     String jsonString1 = gson3.toJson(message1);
     String jsonString3 = gson3.toJson(message2);
 
     // Send the JSON string through the WebSocket connection
     conn.send(jsonString2);
     conn.send(jsonString1);
     conn.send(jsonString3);
     // search for a game needing a player
     Game G = null;
     for (Game i : ActiveGames) {
       if (i.Players == uta.cse3310.PlayerType.redPLAYER) {
         G = i;
         System.out.println("found a match");
       }
     }
 
 
     // No matches ? Create a new Game.
     if (G == null) {
       G = new Game();
       G.GameId = GameId;
       GameId++;
       // Add the first player
       G.Players = uta.cse3310.PlayerType.redPLAYER;
       ActiveGames.add(G);
       System.out.println(" creating a new Game");
     } else {
       // join an existing game
       System.out.println(" not a new game");
       G.Players = uta.cse3310.PlayerType.bluePLAYER;
       G.StartGame();
     }
     System.out.println("G.players is " + G.Players);
     // create an event to go to only the new player
     E.YouAre = G.Players;
     E.GameId = G.GameId;
     // allows the websocket to give us the Game when a message arrives
     conn.setAttachment(G);
 
 
     Gson gson = new Gson();
     // Note only send to the single connection
     conn.send(gson.toJson(E));
     System.out.println(gson.toJson(E));
 
 
     // The state of the game has changed, so lets send it to everyone
     String jsonString;
     jsonString = gson.toJson(G);
 
 
     System.out.println(jsonString);
     broadcast(jsonString);
 
 
   }
 
 
   @Override
   public void onClose(WebSocket conn, int code, String reason, boolean remote) {
     System.out.println(conn + " has closed");
     // Retrieve the game tied to the websocket connection
     Game G = conn.getAttachment();
     G = null;
   }
 
 
   @Override
   public void onMessage(WebSocket conn, String message) {
     System.out.println(conn + ": " + message);
 
 
     // Bring in the data from the webpage
     // A UserEvent is all that is allowed at this point
     GsonBuilder builder = new GsonBuilder();
     Gson gson = builder.create();
     UserEvent U = gson.fromJson(message, UserEvent.class);
     System.out.println(U.Button);
     String eventType = U.type;



     //RUNS FUNCTION BASED ON MESSAGE TYPE RECEIVED
     if(eventType.equals("username")){
        System.out.println("Username Received: " + U.userName);
        //add functionality for adding username to server's waiting player list
        playerList.add(U.userName);
        WebSocketMessage usernames = new WebSocketMessage("userNames", playerList);
        Gson gsonNames = new Gson();
        String jsonUsername = gsonNames.toJson(usernames);
        broadcast(jsonUsername);
        System.out.println(jsonUsername);
      
     }

     if(eventType.equals("removeUsername")){
        System.out.println("Username Received for Removal: " + U.userName);
        playerList.remove(U.userName);
        WebSocketMessage usernames = new WebSocketMessage("userNames", playerList);
        Gson gsonNames = new Gson();
        String jsonUsername = gsonNames.toJson(usernames);
        broadcast(jsonUsername);
        System.out.println(jsonUsername);
     }

     if(eventType.equals("gameStart")){
        System.out.println("Starting Game");
        //move functionality here for game start
     }
 
     if(eventType.equals("wordHighlight")){
        System.out.println("highlight Received: " + U.wordHighlighted);
        System.out.println("startrow Received: " + U.buttonRow);
        System.out.println("Endrow Received: " + U.buttonRowE);
        System.out.println("startcol Received: " + U.buttonCol);
        System.out.println("Endcol Received: " + U.buttonColE);
     }
 
     // Get our Game Object
    
 
 
     // send out the game state every time
     // to everyone
     
   }
  
 
 
 
 
   @Override
   public void onError(WebSocket conn, Exception ex) {
     ex.printStackTrace();
     if (conn != null) {
       // some errors like port binding failed may not be assignable to a specific
       // websocket
     }
   }
 
 
   @Override
   public void onStart() {
     System.out.println("Server started!");
     setConnectionLostTimeout(0);
   }
 
 
   public static void main(String[] args) {
 
 
     // Set up the http server
     int port = 9015;
     HttpServer H = new HttpServer(port, "./html");
     H.start();
     System.out.println("http Server started on port:" + port);
 
 
     // create and start the websocket server
 
 
     port = 9115;
     App A = new App(port);
     A.start();
     System.out.println("websocket Server started on port: " + port);
 
 
   }
 }
 
 
 
 
 
 