package ifba.fisicaparatodos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
public class QuizActivity extends CommonMethods {
    private Button btnContinue;
    private final Button[] btnOption = new Button[4];
    private TextView questDisplay;
    private ArrayList<Question> questionArrayList = new ArrayList<>();
    private Question actualQuestion;
    private String userAnswer;
    private byte rightGuesses = 0;
    private byte wrongGuesses = 0;
    private boolean wasPressed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setToolBarSize(toolbar);
        }
        btnOption[0] = findViewById(R.id.option1);
        btnOption[1] = findViewById(R.id.option2);
        btnOption[2] = findViewById(R.id.option3);
        btnOption[3] = findViewById(R.id.option4);
        for (int z = 0; z < btnOption.length; z++){
            final int finalZ = z;
            btnOption[z].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userAnswer = (String) btnOption[finalZ].getText();
                    MediaPlayer mediaPlayer;
                    switch(answer()){
                        case 0:
                            mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.right);
                            setAsAnswer(btnOption[finalZ],0);
                            break;
                        default:
                            mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.wrong);
                            setAsAnswer(btnOption[finalZ],1);
                    }

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            mediaPlayer.release();
                        }
                    });
                    mediaPlayer.start();
                }
            });
        }
        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            ScrollView mainScroller = findViewById(R.id.mainScroller);
            mainScroller.scrollTo(0,0);
            if (questionArrayList.size()>0 && rightGuesses + wrongGuesses < 10) {
                setQuestion();
            }else if (wasPressed){
                finish();
            }else {
                questionArrayList = new ArrayList<>();
                wasPressed = true;
                String message;
                int percentage = (int) ((float) (rightGuesses)/(rightGuesses + wrongGuesses) * 100);
                btnContinue.setText(getResources().getString(R.string.quit));
                for (Button aBtnOption : btnOption) {
                    aBtnOption.setVisibility(View.INVISIBLE);
                }

                if (rightGuesses >= (rightGuesses + wrongGuesses) * 0.8) {
                    setProgressColor(R.color.correctAnswer);
                    message = getResources().getString(R.string.congratulations ,percentage);
                } else if (rightGuesses >= (rightGuesses + wrongGuesses) * 0.7) {
                    setProgressColor(R.color.energyColorPrimary);
                    message = getResources().getString(R.string.good ,percentage);
                } else {
                    setProgressColor(R.color.wrongAnswer);
                    message = getResources().getString(R.string.youCanImprove ,percentage);
                }
                questDisplay.setText(message);
            }
            }
        });

        questDisplay = findViewById(R.id.questionDisplay);

        Intent thisIntent = getIntent();
        String url = thisIntent.getStringExtra("URL");
        String category;
        MediaPlayer mediaPlayer = null;
        if (url.contains("universe")){
            category = "universe";
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.universe);
        }else if (url.contains("water")){
            category = "water";
            mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.water);
        }else if (url.contains("air")){
            category = "air";
            mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.air);
        }else if (url.contains("colors")){
            category = "colors";
        }else if (url.contains("energy")){
            category = "energy";
            mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.energy);
        }else if (url.contains("motion")){
            category = "motion";
            mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.motion);
        }else{
            category = "ALL";
        }
        if (mediaPlayer != null) {
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.release();
                }
            });
            mediaPlayer.start();
        }
        adicionarPerguntas(category);
        setQuestion();
    }

    private void adicionarPerguntas(String category){
        SQLiteOpenHelper quizDatabaseHelper = new QuizDatabaseHelper(this);
        try {
            SQLiteDatabase db = quizDatabaseHelper.getReadableDatabase();
            String[] selectionArgs = null;
            String selection = null;

            if (!category.equals("ALL")){
                selection = "CATEGORY = ?";
                selectionArgs = new String[] {category};
            }

            Cursor cursor = db.query("QUIZ",
                new String[] {"QUESTION", "OPTION1","OPTION2","OPTION3","OPTION4", "CATEGORY"},
                selection, selectionArgs, null, null, null);

            while(cursor.moveToNext()){
                String question = cursor.getString(0);
                String[] answers = {cursor.getString(1),cursor.getString(2),cursor.getString(3), cursor.getString(4)};
                String questionCategory = cursor.getString(5);
                questionArrayList.add(new Question(question,answers,questionCategory));
            }
            cursor.close();
        }catch (SQLiteException e){
            showToast(getResources().getString(R.string.message_databaseUnavailable));
            finish();
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.getItem(0).setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_help:
                Intent i = new Intent(this, HelpActivity.class);
                i.putExtra("category", actualQuestion.getCategory());
                startActivity(i);
                break;
            default:
                showToast(getResources().getString(R.string.message_unsupported));
        }

        return super.onOptionsItemSelected(item);
    }
    private void setQuestion(){
        try {
            actualQuestion = questionArrayList.get((int) (Math.random() * questionArrayList.size()));
            questionArrayList.remove(actualQuestion);
            questDisplay.setText(actualQuestion.getQuestionText());
            for (Button aBtnOption : btnOption) {
                setAsAnswer(aBtnOption, 2);
                aBtnOption.setText(actualQuestion.getRandomOption());
                aBtnOption.setEnabled(true);
            }
            btnContinue.setVisibility(View.INVISIBLE);
            setColorAndTitle(actualQuestion.getCategory().toLowerCase());
        }catch (Exception ex){
            Toast toast = Toast.makeText(this, getResources().getString(R.string.message_databaseUnavailable), Toast.LENGTH_LONG);
            toast.show();
            finish();
        }
    }
    @Override
    protected void setColor(int colorPrimary, int colorPrimaryDark) {
        super.setColor(colorPrimary, colorPrimaryDark);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnContinue.setBackgroundTintList(ContextCompat.getColorStateList(this, colorPrimary));
        }else{
            btnContinue.setBackground(new ColorDrawable(ContextCompat.getColor(this, colorPrimary)));
        }
    }
    private void setProgressColor(int color) {
        TextView questionDisplay = findViewById(R.id.questionDisplay);
        questionDisplay.setBackground(new ColorDrawable(ContextCompat.getColor(this, color)));
    }

    private int answer(){
        if (userAnswer.equals(actualQuestion.getAnswer())){
            return 0;
        }else{
            return 1;
        }
    }

    private void setAsAnswer(Button btn, int situation){
        if (situation == 0 || situation == 1){
            btnContinue.setVisibility(View.VISIBLE);
            for (Button aBtnOption : btnOption) {
                aBtnOption.setEnabled(false);
            }
        }
        switch(situation){
            case 0:
                rightGuesses++;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.correctAnswer));
                }else{
                    btn.setBackground(new ColorDrawable(ContextCompat.getColor(this, R.color.correctAnswer)));
                }
            break;
            case 1:
                wrongGuesses++;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.wrongAnswer));
                }else {
                    btn.setBackground(new ColorDrawable(ContextCompat.getColor(this, R.color.wrongAnswer)));
                }
                for (Button aBtnOption : btnOption) {
                    if (aBtnOption.getText().equals(actualQuestion.getAnswer())) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            aBtnOption.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.correctAnswer));
                        } else {
                            aBtnOption.setBackground(new ColorDrawable(ContextCompat.getColor(this, R.color.correctAnswer)));
                        }
                        break;
                    }
                }
            break;
            case 2:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.ic_launcher_background));
                }else{
                    btn.setBackground(new ColorDrawable(ContextCompat.getColor(this, R.color.ic_launcher_background)));
                }
                break;
            default: showToast(getResources().getString(R.string.message_unsupported));
        }
    }
}
