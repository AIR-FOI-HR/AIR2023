package com.example.badges;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.database.User;

import org.w3c.dom.Text;

import java.util.Locale;

public class CustomDialogBadgeQuiz implements DataPresenter {
    Context context;
    String badgeUri;
    BadgesRepository badgesRepository = new BadgesRepository();
    User user;
    BadgesData badge;
    QuizData quizData;
    String questionNumberString;

    public CustomDialogBadgeQuiz(){};

    @SuppressLint("SetTextI18n")
    private void prikaziDialog(){
        final Dialog MyDialog = new Dialog(context);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.customdialogquiz);
        ImageView image=(ImageView)MyDialog.findViewById(R.id.quizImage);
        RadioButton radioButtonAnswer1 = (RadioButton)MyDialog.findViewById(R.id.answer1);
        RadioButton radioButtonAnswer2 = (RadioButton)MyDialog.findViewById(R.id.answer2);
        RadioButton radioButtonAnswer3 = (RadioButton)MyDialog.findViewById(R.id.answer3);

        TextView questionNumber = (TextView)MyDialog.findViewById(R.id.question_number);
        TextView question = (TextView)MyDialog.findViewById(R.id.question);
        TextView nextQuestion = (TextView)MyDialog.findViewById(R.id.next_question);

        Button finishQuiz = (Button)MyDialog.findViewById(R.id.finish_quiz);

        //Log.d("Badges","Nest "+badgeUri.toString());

        /*Glide.with(context)
                .asBitmap()
                .load(badgeUri)
                .into(image);*/

        if(!Locale.getDefault().getDisplayLanguage().equals("English")) {

            questionNumber.setText("Pitanje " + questionNumberString);
            question.setText(quizData.getQuestion());

            radioButtonAnswer1.setText(quizData.getAnswer1());
            radioButtonAnswer2.setText(quizData.getAnswer2());
            radioButtonAnswer3.setText(quizData.getAnswer3());
            nextQuestion.setText("DALJE");
            finishQuiz.setText("ZAVRŠI");
        }
        else{
            questionNumber.setText("Question " + questionNumberString);
            question.setText(quizData.getQuestioneng());

            radioButtonAnswer1.setText(quizData.getAnswer1eng());
            radioButtonAnswer2.setText(quizData.getAnswer2eng());
            radioButtonAnswer3.setText(quizData.getAnswer3eng());
            nextQuestion.setText("NEXT");
            finishQuiz.setText("FINISH");
        }

        /*Button downloadBadge = (Button)MyDialog.findViewById(R.id.downloadBadge);

        downloadBadge.setEnabled(true);

        downloadBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog.cancel();
            }
        });*/
        MyDialog.show();
    };

    @Override
    public void setData(User user, BadgesData badge) {
        this.user=user;
        this.badge=badge;
        this.badgeUri=badge.getBadgeURL();
    }

    public void setQuestion(QuizData quizData, String questionNumber){
        this.quizData=quizData;
        this.questionNumberString=questionNumber;
    }

    @Override
    public void setContexPrikazivanja(Context context) {
        this.context=context;
    }

    @Override
    public void izvrsiUpdateKupca() {
        badgesRepository.DodijeliZnackuKorisniku(user,badge);
    }

    @Override
    public void izvrsiUpdatePonuditelja() {

    }

    @Override
    public void prikaziDialogKorisniku() {
        prikaziDialog();
    }
}
