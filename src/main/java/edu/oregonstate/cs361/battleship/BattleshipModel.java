package edu.oregonstate.cs361.battleship;

import java.util.ArrayList;

/**
 * Created by michaelhilton on 1/4/17.
 */
public class BattleshipModel {

    private Ship aircraftCarrier = new Ship("AircraftCarrier",5, new Coordinate(0,0),new Coordinate(0,0));
    private StealthShip battleship = new StealthShip("Battleship",4, new Coordinate(0,0),new Coordinate(0,0));
    private StealthShip submarine = new StealthShip("Submarine",3, new Coordinate(0,0),new Coordinate(0,0));
    private Civilian clipper = new Civilian("Clipper", 3, new Coordinate(0, 0), new Coordinate(0, 0));
    private Civilian dinghy = new Civilian("Dinghy", 1, new Coordinate(0,0), new Coordinate(0, 0));

    private Ship computer_aircraftCarrier = new Ship("Computer_AircraftCarrier",5, new Coordinate(2,2),new Coordinate(2,6));
    private StealthShip computer_battleship = new StealthShip("Computer_Battleship",4, new Coordinate(2,8),new Coordinate(5,8));
    private StealthShip computer_submarine = new StealthShip("Computer_Submarine",3, new Coordinate(9,6),new Coordinate(9,8));
    private Civilian computer_clipper = new Civilian("Computer_Clipper", 3, new Coordinate(5, 1), new Coordinate(5, 3));
    private Civilian computer_dinghy = new Civilian("Computer_Dinghy", 1, new Coordinate(1,1), new Coordinate(1, 1));

    public ArrayList<Coordinate> playerHits;
    public ArrayList<Coordinate> playerMisses;
    public ArrayList<Coordinate> computerHits;
    public ArrayList<Coordinate> computerMisses;

    public boolean scan_result;
    public String error_message;
    //playerHitpoints to count how many hits player has taken, when it equals to 14, computer wins
    //computerHitpoints to count how many hits AI has taken, when it equals to 14, player wins
    public int playershipsank = 0;
    public int computershipsank = 0;
    public int rowShoot = 0;
    public int colShoot = 1;
    public String AI_win = "You lose...T_T";
    public String Player_win = "You WIN!!! ^_^";


    public BattleshipModel() {
        playerHits = new ArrayList<>();
        playerMisses= new ArrayList<>();
        computerHits = new ArrayList<>();
        computerMisses= new ArrayList<>();
    }

    public static BattleshipModel ofStatus(String statusStr) {
        System.out.println("STRING");
        return null;
    }

    public Ship getShip(String shipName) {
        if (shipName.equalsIgnoreCase("aircraftcarrier")) {
            return aircraftCarrier;
        } if(shipName.equalsIgnoreCase("battleship")) {
            return battleship;
        } if(shipName.equalsIgnoreCase("clipper")) {
            return clipper;
        } if(shipName.equalsIgnoreCase("dinghy")) {
            return dinghy;
        }if(shipName.equalsIgnoreCase("submarine")) {
            return submarine;
        } else {
            return null;
        }
    }

