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

public class CustomDialogBadge {
        Context context;
        String badgeUri;
    public CustomDialogBadge(Context context, String badgeUri) {
        this.context = context;
        this.badgeUri=badgeUri;
    }

    public void PokaziNagradu(){

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

}
