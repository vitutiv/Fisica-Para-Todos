package ifba.fisicaparatodos;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
 class QuizDatabaseHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "FISICA_PARA_TODOS";
    private static final int DB_VERSION = 1;
    QuizDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE QUIZ ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "CATEGORY TEXT, "
                + "QUESTION TEXT, "
                + "OPTION1 TEXT, "
                + "OPTION2 TEXT, "
                + "OPTION3 TEXT, "
                + "OPTION4 TEXT );");
        insertUniverseQuestions(db);
        insertWaterQuestions(db);
        insertAirQuestions(db);
        insertColorsQuestions(db);
        insertEnergyQuestions(db);
        insertMotionQuestions(db);
    }
    private void insertUniverseQuestions(SQLiteDatabase db){
        //11 Questoes
        insertQuestion(db, "universe",
                "Uma estrela muito grande, ao explodir, pode se tornar em que após sua morte?",
                "Buraco negro", "Planeta anão", "Cometa", "Meteoro");
        insertQuestion(db, "universe",
                "Em qual dessas categorias se encaixa Plutão?",
                "Planeta Anão", "Lua", "Planeta", "País");
        insertQuestion(db, "universe",
                "Qual é o nome da  galáxia em que vivemos?",
                "Via Láctea", "Andrômeda", "Sistema Solar", "Lua");
        insertQuestion(db, "universe",
                "Júpiter, Saturno, Urano e Netuno são planetas do grupo dos:",
                "Gasosos", "Rochosos", "Anões", "Superquentes");
        insertQuestion(db, "universe",
                "Qual é a galáxia mais próxima da galáxia em que vivemos?",
                "Andrômeda", "Via Láctea", "Galaticus", "Cazaquistão");
        insertQuestion(db, "universe",
                "Os planetas do Sistema Solar por ordem, são:",
                "Mercúrio, Vênus, Terra, Marte, Júpiter, Saturno, Urano e Netuno",
                "Mercúrio, Vênus, Saturno, Terra, Plutão, Netuno, Marte, Júpiter e Urano",
                "Vênus, Mercúrio, Terra, Marte, Júpiter, Saturno, Netuno e Urano",
                "Mercúrio, Vênus, Terra, Marte, Júpiter, Saturno, Urano, Netuno e Plutão");
        insertQuestion(db, "universe",
                "O que é a gravidade?",
                "A força responsável por nos atrair para o centro da Terra",
                "A força responsável por evitar que detritos do espaço atinjam a Terra",
                "A força responsável por separar a água do ar",
                "Nenhuma das Alternativas");
        insertQuestion(db, "universe",
                "Qual o nome dado a teoria da formação do universo?",
                "Big Bang", "Big Crunch", "Big Beng", "Big Crush");
        insertQuestion(db, "universe",
                "Quem formulou a teoria da gravidade?",
                "Isaac Newton", "Albert Einstein", "Stephen Hawking", "Stephen King");
        insertQuestion(db, "universe",
                "O que são buracos negros?",
                "Regiões do universo em que nada do que entra pode escapar",
                "Esferas de gás, que possuem luz própria",
                "Regiões entre as estrelas e aglomerados ",
                "Conjuntos de estrelas");
    }
    private void insertAirQuestions(SQLiteDatabase db){
        //10 questoes
        insertQuestion(db, "air",
                "Qual desses gases é essencial para a nossa respiração?",
                "Oxigênio", "Hidrogênio", "Argônio", "Nitrogênio");
        insertQuestion(db, "air",
                "Sobre pressão atmosférica, é correto afirmar que:",
                "É o peso que o ar exerce sobre a superfície terrestre",
                "Não influencia sobre a superfície terrestre",
                "É a força de uma área aplicada sobre as moléculas de oxigênio",
                "É o peso que a superfície exerce sobre o ar");
        insertQuestion(db, "air",
                "A atmosfera é formada por 5 grandes camadas. \nEm qual delas os aviões de transporte de cargas e passageiros voam?",
                "Troposfera", "Estratosfera", "Mesosfera", "Exosfera");
        insertQuestion(db, "air",
                "Por que as pessoas ficam sem ar quando vão á lugares com grande altitude?",
                "Porque quanto mais alto é o local, mais rarefeito é o ar",
                "Por causa da gravidade que não deixa as pessoas respirarem",
                "Porque quanto mais alto é o local, menos rarefeito é o ar",
                "Nenhuma das Alternativas");
        insertQuestion(db, "air",
                "Qual a importância da camada de ozônio?",
                "É a camada que nos protege dos raios solares",
                "É responsável por não deixar os outros gases saírem do planeta",
                "Impedir que os raios lunares cheguem a superfície terrestre",
                "Permitir a passagem total dos raios solares");
        insertQuestion(db, "air",
                "Do que é formado o ar presente na atmosfera?",
                "É uma mistura de gases como o oxigênio, o nitrogênio e o argônio",
                "Apenas por Hélio e Oxigênio",
                "É uma mistura de gases nobres, especialmente por Hélio e Xenônio",
                "Somente por ozônio");
        insertQuestion(db, "air",
                "Sobre o aquecimento global, é correto afirmar:",
                "É causada também pela poluição, as queimadas e o desmatamento",
                "É processo de declive médio das temperaturas da Terra",
                "Não influência em epidemias",
                "Não afeta as florestas e geleiras");
        insertQuestion(db, "air",
                "Qual a principal causa do agravamento do efeito estufa?",
                "É resultado da crescente queima dos combustíveis fósseis",
                "Derretimento das calotas polares",
                "Aumento drástico da temperatura",
                "O aquecimento global");
        insertQuestion(db, "air",
                "O que é ar rarefeito?",
                "É o que acontece nos lugares mais altos, onde os gases como o oxigênio estão presentes em menores quantidades.",
                "É o ar responsável por impedir que a pressão atmosférica aumente",
                "Grande concentração de gases em uma região pequena",
                "Um ar que varia com a temperatura");
        insertQuestion(db, "air",
                "Onde a pressão é maior?",
                "No fundo do mar", "No espaço", "Na exosfera", "Na superfície terrestre");
    }
    private void insertWaterQuestions(SQLiteDatabase db){
        //7 Questoes
        insertQuestion(db, "water",
                "Escolha a opção que corresponde aos tipos de precipitação",
                "Todas as Outras Alternativas", "Neve", "Granizo", "Chuva");
        insertQuestion(db, "water",
                "As atividades humanas interferem no ciclo da água, alterando:",
                "a qualidade da água e sua quantidade disponível para o consumo das populações",
                "a quantidade total, mas não a qualidade da água disponível no planeta",
                "a qualidade da água disponível, apenas no subsolo terrestre",
                "apenas a disponibilidade de água superficial existente nos rios e lagos");
        insertQuestion(db, "water",
                "Qual dos itens possui maior densidade em temperatura ambiente?",
                "Leite", "Água doce", "Óleo de coco", "Água Salgada");
        insertQuestion(db, "water",
                "Por que a água é considerada um solvente universal?",
                "Porque ela pode dissolver muitas substâncias",
                "Porque ela está presente em todo o universo",
                "Porque ela derrete tudo o que entra em contato",
                "Nenhuma das alternativas");
        insertQuestion(db, "water",
                "O que é água mineral?",
                "É a água extraída dos minerais",
                "É a água que não contém minerais",
                "É a água produzida no laboratório",
                "Nenhuma das alternativas");
        insertQuestion(db, "water",
                "Qual a porcentagem de água doce POTÁVEL no mundo?",
                "1%", "97%", "50%", "3%");
    }
    private void insertColorsQuestions(SQLiteDatabase db){
        //10 Questoes
        insertQuestion(db, "colors",
                "Por que um papel de cor branca aparenta ser branco sob a luz vermelho sob a luz vermelha ,azul sob a luz azul e assim por diante?",
                "Pois o papel, por ser branco irá refletir qualquer cor que ele estiver recebendo",
                "Pois o papel, por ser branco irá absorver qualquer cor que ele estiver recebendo",
                "Pois o branco possui a capacidade de se tornar qualquer cor",
                "Pois o branco forma todas as cores");
        insertQuestion(db, "colors",
                "(PUC) Um pedaço de tecido vermelho, quando observado numa sala iluminada com luz azul, parece:",
                "Preto", "Branco", "Vermelho", "Azul");
        insertQuestion(db, "colors",
                "Misturando as tintas amarela e vermelha, qual cor nós obtemos?",
                "Laranja", "Rosa", "Roxo", "Azul");
        insertQuestion(db, "colors",
                "Misturando as tintas amarelo e azul, qual cor nós obtemos?",
                "Verde", "Marrom", "Azul", "Roxo");
        insertQuestion(db, "colors",
                "Misturando as tintas azul e vermelha, qual cor nós obtemos?",
                "Roxo", "Rosa", "Azul", "Laranja");
        insertQuestion(db, "colors",
                "Ao misturar cores primárias obtemos uma cor:",
                "Secundária", "Terciária", "Primária", "Nenhuma das Alternativas");
        insertQuestion(db, "colors",
                "A tríade de cores aditivas é formada pelas cores:",
                "Vermelho, Verde e Azul", "Ciano, Magenta e Amarelo", "Preto, Branco e Marrom", "Azul, Amarelo e Verde");
        insertQuestion(db, "colors",
                "A tríade de cores subtrativas é formada pelas cores:",
                "Ciano, Magenta e Amarelo", "Vermelho, Verde e Azul", "Preto, Branco e Marrom", "Azul, Amarelo e Verde");
        insertQuestion(db, "colors",
                "Supondo que no interior de uma sala haja três objetos de cores distintas: verde, azul e vermelho. " +
                        " \nDe que cor, respectivamente, veremos esses objetos se essa sala for iluminada por uma luz de cor azul?",
                "Azul, azul e roxo", "Verde, azul e roxo", "Preto, azul e preto", "Todos azuis");
        insertQuestion(db, "colors",
                "O que é o albedo?",
                "A capacidade que um material tem de refletir a luz que ele recebe",
                "A capacidade de um material de absorver a luz que ele recebe",
                "Ausência de incidência solar",
                "Um material que tem como propriedades ser totalmente escuro");
    }
    private void insertEnergyQuestions(SQLiteDatabase db){
        //5 Questoes
        insertQuestion(db, "energy",
                "As energias alternativas têm como objetivo de",
                "achar uma solução energética mais sustentável","poluir o meio ambiente", "modificar o ambiente", "causar o efeito estufa");
        insertQuestion(db, "energy",
                "Qual dessas é considerada uma energia renovável?",
                "Energia Eólica","Energia Nuclear", "Energia do Carvão", "Energia do Petróleo");
        insertQuestion(db, "energy",
                "Qual dos fatores abaixo é uma desvantagem das usinas hidrelétricas?",
                "Expropriações de comunidades próximas",
                "Não poluição do ar",
                "Reflorestamento, visto que as áreas das usinas são imensas",
                "Uso de fontes renováveis de energia ");
        insertQuestion(db, "energy",
                "Qual é a fonte de energia renovável mais utilizada no mundo?",
                "Hidrelétrica", "Eólica", "Nuclear", "Solar");
        insertQuestion(db, "energy",
                "Entende-se por energia geotérmica",
                "A energia do calor do interior da Terra",
                "A energia dos raios solares",
                "A energia do movimento das marés",
                "A energia fora do planeta Terra");
    }
    private void insertMotionQuestions(SQLiteDatabase db){
        insertQuestion(db, "motion",
                "Em um movimento uniforme o que não varia?",
                "Velocidade", "Tempo", "Deslocamento","Nenhuma das Alternativas");
        insertQuestion(db, "motion",
                "Um trem se move a 90 km/h durante 30 minutos, qual foi a distância percorrida?",
                "45 km", "60 km", "80 km", "90 km");
        insertQuestion(db, "motion",
                "Na física, qual o conceito de movimento?",
                "É a mudança de posição de um objeto ao decorrer do tempo",
                "A velocidade com que a luz se movimenta",
                "O tempo que a luz do Sol leva para chegar na Terra",
                "A distância entre o Sol e a Terra");
        insertQuestion(db, "motion",
                "Se um objeto está parado, ele tende a ficar...?",
                "Parado", "Em movimento", "Pulando", "Voando");
        insertQuestion(db, "motion",
                "O que descreve o espaço que um móvel percorre em um segundo?",
                "Velocidade", "Tempo", "Aceleração", "Nenhuma Das alternativas");
        insertQuestion(db, "motion",
                "Você está em um carro que se move em uma estrada a 30km/h. Em relação ao carro, qual a sua velocidade?",
                "0 km/h", "30 km/h", "30 m/s", "30 km/s");
        insertQuestion(db, "motion",
                "Em um acidente, um caminhão e uma moto se chocam. A moto é totalmente destruída. Qual o tipo de colisão?",
                "Inelástica", "Elástica", "Superelástica", "Muito Elástica");
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        recreateTable(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        recreateTable(db);
    }
    private void recreateTable(@NonNull SQLiteDatabase db){
        db.execSQL("DROP TABLE QUIZ");
        onCreate(db);
    }
    private void insertQuestion(SQLiteDatabase db, String category, String question, String option1, String option2, String option3, String option4){
        ContentValues quizValues = new ContentValues();
        quizValues.put("CATEGORY", category);
        quizValues.put("QUESTION", question);
        quizValues.put("OPTION1", option1);
        quizValues.put("OPTION2",option2);
        quizValues.put("OPTION3",option3);
        quizValues.put("OPTION4", option4);
        db.insert("QUIZ", null, quizValues);
    }
}