    public BattleshipModel placeShip(String shipName, String row, String col, String orientation, BattleshipModel currModel) {
        int rowint = Integer.parseInt(row);
        int colInt = Integer.parseInt(col);
        if(orientation.equals("horizontal")){
            if (shipName.equalsIgnoreCase("aircraftcarrier")) {
                currModel.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint,colInt+4));
            } if(shipName.equalsIgnoreCase("battleship")) {
                currModel.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint,colInt+3));
            } if(shipName.equalsIgnoreCase("clipper")) {
                currModel.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint,colInt+2));
            } if(shipName.equalsIgnoreCase("dinghy")) {
                currModel.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint,colInt));
            }if(shipName.equalsIgnoreCase("submarine")) {
                currModel.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint,colInt+2));
            }
        }else{
            //vertical
                if (shipName.equalsIgnoreCase("aircraftcarrier")) {
                    currModel.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint+4,colInt));
                } if(shipName.equalsIgnoreCase("battleship")) {
                    currModel.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint+3,colInt));
                } if(shipName.equalsIgnoreCase("clipper")) {
                    currModel.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint+2,colInt));
                } if(shipName.equalsIgnoreCase("dinghy")) {
                    currModel.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint,colInt));
                }if(shipName.equalsIgnoreCase("submarine")) {
                    currModel.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint+2,colInt));
                }
        }
        return currModel;
    }

    public void shootAtComputer(int row, int col) {
        Coordinate coor = new Coordinate(row,col);
        if(computer_aircraftCarrier.covers(coor)){
            computerHits.add(coor);
            computer_aircraftCarrier.health -= 1;
            checkcomputerhealth(computer_aircraftCarrier.health);
        }else if (computer_battleship.covers(coor)){
            computerHits.add(coor);
            computer_battleship.health -= 1;
            checkcomputerhealth(computer_battleship.health);
        }else if (computer_clipper.covers(coor)){
            computerHits.add(coor);
            computer_clipper.health -= 1;
            checkcomputerhealth(computer_clipper.health);
        }else if (computer_dinghy.covers(coor)){
            computerHits.add(coor);
            computer_dinghy.health -= 1;
            checkcomputerhealth(computer_dinghy.health);
        }else if (computer_submarine.covers(coor)){
            computerHits.add(coor);
            computer_submarine.health -= 1;
            checkcomputerhealth(computer_submarine.health);
        } else {
            computerMisses.add(coor);
        }
    }

    public void shootAtPlayer() {
        int max = 10;
        int min = 1;
        if(rowShoot < 10) {
            rowShoot += 1;
        }
        else if(rowShoot == max) {
            rowShoot = min;
            colShoot += 2;
            if(colShoot > max)
            {
                colShoot = 2;
            }
        }


        Coordinate coor = new Coordinate(rowShoot,colShoot);
        if(playerMisses.contains(coor)){
            System.out.println("Dupe");
        }


        if(aircraftCarrier.covers(coor)){
            playerHits.add(coor);
            aircraftCarrier.health -= 1;
            checkplayerhealth(aircraftCarrier.health);
        }else if (battleship.covers(coor)){
            playerHits.add(coor);
            battleship.health -= 1;
            checkplayerhealth(battleship.health);
        }else if (clipper.covers(coor)){
            playerHits.add(coor);
            clipper.health -= 1;
            checkplayerhealth(clipper.health);
        }else if (dinghy.covers(coor)){
            playerHits.add(coor);
            dinghy.health -= 1;
            checkplayerhealth(dinghy.health);
        }else if (submarine.covers(coor)){
            playerHits.add(coor);
            submarine.health -= 1;
            checkplayerhealth(submarine.health);
        } else {
            playerMisses.add(coor);
        }
    }

    public void checkplayerhealth(int health){
        if(health == 0){
            playershipsank += 1;
        }else{}
    }

    public void checkcomputerhealth(int health){
        if(health == 0){
            computershipsank += 1;
        }else{}
    }


    public boolean checkfirepoint(int row, int col){
        int hitsize = computerHits.size();
        int missize = computerMisses.size();

        int i = 0;
        int j = 0;
        while( i < hitsize) {
            Coordinate z = computerHits.get(i);
            int xhit = z.Across;
            int yhit = z.Down;
            if (row == xhit && col == yhit){
                return true;
            }else{
                i += 1;
            }
        }
        while( j < missize){
            Coordinate m = computerMisses.get(j);
            int xmiss = m.Across;
            int ymiss = m.Down;
            if(row == xmiss && col == ymiss){
                return true;
            }else{
                j += 1;
            }
        }
        return false;
    }

    public boolean scanPlayer(int row, int col ) {
        Coordinate coor = new Coordinate(row,col);
        if((computer_aircraftCarrier.covers(coor)) && (computer_aircraftCarrier.getStealth()==false)){
            return true;
        }else if ((computer_battleship.covers(coor))&& (computer_battleship.getStealth()==true)){
            return false;
        }else if ((computer_clipper.covers(coor)) && (computer_clipper.getStealth()==false)){
            return true;
        }else if ((computer_dinghy.covers(coor)) && (computer_dinghy.getStealth()==false)){
            return true;
        }else if ((computer_submarine.covers(coor))  && (computer_submarine.getStealth()==true)){
            return false;
        } else {
            return false;
        }
    }
}