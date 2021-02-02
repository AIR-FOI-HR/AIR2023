package com.example.badges;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.database.User;

import java.lang.annotation.Repeatable;
import java.util.ArrayList;
import java.util.List;

public class Logic {

    BadgesRepository badgesRepository = new BadgesRepository();
    private User user;
    ArrayList<BadgeID> badgesIDList;
    ArrayList<BadgesData> badgesList;
    private Integer BRKUPNJI = 0;
    private Integer BRPRODAJA = 0;
    private Integer BRODG;
    private Integer BRPITANJA;
    private Integer POSTOJI = 0;
    private float OCJENA;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Logic(User user, ArrayList<BadgesData> badges, ArrayList<BadgeID> badgesID) {
        this.user = user;
        this.badgesList = badges;
        this.badgesIDList = badgesID;
        InicijalizirajVarijabe();
        postaviPOSTOJI(badges, badgesID);
    }


    private void InicijalizirajVarijabe(){
      BRKUPNJI = user.getNumberOfPurchases();
      BRPRODAJA = user.getNumberOfSales();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String PretvoriUPostfix(String condition){
        condition = ZamijeniVarijable(condition);
        String[] conditionArray = condition.split(" ");
        List<String> output = InfixToPostfix.infixToRPN(conditionArray);
        Log.d("TagPolje", output.toString());


        String stringCondition = String.join(" ", output);
        ProvjeriIstinitost(stringCondition);

        return output.toString();
    }

    private String ZamijeniVarijable(String condition) {
        if(condition.contains("BRKUPNJI")){
            condition = condition.replace("BRKUPNJI", BRKUPNJI.toString());
        }
        if(condition.contains("BRPRODAJA")){
            condition = condition.replace("BRPRODAJA", BRPRODAJA.toString());
        }
        if(condition.contains("==")){
            condition = condition.replace("==","=");
        }
        if(condition.contains("||")){
            condition = condition.replace("||", "|");
        }
        if(condition.contains("&&")){
            condition = condition.replace("&&", "&");
        }
        return condition;
    }

    public Boolean ProvjeriIstinitost(String condition){
        Log.d("TagPolje", condition);
        CalculatePostfix.evaluatePostfix( "100 200 + 2 / 5 * 7 +");
        Log.d("TagPolje",  String.valueOf(CalculatePostfix.evaluatePostfix( "100 200 + 2 / 5 * 7 +")));
        //CalculatePostfix.evaluatePostfix(condition);
        //Log.d("TagPolje",  String.valueOf(CalculatePostfix.evaluatePostfix(condition)));
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void postaviPOSTOJI(ArrayList<BadgesData> badges, ArrayList<BadgeID> badgesID) {
        boolean exists;
        for (int i = 0; i < badges.size(); i++) {
            exists = true;
            for (int j = 0; j < badgesID.size(); j++) {
                if ((!(badges.get(i).getBadgeID().equals(badgesID.get(j).getId()))) && badges.get(i).getCategory().equals("seller")) {
                    POSTOJI = 0;
                    exists = false;

                }else{
                    POSTOJI = 1;
                    exists = true;
                    break;
                }
            }

            if(!exists){
                String condition = badges.get(i).getCondition();
                if(condition.contains("POSTOJI")){
                    condition = condition.replace("POSTOJI", POSTOJI.toString());
                }
                PretvoriUPostfix(condition);
            }
        }
    }

}
