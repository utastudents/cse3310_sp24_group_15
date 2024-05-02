

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
 import java.util.Arrays;

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
   ArrayList<String> qPlayerList = new ArrayList<String>();
 
   int GameId = 1;
   int score11 = 0;
 
   public String version = System.getenv("VERSION");

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
 
     //System.out.println("version is: " + version);
     System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " connected");
 
     WebSocketMessage versionSend = new WebSocketMessage("versionSend", version, 0, "none");

     Gson gsonVersion = new Gson();
     String jsonVersion = gsonVersion.toJson(versionSend);
     conn.send(jsonVersion);
     //System.out.println(jsonVersion);

 
 
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
     //System.out.println(conn + ": " + message);
 
 
     // Bring in the data from the webpage
     // A UserEvent is all that is allowed at this point
     GsonBuilder builder = new GsonBuilder();
     Gson gson = builder.create();
     UserEvent U = gson.fromJson(message, UserEvent.class);
     //System.out.println(U.Button);
     String eventType = U.type;

     Gson gson4 = new Gson();

     //RUNS FUNCTION BASED ON MESSAGE TYPE RECEIVED
     if(eventType.equals("username")){
        //System.out.println("Username Received: " + U.userName);
        //add functionality for adding username to server's waiting player list
        playerList.add(U.userName);
        WebSocketMessage usernames = new WebSocketMessage("userNames", playerList, 0, "none");
        Gson gsonNames = new Gson();
        String jsonUsername = gsonNames.toJson(usernames);
        broadcast(jsonUsername);
        //System.out.println(jsonUsername);
      
     }
     if(eventType.equals("wordHighlight")){
        
        //System.out.println("highlight Received: " + U.score1);
        //System.out.println("highlight Received: " + U.score2);
        //System.out.println("g Received: " + U.GameId);

        WebSocketMessage Playerscores = new WebSocketMessage("score1", U.score1, U.GameId, "none");
        Gson gsonNames1 = new Gson();
        String jsonPlayerscores = gsonNames1.toJson(Playerscores);
        broadcast(jsonPlayerscores);
        //System.out.println(jsonPlayerscores);
        WebSocketMessage Playerscores2 = new WebSocketMessage("score2", U.score2, U.GameId, "none");
    
        String jsonPlayerscores2 = gsonNames1.toJson(Playerscores2);
        broadcast(jsonPlayerscores2);
        //System.out.println(jsonPlayerscores2);
     }

     if(eventType.equals("removeUsername")){
        //System.out.println("Username Received for Removal: " + U.userName);
        playerList.remove(U.userName);
        WebSocketMessage usernames = new WebSocketMessage("userNames", playerList, 0, "none");
        Gson gsonNames = new Gson();
        String jsonUsername = gsonNames.toJson(usernames);
        broadcast(jsonUsername);
        //System.out.println(jsonUsername);
     }

     if(eventType.equals("removeWord")){
        //System.out.println("Word for Removal: " + U.wordHighlighted);
        ArrayList<String> receivedWords = new ArrayList();
        receivedWords = U.wordList;
        receivedWords.remove(U.wordHighlighted);

        WebSocketMessage wordListMessage = new WebSocketMessage("WordList", receivedWords, U.GameId, "none");
        String jsonWordList = gson4.toJson(wordListMessage);
        broadcast(jsonWordList);
        //System.out.println(jsonWordList);

     }

     if(eventType.equals("colorUpdate")){
        //print for debugging if received message
        //System.out.println("color Updating" + U.buttonRow + " : " + U.buttonCol);
        ArrayList<Integer> buttonArr = new ArrayList();
        buttonArr.add(U.buttonRow);
        buttonArr.add(U.buttonCol);

        //using username arg to send color since both are strings
        WebSocketMessage colorUpdateMessage = new WebSocketMessage("colorUpdate", buttonArr, U.GameId, U.wordHighlighted);
        String jsonColorUpdate = gson4.toJson(colorUpdateMessage);
        broadcast(jsonColorUpdate);
        //System.out.println(jsonColorUpdate);

     }

     if(eventType.equals("gameStart")){

        //System.out.println("Checking to see if we can Start Game");
        qPlayerList.add(U.userName);
        int qPlayerListSize = qPlayerList.size();
        //System.out.println(qPlayerListSize + " : Players Waiting");

        WebSocketMessage waiting = new WebSocketMessage("waiting", "waiting", 0, qPlayerList.get(0));
        String jsonWait = gson4.toJson(waiting);
        broadcast(jsonWait);
        //System.out.println(jsonWait);


        if(qPlayerListSize == 2){

          //System.out.println("Starting Game");
          //System.out.println("player blue is: " + qPlayerList.get(0));
          //System.out.println("Player red is: " + qPlayerList.get(1));

          //get two players from list and pull them out of qPlayerList
          String bluePlayer = qPlayerList.get(0);
          String redPlayer = qPlayerList.get(1);
          qPlayerList.remove(bluePlayer);
          qPlayerList.remove(redPlayer);

          //start game new game and increment id
          Game G = new Game();
          G.GameId = GameId;
          GameId++;

          //set blue player in game instance
          G.bluePlayer = bluePlayer;

          //send color to js for blue
          WebSocketMessage blueSet = new WebSocketMessage("giveColor", "blue", GameId, bluePlayer);
          String jsonBlue = gson4.toJson(blueSet);
          broadcast(jsonBlue);
          //System.out.println(jsonBlue);

          WebSocketMessage bluePlayerSet = new WebSocketMessage("givePlayers", redPlayer, GameId, "blue");
          String jsonBluePlayer = gson4.toJson(bluePlayerSet);
          broadcast(jsonBluePlayer);
          //System.out.println(jsonBluePlayer);


          //set red player in game instance
          G.redPlayer = redPlayer;

          //send color to js for red
          WebSocketMessage redSet = new WebSocketMessage("giveColor", "red", GameId, redPlayer);
          String jsonRed = gson4.toJson(redSet);
          broadcast(jsonRed);
          //System.out.println(jsonRed);

          WebSocketMessage redPlayerSet = new WebSocketMessage("givePlayers", bluePlayer, GameId, "red");
          String jsonRedPlayer = gson4.toJson(redPlayerSet);
          broadcast(jsonRedPlayer);
          //System.out.println(jsonRedPlayer);

          ActiveGames.add(G);
          //System.out.println("Starting Game");

          G.initializeGrid();
          G.populateGridWithWords();
          char[][] arr = G.getGrid();
          Set<String> uniqueWords = G.uniqueWords;

          String[] usedWords = new String[uniqueWords.size()];

          uniqueWords.toArray(usedWords);

          WebSocketMessage gridMessage = new WebSocketMessage("grid", arr, GameId, "none");
          String jsonGrid = gson4.toJson(gridMessage);
          broadcast(jsonGrid);

          WebSocketMessage wordListMessage = new WebSocketMessage("WordList", usedWords, GameId, "none");
          String jsonWordList = gson4.toJson(wordListMessage);
          broadcast(jsonWordList);
          //System.out.println(jsonWordList);
        
        }
        
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
 
 
 
 
 
 