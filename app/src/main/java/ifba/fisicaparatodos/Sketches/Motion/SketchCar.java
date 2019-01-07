package ifba.fisicaparatodos.Sketches.Motion;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;

public class SketchCar extends PApplet {
    private Car c1, c2;
    private int vr = 0;
    private int vb = 0;
    private final int s0r = 0;
    private int s0b = 0;
    private int red,blue;
    private short acertos,erros;
    //private PImage checkerboard;
    private int qAtual;
    private boolean isRunning = false;
    private boolean isOver = false;
    private boolean answer,userAnswer;
    private String texto;
    public void settings(){
        size(displayWidth,displayHeight);
    }
    public void setup(){
        frameRate(30);
        red = color(230, 75, 25);
        blue = color(0, 144, 195);
        setQuestion();
    }
    private void setQuestion(){
        do{
            vr = (int)random(10);
        }while (vr < 1);

        do{
            vb = (int)random(10);
        }while (vb < 1);
        s0b = (int)random(50);
        textSize(height/20);
        stroke(255);
        texto = ("Um carro vermelho está no ponto " + s0r + "km a " + vr + "km/h, enquanto um azul está no ponto " + s0b + "km a " + vb +"km/h. Quem chega primeiro ao trecho 100km?");
        c1 = new Car(vr, s0r, 170, red);
        c2 = new Car(vb, s0b, 80, blue);
        qAtual = (acertos+erros+1);
        //checkerboard=loadImage("processing/checkerboard.jpg");
    }
    public void draw(){
        background(252, 194, 27);
        noStroke();
        if (!isOver){
            if (!isRunning){
                //Blue button
                fill(red);
                rect (0,height - 200 - (height/12),width/2,height/10);
                //Red button
                fill(blue);
                rect (width/2,height - 200 - (height/12),width/2,height/10);
            }else{
                textAlign(CENTER,CENTER);
                if (userAnswer == answer){
                    fill(76, 175, 80);
                    rect (0,height - 200 - (height/12),width,height/10);
                    fill(255);
                    text("RESPOSTA CERTA!",0,height - 200 - (height/12),width,height/10);
                }else{
                    fill(244, 67, 54);
                    rect (0,height - 200 - (height/12),width,height/10);
                    fill(255);
                    text("RESPOSTA ERRADA",0,height - 200 - (height/12),width,height/10);
                }
            }
            //Track
            fill(0);
            rect (0,height - 200,width, height);
            //Split Line
            fill(252, 194, 27);
            rect(0,height - 105,width,5);
            rect(0,height - 95,width,5);
            //Finish Line
            fill(255);
            stroke(1);
            //image (checkerboard,width - 20,height - 200,width, height);
            c1.drawCar();
            c2.drawCar();

            if (isRunning){
                c1.moveCar();
                c2.moveCar();
            }
            fill(0);
            rect(0,0,width,height/18);
            textAlign(LEFT,CENTER);
            fill(255);
            text("Questão " + qAtual,10,0, width - 20, (height /18));
            fill(0);
            textAlign(CENTER,CENTER);
            text(texto,10,height/18, width - 20, (height - 200) - (height/12) - (height/18));
        }else {
            PShape emoji;
            texto = "Você ";
            if (acertos >= (acertos + erros)){
                emoji = loadShape("processing/emoji_u1f604.svg");
                texto += "está de parabéns!";
            }else if (acertos >= (acertos + erros) * 0.8){
                emoji = loadShape("processing/emoji_u1f603.svg");
                texto += "foi ótimo!";
            }else if (acertos >= (acertos + erros) * 0.6){
                emoji = loadShape("processing/emoji_u263a.svg") ;
                texto += "foi bem!";
            }else{
                emoji = loadShape("processing/emoji_u1f641.svg");
                texto += "consegue melhorar!";
            }
            fill(255);
            shapeMode(CORNER);
            background(0);
            shape(emoji,(width / 2) - 200,(height / 2) - 200,400,400);
            textAlign(CENTER,TOP);
            float porcentagem = ((float) acertos/((float) acertos+erros)) * 100;
            text(texto + "\n" + "Acertou " + (int) porcentagem + "% das questões",0,(height / 5),width,height - 50);
        }
    }
    public void mouseReleased(){
        if (!isOver) {
            if (mouseY > (height - 200) - (height / 12) && mouseY < (height - 200) - (height / 12) + (height / 10) || isRunning) {
                if (isRunning) {
                    setQuestion();
                    if (acertos + erros == 5) {
                        isOver = true;
                    }
                } else {
                    userAnswer = mouseX >= (width / 2);
                    int resultRed = (width - s0r) / (vr);
                    int resultBlue = (width - s0b) / (vb);
                    answer = (resultBlue < resultRed);
                    if (userAnswer == answer) {
                        acertos++;
                    } else {
                        erros++;
                    }
                }
                isRunning = !isRunning;
            }
        }
    }

    class Car{
        final float speed;
        float xAxis;
        final float yAxis;
        final int carColor;
        final PImage imgRed,imgBlue;

        Car(int s, int x, int y, int c){
            speed = s * (width/100);
            xAxis = x * (width/100);
            yAxis = y;
            carColor = c;
            imgRed = loadImage("processing/red.png");
            imgBlue = loadImage("processing/blue.png");
        }

        void drawCar(){
            pushMatrix();
            fill(carColor);
            if (carColor == red){
                image(imgRed, xAxis,height - yAxis, 100,50);
            }else{
                image(imgBlue, xAxis,height - yAxis, 100,50);
            }
            popMatrix();
        }
        void moveCar(){
            xAxis = xAxis + speed;
        }
    }
}