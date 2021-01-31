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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.database.User;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class CustomDialogBadgeQuiz implements DataPresenter {
    Context context;
    String badgeUri;
    BadgesRepository badgesRepository = new BadgesRepository();
    User user;
    BadgesData badge;
    ArrayList<QuizData> quizDataArrayList;
    Integer questionNumberInteger = 0;
    Integer correctAnswers = 0;

    RadioButton radioButtonAnswer1;
    RadioButton radioButtonAnswer2;
    RadioButton radioButtonAnswer3;

    public CustomDialogBadgeQuiz(){};

    @SuppressLint("SetTextI18n")
    private void prikaziDialog(){
        final Dialog MyDialog = new Dialog(context);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.customdialogquiz);
        ImageView image=(ImageView)MyDialog.findViewById(R.id.quizImage);
        radioButtonAnswer1 = (RadioButton)MyDialog.findViewById(R.id.answer1);
        radioButtonAnswer2 = (RadioButton)MyDialog.findViewById(R.id.answer2);
        radioButtonAnswer3 = (RadioButton)MyDialog.findViewById(R.id.answer3);

        TextView questionNumber = (TextView)MyDialog.findViewById(R.id.question_number);
        TextView question = (TextView)MyDialog.findViewById(R.id.question);
        TextView nextQuestion = (TextView)MyDialog.findViewById(R.id.next_question);

        Button finishQuiz = (Button)MyDialog.findViewById(R.id.finish_quiz);
        Button endQuiz = (Button)MyDialog.findViewById(R.id.end_Quiz);

        if(!quizDataArrayList.get(questionNumberInteger).getPicture().equals("")){
            image.setVisibility(View.VISIBLE);

            Glide.with(context)
                    .asBitmap()
                    .load(quizDataArrayList.get(questionNumberInteger).getPicture())
                    .into(image);
        }
        else{
            image.setVisibility(View.GONE);
        }

        if(questionNumberInteger < quizDataArrayList.size()) {

            if (!Locale.getDefault().getDisplayLanguage().equals("English")) {
                questionNumber.setText("Pitanje " + (questionNumberInteger+1));
                question.setText(quizDataArrayList.get(questionNumberInteger).getQuestion());

                radioButtonAnswer1.setText(quizDataArrayList.get(questionNumberInteger).getAnswer1());
                radioButtonAnswer2.setText(quizDataArrayList.get(questionNumberInteger).getAnswer2());
                radioButtonAnswer3.setText(quizDataArrayList.get(questionNumberInteger).getAnswer3());
                nextQuestion.setText("DALJE");
                finishQuiz.setText("ZAVRŠI");
            } else {
                questionNumber.setText("Question " + (questionNumberInteger+1));
                question.setText(quizDataArrayList.get(questionNumberInteger).getQuestioneng());

                radioButtonAnswer1.setText(quizDataArrayList.get(questionNumberInteger).getAnswer1eng());
                radioButtonAnswer2.setText(quizDataArrayList.get(questionNumberInteger).getAnswer2eng());
                radioButtonAnswer3.setText(quizDataArrayList.get(questionNumberInteger).getAnswer3eng());
                nextQuestion.setText("NEXT");
                finishQuiz.setText("FINISH");
            }

            if(questionNumberInteger == (quizDataArrayList.size()-1)){
                finishQuiz.setVisibility(View.VISIBLE);
                nextQuestion.setVisibility(View.INVISIBLE);
            }
        }

        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(provjeriOdgovor()) {
                    MyDialog.dismiss();
                    questionNumberInteger++;
                    prikaziDialog();
                }
            }
        });

        finishQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(provjeriOdgovor()) {
                    image.setVisibility(View.GONE);
                    radioButtonAnswer1.setVisibility(View.GONE);
                    radioButtonAnswer2.setVisibility(View.GONE);
                    radioButtonAnswer3.setVisibility(View.GONE);

                    questionNumber.setVisibility(View.GONE);
                    String correctAnswersText = "Broj točnih odgovora: " + correctAnswers;
                    question.setText(correctAnswersText);
                    nextQuestion.setVisibility(View.INVISIBLE);

                    finishQuiz.setVisibility(View.GONE);
                    endQuiz.setVisibility(View.VISIBLE);

                }
            }
        });

        endQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(correctAnswers.equals(quizDataArrayList.size())){
                    izvrsiUpdateKupca();
                    CustomDialogBadge customDialogBadge = new CustomDialogBadge();
                    customDialogBadge.setContexPrikazivanja(context);
                    customDialogBadge.setData(user, badge);
                    customDialogBadge.prikaziDialogKorisniku();
                }
                else{
                    izvrsiUpdatePonuditelja();
                }
                MyDialog.dismiss();
            }
        });

        MyDialog.show();
    };

    @Override
    public void setData(User user, BadgesData badge) {
        this.user=user;
        this.badge=badge;
        this.badgeUri=badge.getBadgeURL();
    }

    public void setQuestions(ArrayList<QuizData> quizDataArrayList){
        this.quizDataArrayList=quizDataArrayList;
    }

    @Override
    public void setContexPrikazivanja(Context context) {
        this.context=context;
    }

    @Override
    public void izvrsiUpdateKupca() {
        badgesRepository.DodijeliZnackuKorisniku(user,badge.getBadgeURL());
    }

    @Override
    public void izvrsiUpdatePonuditelja() {
        badgesRepository.DodijeliZnackuKorisniku(user,"-" );
    }

    @Override
    public void prikaziDialogKorisniku() {
        prikaziDialog();
    }

    public boolean provjeriOdgovor(){
        boolean answered = true;

        String odgovor = "";
        if(radioButtonAnswer1.isChecked()){
            odgovor = radioButtonAnswer1.getText().toString();
        }
        else if(radioButtonAnswer2.isChecked()){
            odgovor = radioButtonAnswer2.getText().toString();
        }
        else if(radioButtonAnswer3.isChecked()){
            odgovor = radioButtonAnswer3.getText().toString();
        }
        else{
            Toast.makeText(context, R.string.chooseAnswer, Toast.LENGTH_LONG).show();
            answered = false;
        }

        if (!Locale.getDefault().getDisplayLanguage().equals("English")) {
            if(odgovor.equals(quizDataArrayList.get(questionNumberInteger).getCorrectAnswer())){
                correctAnswers++;
            }
        }

        else{
            if(odgovor.equals(quizDataArrayList.get(questionNumberInteger).getCorrectAnswereng())){
                correctAnswers++;
            }
        }

        return answered;
    }
}
