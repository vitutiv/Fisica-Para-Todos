package ifba.fisicaparatodos.Sketches.Water;
import java.util.ArrayList;
import processing.core.*;
public class SketchBottle extends PApplet {
    private Bottle Bottle;
    private float grav;
    public void settings(){
        size(displayWidth,displayHeight);
    }
    public void setup() {
        grav = 0.1F;
        touchStarted();
    }
    /*private void debug(){
        text(frameRate,mouseX,mouseY);
    }*/
    public void draw() {
        background(100);
        Bottle.show();
        Bottle.controlLiquid();
        //debug();
    }
    public void touchStarted(){
        Bottle = new Bottle(/*10,10,200,500*/);
    }
    class Bottle {
        final PVector pos;
        final float w;
        final float h;
        float level;
        final Hole[] holes;
        Bottle(/*float x, float y, float w, float h*/){
            int x = 10;
            int y = 10;
            holes = new Hole[3];
            level=100;
            pos = new PVector(x,y);
            w = 200;
            h = 500;
            float n;
            n = 0.2F;
            holes[0] = new Hole(pos.x+w-10,pos.y+(h*n),(100-n*100));
            n = 0.5F;
            holes[1] = new Hole(pos.x+w-10,pos.y+(h*n),(100-n*100));
            n = 0.98F;
            holes[2] = new Hole(pos.x+w-10,pos.y+(h*0.98F),(100-n*100));
        }
        void show() {
            pushStyle();
            fill(255);
            strokeWeight(4);
            rect(pos.x,pos.y, w, h);
            noStroke();
            fill(0,191,255);
            rect(1+pos.x,pos.y+ h, w -1,-(level/100)* h);
            popStyle();
        }
        void controlLiquid() {
            if (level-0.1>0){
                level-=0.1;

            }
            for (Hole hole : holes) hole.control(level);
        }
    }
    class Drop {
        final PVector pos;
        final PVector speed;
        final PVector acc;
        Drop(float x,float y,float start){
            acc = new PVector();
            pos = new PVector(x,y+10);
            this.speed = new PVector(start,0);
        }
        void move(){
            acc.add(0,grav);
            speed.add(acc);
            pos.add(speed);
            acc.mult(0);
        }
        void show(){
            pushStyle();
            stroke(0,191,255);
            strokeWeight(7);
            point(pos.x,pos.y);
            popStyle();
        }
    }
    class Hole {
        final PVector pos;
        final ArrayList<Drop> drops;
        final float relPos;
        Hole(float x, float y, float relPos){
            drops = new ArrayList<>();
            this.pos = new PVector(x,y);
            this.relPos = relPos;
        }
        void control(float level){
            pushMatrix();
            noFill();
            ellipse(pos.x,pos.y,10,10);
            popMatrix();
            manageArray(level);
            for (Drop drop : drops) {
                drop.move();
                drop.show();
            }
        }
        void manageArray(float level){
            if (level-relPos>0){
                Drop drop = new Drop((random(2)-1+pos.x), (random(20)-20+pos.y),sqrt(0.2F*(level-relPos)));
                drops.add(drop);
            }

            for(int i =0;i<drops.size();i++){
                Drop drop = drops.get(i);
                if (drop.pos.y>height){
                    drops.remove(i);
                }
            }
        }
    }
}
