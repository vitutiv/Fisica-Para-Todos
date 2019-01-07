package ifba.fisicaparatodos.Sketches.Universe;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;


public class SketchSolarSystem extends PApplet {
    private ArrayList<Planet> planets;
    private float xo,yo;
    private float zoom;
    private float ratio;
    private Planet planet;
    private HScrollbar zoomBar;
    private PImage zoomImage;
    private boolean isZooming = false;
    private boolean isShowingInfo = false;
    private boolean found = false;
    private PVector newPosition;
    private boolean wantsToZoom = false;

    public void settings(){
        size(displayWidth,displayHeight);
    }
    public void setup(){
        planets = new ArrayList<>();
        planets.add(new Planet("Sol", 0, 45, 253, 184, 19));
        planets.add(new Planet("Mercúrio", 30, 10, 177, 177, 177));
        planets.add(new Planet("Vênus", 44, 15, 193,143,23));
        planets.add(new Planet("Terra", 61, 16, 0,119,210));
        planets.add(new Planet("Marte", 76, 12, 193,68,14));
        planets.add(new Planet("Júpiter", 96, 22, 216,202,157));
        planets.add(new Planet("Saturno", 116, 19, 234,214,184));
        planets.add(new Planet("Urano", 116, 19, 198,211,227));

        xo = width/2;
        yo = height/2;

        ratio = (float)(width/360);
        zoomBar = new HScrollbar(/*0,*/height-60*ratio,width,(int)(20*ratio)/*,2 ,ratio*/);
        zoomImage = loadImage("processing/zoomIcon.png");
        zoomImage.resize((int) (30*ratio),(int) (30*ratio));
        zoom = (float) (ratio*1.5);
    }
    public String getPlanetName(){
        if (planet != null){
            return planet.name;
        }else{
            return null;
        }
    }

    public void draw(){
        background(0);


        if(isZooming){
            zoomBar.update();
            zoomBar.display();
            zoom = zoomBar.getPos();
        }
        drawIcons();

        if(!wantsToZoom){translate(xo,yo); scale(zoom);}
        else {scale(zoom); translate(width/(zoom*2)-newPosition.x,height/(zoom*2)-newPosition.y); }

        for (Planet planet1 : planets) {
            if (planet1.isEnabled) {
                found = true;
                planet = planet1;
            }
            planet1.move();
            //planets.get(i).wasTouched(mouseX,mouseY,xo,yo,zoom,planets);
        }
        isShowingInfo = found;

        found = false;


    }

    public void mouseDragged(){
        if(!isZooming){
            xo+=(mouseX - pmouseX);
            yo+=(mouseY - pmouseY);
        }
    }

    public void mouseReleased(){

        touchieCommands();

    }

//public void touchStarted(){
//  mouseX = (int)touches[0].x;
//  mouseY = (int)touches[0].y;

//  touchieCommands();

//}

    private void touchieCommands(){

        if (mouseY>height-40*ratio && mouseY<height-10*ratio){

            if (mouseX>width-40*ratio && mouseX<width-10*ratio){
                isZooming = !isZooming;
            }

            //rect(width-90,height-40,30,30,5);

            if (mouseX>width-90*ratio && mouseX<width-60*ratio){
                if(!isZooming){
                    zoom = 1*ratio;
                }
                xo = width/2;
                yo = height/2;
            }

            //rect(width-140,height-40,30,30,5);
            if (mouseX>width-140*ratio && mouseX<width-100*ratio && isShowingInfo){
                wantsToZoom = !wantsToZoom;

                if(wantsToZoom){

                    for (Planet planet1 : planets) {

                        if (planet1.isEnabled) {
                            newPosition = planet1.position;
                        }
                    }
                }
            }
        }

        if(!wantsToZoom){
            for(int i = 0; i<planets.size(); i++){
                planet = planets.get(i);
                planet.wasTouched(mouseX,mouseY,xo,yo,zoom,planets);
            }
        }

    }

