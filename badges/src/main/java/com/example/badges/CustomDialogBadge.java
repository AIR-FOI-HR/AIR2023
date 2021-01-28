package com.example.badges;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.database.Badge;
import com.example.database.User;

public class CustomDialogBadge implements DataPresenter{
        Context context;
        String badgeUri;
        BadgesRepository badgesRepository=new BadgesRepository();
        User user;
        BadgesData badge;
    /*
    public CustomDialogBadge(Context context, String badgeUri) {
        this.context = context;
        this.badgeUri=badgeUri;
    }
    */

    public CustomDialogBadge() {
    }

    private void PokaziNagradu(){

        final Dialog MyDialog = new Dialog(context);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.customdialog);
        ImageView image=(ImageView)MyDialog.findViewById(R.id.BadgeImage);

        Log.d("Badges","Nest "+badgeUri.toString());

        Glide.with(context)
                .asBitmap()
                .load(badgeUri)
                .into(image);


        Button downloadBadge = (Button)MyDialog.findViewById(R.id.downloadBadge);

        downloadBadge.setEnabled(true);

        downloadBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog.cancel();
            }
        });
        MyDialog.show();
    }

    @Override
    public void setData(User user, BadgesData badge) {
        this.user=user;
        this.badge=badge;
        this.badgeUri=badge.getBadgeURL();
    }

    @Override
    public void setContexPrikazivanja(Context context) {
        this.context=context;
    }

    @Override
    public void izvrsiUpdateKupca() {
        badgesRepository.DodijeliZnackuKupcu(user,badge);
    }

    @Override
    public void izvrsiUpdatePonuditelja() {
        badgesRepository.DodijeliZnackuProdavatelju(user,badge);
    }

    @Override
    public void prikaziNagraduKorisniku() {
        PokaziNagradu();
    }
}
