package com.example.aigeth.ludoonline.model.User;

import java.util.ArrayList;

public class Users {
    private ArrayList<User> users;
    private User myUser = null;


    public Users() {
        users = new ArrayList<>();}

    public synchronized User getUserById(int id){
        for(User user : users){
            if(user.getId() == id){
                return user;
            }
        }
        return null;
    }

    public synchronized User getUserByColor(int color){
        for(User user : users){
            if(user.getColor() == color){
                return user;
            }
        }
        return null;
    }

    public void setMyUser(int color){
        for(User user : users){
            if (user.getColor() == color){
                myUser = user;
                return;
            }
        }
    }

    public void addUser(User user){
        users.add(user);
    }

    public void removeUser(User user){
        users.remove(user);
    }

    public User get(int index) {
        return users.get(index);
    }

    public ArrayList<User> getUsers(){
        return users;
    }

    public int size(){
        return users.size();
    }

    public void clear(){
        users.clear();
    }

    public User getMyUser() {
        return myUser;
    }


}