    private void drawIcons(){
        if(isShowingInfo){
            textSize(ratio*25);
            fill(255);
            textAlign(LEFT, TOP);
            text(planet.name,10,10);
        }

        noStroke();

        strokeWeight(0);

        //fill(255);
        //rect(width-40*ratio,height-40*ratio,30*ratio,30*ratio,5*ratio);
        //fill(0);
        //rect(width-30*ratio,height-40*ratio,10*ratio,30*ratio);
        //rect(width-40*ratio,height-30*ratio,30*ratio,10*ratio);

        image(zoomImage,width-40*ratio,height-40*ratio);

        fill(255);
        rect(width-90*ratio,height-40*ratio,30*ratio,30*ratio,5*ratio);

        if (isShowingInfo){
            fill(255);
            rect(width-140*ratio,height-40*ratio,30*ratio,30*ratio,5*ratio);
            stroke(0);
            strokeWeight(20);
            point(width-125*ratio,height-25*ratio);
        }
    }
    class HScrollbar {
        final int swidth, sheight;    // width and height of bar
        final float xpos, ypos;       // x and y position of bar
        float spos, newspos;    // x position of slider
        final float sposMin, sposMax; // max and min values of slider
        final int loose;              // how loose/heavy
        boolean over;           // is the mouse over the slider?
        boolean locked;
        final float ratio;
        //final float sRatio;

        HScrollbar (/*float xp,*/ float yp, int sw, int sh /*,int l, float sRatio*/) {
            swidth = sw;
            sheight = sh;
            int widthtoheight = sw - sh;
            ratio = (float)sw / (float)widthtoheight;
            xpos = 0;
            ypos = yp-sheight/2;
            spos = xpos + swidth/2 - sheight/2;
            newspos = spos;
            sposMin = xpos;
            sposMax = xpos + swidth - sheight;
            loose = 2;
            //this.sRatio = sRatio;
        }

        void update() {
            over = overEvent();
            if (mousePressed && over) {
                locked = true;
            }
            if (!mousePressed) {
                locked = false;
            }
            if (locked) {
                newspos = constrain(mouseX-sheight/2, sposMin, sposMax);
            }
            if (abs(newspos - spos) > 1) {
                spos = spos + (newspos-spos)/loose;
            }
        }

        float constrain(float val, float minv, float maxv) {
            return min(max(val, minv), maxv);
        }

        boolean overEvent() {
            return mouseX > xpos && mouseX < xpos + swidth &&
                    mouseY > ypos && mouseY < ypos + sheight;
        }

        void display() {
            noStroke();
            fill(204);
            rect(xpos, ypos, swidth, sheight);
            if (over || locked) {
                fill(0, 0, 0);
            } else {
                fill(102, 102, 102);
            }
            rect(spos, ypos, sheight, sheight);
        }

        float getPos() {
            // Convert spos to be values between
            // 0 and the total width of the scrollbar
            return (float) (spos/(ratio*40) + 0.5*ratio);
        }
    }
    public class Planet{
        final String name;
        //final float distance;
        final float radius;
        final int red, green, blue;
        final PVector position;
        final float angle;
        final float angularSpeed;
        boolean isEnabled;
        final float ratio = (float)(width) / (float)(360);
        float warp;
        int multiplier = 1;

        Planet (String name, float distance, float radius, int red, int green, int blue){
            this.name = name;
            //this.distance = distance;
            this.radius = radius;
            this.red = red;
            this.green = green;
            this.blue = blue;
            angle = random(TWO_PI);
            position = PVector.fromAngle(angle);
            position.mult(distance);
            angularSpeed = sqrt(distance)/500;
            warp = 10*ratio;
        }
        void move(){
            position.rotate(angularSpeed);
            show();
        }

        void show(){
            if (isEnabled){
                stroke(255);
                strokeWeight(1);
                fill(0,0,0,0);
                if(warp>10*ratio) multiplier = -1;
                if(warp<0) multiplier = 1;

                warp = (float) (warp + multiplier*0.3);

                ellipse(position.x,position.y,warp+radius,warp+radius);

            }

            stroke(red,green,blue);
            strokeWeight(radius);
            point(position.x,position.y);
            if (name.equals("Terra")){
                stroke(70, 180, 0);
                strokeWeight(7);
                point(position.x-2,position.y+3);
                point(position.x-2,position.y);
                point(position.x,position.y-3);
                point(position.x+3,position.y+3);
            }
        }

        void wasTouched(float x, float y, float xo, float yo, float zoom, ArrayList<Planet> array){
            PVector touchPosition = new PVector((x-xo)/zoom,(y-yo)/zoom);
            PVector relativePosition = touchPosition.copy();

            relativePosition.sub(position);

            float distanceRad = relativePosition.mag();

            if (distanceRad<=radius) {isEnabled=!isEnabled; manageArray(array);}
        }

        void manageArray(ArrayList<Planet> array){
            for (Planet anArray : array) {
                if (anArray != this) {
                    anArray.isEnabled = false;
                }
            }
        }
    }
}
