package com.example.badges;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.database.User;

import java.lang.annotation.Repeatable;
import java.util.ArrayList;
import java.util.List;

public class Logic {

    BadgesRepository badgesRepository = new BadgesRepository();
    private User user;
    ArrayList<BadgeID> badgesIDList;
    ArrayList<BadgesData> badgesList;
    private Context reservationFragmentContext;
    private String category;
    private Integer BRKUPNJI = 0;
    private Integer BRPRODAJA = 0;
    private Integer BRODG;
    private Integer BRPITANJA;
    private Integer POSTOJI = 0;
    private float OCJENA;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Logic(User user, ArrayList<BadgesData> badges, ArrayList<BadgeID> badgesID, Context reservationFragmentContext, String category) {
        this.user = user;
        this.badgesList = badges;
        this.badgesIDList = badgesID;
        this.reservationFragmentContext = reservationFragmentContext;
        this.category = category;
        InicijalizirajVarijabe();
        postaviPOSTOJI(badges, badgesID, category);
    }


    private void InicijalizirajVarijabe(){
      BRKUPNJI = user.getNumberOfPurchases();
      BRPRODAJA = user.getNumberOfSales();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String PretvoriUPostfix(String condition, BadgesData badge){
        condition = ZamijeniVarijable(condition);
        String[] conditionArray = condition.split(" ");
        //List<String> output = InfixToPostfix.infixToRPN(conditionArray);
        //Log.d("TagPolje", output.toString());

        String stringCondition = String.join(" ", conditionArray);
        ProvjeriIstinitost(stringCondition, badge);

        return stringCondition;
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

    public Boolean ProvjeriIstinitost(String condition, BadgesData badge){
        Log.d("TagPolje", condition);
        Log.d("TagPolje",  String.valueOf(CalculatePostfix.evaluatePostfix(condition)));
        Integer result = (CalculatePostfix.evaluatePostfix(condition));
        if(result==1){
            CustomDialogBadge customDialogBadge=new CustomDialogBadge();
            customDialogBadge.setContexPrikazivanja(reservationFragmentContext);
            customDialogBadge.setData(user,badge);
            customDialogBadge.izvrsiUpdateKorisnika();
            customDialogBadge.prikaziDialogKorisniku();
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void postaviPOSTOJI(ArrayList<BadgesData> badges, ArrayList<BadgeID> badgesID, String category) {
        boolean exists;
        for (int i = 0; i < badges.size(); i++) {
            exists = true;
            for (int j = 0; j < badgesID.size(); j++) {
                if ((!(badges.get(i).getBadgeID().equals(badgesID.get(j).getId()))) && badges.get(i).getCategory().equals(category)) {
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
                PretvoriUPostfix(condition, badges.get(i));
            }
        }
    }

}
