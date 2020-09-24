package com.example.aigeth.ludoonline.model;

import android.content.Context;
import android.util.Log;

import com.example.aigeth.ludoonline.model.User.User;
import com.example.aigeth.ludoonline.model.User.Users;

import java.util.ArrayList;

public class Board {
    public BoardPlace[] boardPlace;
    private int turn = 10;
    private Dice dice;
    private Users users;
    private ArrayList<AnimationInterface> animations;

    private static Board theModel = null;

    private Board(Context context) {
        board();
        users = new Users();
        dice  = new Dice(0.43443848, 0.44601466, 5, context);
        animations = new ArrayList<>();
        animations.add(getDice());
    }

    public static Board getInstance(Context context){
        if (theModel == null)
            theModel = new Board(context);
        return theModel;
    }

    public final void board(){
        boardPlace = new BoardPlace[109];
        int count = 0;
        for(int i = 0; i < boardPlace.length; i++){
            boardPlace[i] = new BoardPlace(0,0);
        }

        boardPlace[count].x = 0.12915039; boardPlace[count++].y = 0.22709727;//0
        boardPlace[count].x = 0.19927979; boardPlace[count++].y = 0.2613347; //1
        boardPlace[count].x = 0.26940918; boardPlace[count++].y = 0.2955174; //2
        boardPlace[count].x = 0.3340149; boardPlace[count++].y = 0.32663736; //3
        boardPlace[count].x = 0.4215088; boardPlace[count++].y = 0.35529616; //4
        boardPlace[count].x = 0.42495728; boardPlace[count++].y = 0.27440616;//5
        boardPlace[count].x = 0.4215088; boardPlace[count++].y = 0.2090488;  //6
        boardPlace[count].x = 0.42495728; boardPlace[count++].y = 0.1381128; //7
        boardPlace[count].x = 0.42495728; boardPlace[count++].y = 0.072755426;//8
        boardPlace[count].x = 0.4978943; boardPlace[count++].y = 0.072755426;//9
        boardPlace[count].x = 0.5742798; boardPlace[count++].y = 0.072755426;//10
        boardPlace[count].x = 0.5708008; boardPlace[count++].y = 0.14062865; //11
        boardPlace[count].x = 0.5742798; boardPlace[count++].y = 0.2090488;  //12
        boardPlace[count].x = 0.5742798; boardPlace[count++].y = 0.27686733; //13
        boardPlace[count].x = 0.5770569; boardPlace[count++].y = 0.35841364; //14
        boardPlace[count].x = 0.6589966; boardPlace[count++].y = 0.3291532;  //15
        boardPlace[count].x = 0.7319031; boardPlace[count++].y = 0.29803327; //16
        boardPlace[count].x = 0.7992554; boardPlace[count++].y = 0.2663117;  //17
        boardPlace[count].x = 0.8694153; boardPlace[count++].y = 0.23207428; //18
        boardPlace[count].x = 0.90133667; boardPlace[count++].y = 0.2955174; //19
        boardPlace[count].x = 0.9395447; boardPlace[count++].y = 0.35841364; //20
        boardPlace[count].x = 0.8666382; boardPlace[count++].y = 0.3944559;  //21
        boardPlace[count].x = 0.7964783; boardPlace[count++].y = 0.42623216; //22
        boardPlace[count].x = 0.729126; boardPlace[count++].y = 0.45795372;  //23
        boardPlace[count].x = 0.729126; boardPlace[count++].y = 0.45795372;  //24
        boardPlace[count].x = 0.6527405; boardPlace[count++].y = 0.5046063;  //25
        boardPlace[count].x = 0.7263489; boardPlace[count++].y = 0.53884375; //26
        boardPlace[count].x = 0.7964783; boardPlace[count++].y = 0.57308114; //27
        boardPlace[count].x = 0.8666382; boardPlace[count++].y = 0.6042011;  //28
        boardPlace[count].x = 0.9395447; boardPlace[count++].y = 0.6383838;  //29
        boardPlace[count].x = 0.8985596; boardPlace[count++].y = 0.6987642;  //30
        boardPlace[count].x = 0.8666382; boardPlace[count++].y = 0.7665827;  //31
        boardPlace[count].x = 0.7992554; boardPlace[count++].y = 0.7354628;  //32
        boardPlace[count].x = 0.729126; boardPlace[count++].y = 0.70128006;  //33
        boardPlace[count].x = 0.6617737; boardPlace[count++].y = 0.6670426;  //34
        boardPlace[count].x = 0.5742798; boardPlace[count++].y = 0.64089966; //35
        boardPlace[count].x = 0.5708008; boardPlace[count++].y = 0.7223913;  //36
        boardPlace[count].x = 0.5708008; boardPlace[count++].y = 0.7902645;  //37
        boardPlace[count].x = 0.5742798; boardPlace[count++].y = 0.8555672;  //38
        boardPlace[count].x = 0.5742798; boardPlace[count++].y = 0.92404205; //39
        boardPlace[count].x = 0.4978943; boardPlace[count++].y = 0.92404205; //40
        boardPlace[count].x = 0.42495728; boardPlace[count++].y = 0.92404205;//41
        boardPlace[count].x = 0.42495728; boardPlace[count++].y = 0.8555672; //42
        boardPlace[count].x = 0.42495728; boardPlace[count++].y = 0.7902645; //43
        boardPlace[count].x = 0.4215088; boardPlace[count++].y = 0.7223913;  //44
        boardPlace[count].x = 0.4187317; boardPlace[count++].y = 0.6383838;  //45
        boardPlace[count].x = 0.3395691; boardPlace[count++].y = 0.6670426;  //46
        boardPlace[count].x = 0.26940918; boardPlace[count++].y = 0.70128006;//47
        boardPlace[count].x = 0.20205688; boardPlace[count++].y = 0.7330016; //48
        boardPlace[count].x = 0.1354065; boardPlace[count++].y = 0.7665827;  //49
        boardPlace[count].x = 0.09719849; boardPlace[count++].y = 0.7037412; //50
        boardPlace[count].x = 0.061798096; boardPlace[count++].y = 0.6383838;//51
        boardPlace[count].x = 0.13192749; boardPlace[count++].y = 0.6042011; //52
        boardPlace[count].x = 0.19927979; boardPlace[count++].y = 0.5705653; //53
        boardPlace[count].x = 0.26940918; boardPlace[count++].y = 0.53884375;//54
        boardPlace[count].x = 0.34857178; boardPlace[count++].y = 0.49716815;//55
        boardPlace[count].x = 0.26663208; boardPlace[count++].y = 0.45795372;//56
        boardPlace[count].x = 0.19927979; boardPlace[count++].y = 0.42623216;//57
        boardPlace[count].x = 0.13192749; boardPlace[count++].y = 0.39199474;//58
        boardPlace[count].x = 0.059020996; boardPlace[count++].y = 0.36339062;//59

        double bX[] = {0.23517, 0.29, 0.341, 0.341};
        double bY[] = {0.18, 0.21, 0.1975, 0.1238};


        double gX[] = {0.9308, 0.8603, 0.8603, 0.9308};
        double gY[] = {0.46, 0.484, 0.515, 0.538};


        //startingPlace
        boardPlace[count].x = bX[0]; boardPlace[count++].y = bY[0];
        boardPlace[count].x = bX[1]; boardPlace[count++].y = bY[1];
        boardPlace[count].x = bX[2]; boardPlace[count++].y = bY[2];
        boardPlace[count].x = bX[3]; boardPlace[count++].y = bY[3];

        boardPlace[count].x = 1- bX[0]; boardPlace[count++].y = bY[0];
        boardPlace[count].x = 1- bX[1]; boardPlace[count++].y = bY[1];
        boardPlace[count].x = 1- bX[2]; boardPlace[count++].y = bY[2];
        boardPlace[count].x = 1- bX[3]; boardPlace[count++].y = bY[3];

        boardPlace[count].x = gX[0]; boardPlace[count++].y = gY[0];
        boardPlace[count].x = gX[1]; boardPlace[count++].y = gY[1];
        boardPlace[count].x = gX[2]; boardPlace[count++].y = gY[2];
        boardPlace[count].x = gX[3]; boardPlace[count++].y = gY[3];

        boardPlace[count].x = 1- bX[0]; boardPlace[count++].y = 1- bY[0];
        boardPlace[count].x = 1- bX[1]; boardPlace[count++].y = 1- bY[1];
        boardPlace[count].x = 1- bX[2]; boardPlace[count++].y = 1- bY[2];
        boardPlace[count].x = 1- bX[3]; boardPlace[count++].y = 1- bY[3];

        boardPlace[count].x = bX[0]; boardPlace[count++].y = 1- bY[0];
        boardPlace[count].x = bX[1]; boardPlace[count++].y = 1- bY[1];
        boardPlace[count].x = bX[2]; boardPlace[count++].y = 1- bY[2];
        boardPlace[count].x = bX[3]; boardPlace[count++].y = 1- bY[3];

        boardPlace[count].x = 1- gX[0]; boardPlace[count++].y = gY[0];
        boardPlace[count].x = 1- gX[1]; boardPlace[count++].y = gY[1];
        boardPlace[count].x = 1- gX[2]; boardPlace[count++].y = gY[2];
        boardPlace[count].x = 1- gX[3]; boardPlace[count++].y = gY[3];

        //endingPlace
        boardPlace[count].x = 0.17010498; boardPlace[count++].y = 0.3291532;
        boardPlace[count].x = 0.2374878; boardPlace[count++].y = 0.36339062;
        boardPlace[count].x = 0.302063; boardPlace[count++].y = 0.39199474;
        boardPlace[count].x = 0.37496948; boardPlace[count++].y = 0.42623216;

        boardPlace[count].x = 0.4978943; boardPlace[count++].y = 0.1430898;
        boardPlace[count].x = 0.4978943; boardPlace[count++].y = 0.21156465;
        boardPlace[count].x = 0.4978943; boardPlace[count++].y = 0.27938315;
        boardPlace[count].x = 0.4951172; boardPlace[count++].y = 0.3422247;

        boardPlace[count].x = 0.8339844; boardPlace[count++].y = 0.3291532;
        boardPlace[count].x = 0.763855; boardPlace[count++].y = 0.36339062;
        boardPlace[count].x = 0.6965027; boardPlace[count++].y = 0.3944559;
        boardPlace[count].x = 0.6263428; boardPlace[count++].y = 0.4237163;

        boardPlace[count].x = 0.8284302; boardPlace[count++].y = 0.6695038;
        boardPlace[count].x = 0.7610779; boardPlace[count++].y = 0.6359227;
        boardPlace[count].x = 0.6937256; boardPlace[count++].y = 0.6042011;
        boardPlace[count].x = 0.6207886; boardPlace[count++].y = 0.57308114;

        boardPlace[count].x = 0.4978943; boardPlace[count++].y = 0.85868466;
        boardPlace[count].x = 0.4978943; boardPlace[count++].y = 0.7902645;
        boardPlace[count].x = 0.4978943; boardPlace[count++].y = 0.7193285;
        boardPlace[count].x = 0.4978943; boardPlace[count++].y = 0.65397114;

        boardPlace[count].x = 0.16732788; boardPlace[count++].y = 0.67262125;
        boardPlace[count].x = 0.2374878; boardPlace[count++].y = 0.6383838;
        boardPlace[count].x = 0.3048401; boardPlace[count++].y = 0.6066623;
        boardPlace[count].x = 0.37496948; boardPlace[count++].y = 0.5705653;

        boardPlace[count].x = 0.4978943; boardPlace[count++].y = 0.49962932;

    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public boolean isMyTurn(){
        return users.getMyUser().getColor() == turn;
    }

    public Dice getDice() {
        return dice;
    }

    public ArrayList<User> getAllUsers() {
        return users.getUsers();
    }

    public void clearUsers(){
        users.clear();
        animations.clear();
        animations.add(getDice());
    }

    public void addUser(User user){
        users.addUser(user);
        animations.addAll(user.getPieces());
        Log.d("Board", "addUser: " + animations.size() + " : " + user.getPieces().size());
    }

    public ArrayList<AnimationInterface> getAnimations() {
        return animations;
    }

    public Users getUsers() {
        return users;
    }
}
