package ifba.fisicaparatodos.Sketches.Energy;

import processing.core.*;

public class SketchEnergy extends PApplet {
    private float h;
    private float l1;
    private float l2;
    private float x1;
    private float x2;
    private float x31;
    private float x32;
    private float y11;
    private float yc1;
    private float y21;
    private float yc2;
    private int q1, q2, q3, q4, q5, q6, pincel, verde, azul, vermelho;
    private PShape img;
    public void settings(){
        size(displayWidth,displayHeight);
    }
    public void setup() {
        h = height/15; //altura
        l1 = 3*width/7; //largura 1
        l2 = width/2.4193548F; // largura 2
        x1 = width/60; //inicio retangulos 1
        x2 = 4*width/7; //inicio retangulos 2
        x31 = x1 + l1; //fim action 1
        x32 = x2 + l2; //fim action 2
        y11 = height/6 + height/60; //inicio retangulos 1
        yc1 = height/4 + height/60; //complemento 1
        y21 = height/20; //inicio retangulos 2
        yc2 = height/6; //complemento 2
        q1 = color(255, 255, 255); //cor retangulo 2-1
        q2 = color(255, 255, 255); //cor retangulo 2-2
        q3 = color(255, 255, 255); //cor retangulo 2-3
        q4 = color(255, 255, 255); //cor retangulo 2-4
        q5 = color(255, 255, 255); //cor retangulo 2-5
        q6 = color(255, 255, 255); //cor retangulo 2-6
        pincel  = color(255, 255, 255);
        verde = color(168, 237, 125);
        azul = color(108, 207, 235);
        vermelho = color(247, 134, 134);
        img = loadShape("processing/emoji_u26a1.svg");
        textMode(CENTER);
    }
    public void draw() {
        background(247, 247, 185);
        rec1(verde, 0, "Energia do Movimento");
        rec1(azul, 1, "Energia Luminosa");
        rec1(vermelho, 2, "Energia Química");
        rec2(q1, 0, "Termoelétrica");
        rec2(q2, 1, "Eólica");
        rec2(q3, 2, "Solar");
        rec2(q4, 3, "Maremotriz");
        rec2(q5, 4, "Nuclear");
        rec2(q6, 5, "Hidrelétrica");
        if ((q1 == vermelho) && (q2 == verde) && (q3 == azul) && (q4 == verde) && (q5 == vermelho) && (q6 == verde)) {
            background(0);
            shape(img, (width / 2) - 200,(height / 2) - 200,400,400);
            fill(255);
            textSize(width/20);
            textAlign(CENTER,TOP);
            text("PARABÉNS! \n Você conseguiu completar a atividade!",0,(height / 5),width,height - 50);
        }
    }
    private void rec1(int cor, float n, String text) {
        strokeWeight(width/200);
        fill(cor);
        if (cor == verde) {
            stroke(57, 135, 78);
        }
        if (cor == azul) {
            stroke(59, 63, 115);
        }
        if (cor == vermelho) {
            stroke(145, 38, 38);
        }
        rect(x1, y11 + n*yc1, l1, h, 20);
        fill(0);
        textSize(width/30);
        text(text, x1, y11 + n*yc1, l1, h);
    }

    private void rec2(int cor, float n, String text) {
        fill(cor);
        stroke(115, 113, 103);
        rect(x2, y21 + n*yc2, l2, h);
        fill(0);
        textAlign(CENTER,CENTER);
        text(text, x2, y21 + n*yc2, l2, h);
    }

    public void mouseReleased(){
        mouseDragged();
    }
        public void mouseDragged() {
        if ((q1 != vermelho) || (q2 != verde) || (q3 != azul) || (q4 != verde) || (q5 != vermelho) || (q6 != verde)) {
            noStroke();
            fill(pincel);
            ellipse(mouseX, mouseY, width / 60, width / 60);
            if ((mouseX >= x1) && (mouseX <= x31) && (mouseY >= y11) && (mouseY <= y11 + h)) {
                pincel = color(verde);
            }
            if ((mouseX >= x1) && (mouseX <= x31) && (mouseY >= y11 + yc1) && (mouseY <= y11 + h + yc1)) {
                pincel = color(azul);
            }
            if ((mouseX >= x1) && (mouseX <= x31) && (mouseY >= y11 + 2 * yc1) && (mouseY <= y11 + h + 2 * yc1)) {
                pincel = color(vermelho);
            }
            if ((mouseX >= x2) && (mouseX <= x32) && (mouseY >= y21) && (mouseY <= y21 + h)) {
                q1 = color(pincel);
            }
            if ((mouseX >= x2) && (mouseX <= x32) && (mouseY >= y21 + yc2) && (mouseY <= y21 + h + yc2)) {
                q2 = color(pincel);
            }
            if ((mouseX >= x2) && (mouseX <= x32) && (mouseY >= y21 + 2 * yc2) && (mouseY <= y21 + h + 2 * yc2)) {
                q3 = color(pincel);
            }
            if ((mouseX >= x2) && (mouseX <= x32) && (mouseY >= y21 + 3 * yc2) && (mouseY <= y21 + h + 3 * yc2)) {
                q4 = color(pincel);
            }
            if ((mouseX >= x2) && (mouseX <= x32) && (mouseY >= y21 + 4 * yc2) && (mouseY <= y21 + h + 4 * yc2)) {
                q5 = color(pincel);
            }
            if ((mouseX >= x2) && (mouseX <= x32) && (mouseY >= y21 + 5 * yc2) && (mouseY <= y21 + h + 5 * yc2)) {
                q6 = color(pincel);
            }
        }
    }
}