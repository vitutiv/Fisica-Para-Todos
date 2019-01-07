package ifba.fisicaparatodos;
import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.Collections;
class Question {
    private final String questionText;
    private final ArrayList<String> options = new ArrayList<>();
    private final String category;
    private final String answer;
    //A resposta correta é sempre o primeiro item do vetor, por favor se atentar a isso ao adicionar novas questões
    Question(String q, @NonNull String[] o, String c){
        questionText = q;
        Collections.addAll(options, o);
        category = c;
        answer = o[0];
     }
    String getQuestionText(){
        return questionText;
    }
    String getCategory(){
        return category;
    }
    String getAnswer(){
        return answer;
    }
    String getRandomOption(){
        String opt = options.get((int) (Math.random() * options.size()));
        options.remove(opt);
        return opt;
    }
}