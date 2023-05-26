package project;
import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.awt.Label;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


import javafx.scene.image.*;
import javafx.scene.text.*;

import java.util.Scanner;
import java.awt.Label;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.scene.image.ImageView;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.*;
import javafx.scene.control.TextField;

import javafx.scene.image.*;
import javafx.scene.text.*;

import java.util.Scanner;



class Branch {
    ArrayList<Point2D> points =null;
   int dstID;
   int dstPort; 

   public Branch() {
   }

   @Override
   public String toString() {
       return "Branch{" + "points=" + points + ", dstID=" + dstID + ", dstPort=" + dstPort + '}';
   }

   public Branch(ArrayList<Point2D> points, int dstID, int dstPort) {
       this.points = points;
       this.dstID = dstID;
       this.dstPort = dstPort;
   }
   
}


class Lines {
    int srcID;
    int srcPort;
    ArrayList<Point2D> points =null;
    int dstID=0;
    int dstPort=0; 
public Lines() {
    }
    @Override
    public String toString() {
        return "Lines{" + "srcID=" + srcID + ", srcPort=" + srcPort + ", points=" + points + ", dstID=" + dstID + ", dstPort=" + dstPort + ", branches=" + branches + '}';
    }
    ArrayList<Branch> branches=null;

    public Lines(int srcID, int srcPort, ArrayList<Point2D> points, int dstID, int dstPort, ArrayList<Branch> branches) {
        this.srcID = srcID;
        this.srcPort = srcPort;
        this.points = points;
        this.dstID = dstID;
        this.dstPort = dstPort;
        this.branches = branches;
    }

    

   
}

class Blocks {
    String name;
    int ID;
    int[] position;
    int ports=1;

   Blocks(String name, int ID, int[] position) {
        this.name = name;
        this.ID = ID;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }
    public int getLeft(){
            return position[0];
    }
    public int getTop(){
            return position[1];
    }
    public int getRight(){
            return position[2];
    }
    public int getBottom(){
            return position[3];
    }

    @Override
    public String toString() {
        return "Blocks{" + "name= " + name + ", ID= " + ID + ", position=[" + position[0]+","+ position[1]+"," +position[2]+","+position[3]+ "] , ports= " + ports + '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public void setPorts(int ports) {
        this.ports = ports;
    }

    public int getPorts() {
        return ports;
    }
    
}
public class main extends Application{
	


	
	
	
	private static byte[] readAllBytes(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        fis.read(buffer);
        fis.close();
        return buffer;
    }
 public static int[] extractNumbers(String line){
        int[] arr=new int[4];
         int firstComma=line.indexOf(',');
         int secondComma=line.indexOf(',', firstComma+1);
         int thirdComma=line.indexOf(',', secondComma+1);
         String k=line.substring(line.indexOf('[')+1, firstComma);
         arr[0]=Integer.parseInt(k);
         String l=line.substring(firstComma+2,secondComma);
         arr[1]=Integer.parseInt(l);
         String m=line.substring(secondComma+2,thirdComma);
         arr[2]=Integer.parseInt(m);
         String n=line.substring(thirdComma+2,line.indexOf(']'));
         arr[3]=Integer.parseInt(n);    
         return arr;
    }
 public static ArrayList<Point2D> extractPoints(String line){
     ArrayList<Point2D> arr = new ArrayList<Point2D>();
     String m =line.substring(line.indexOf('[')+1,line.indexOf(']'));
     String[] arr1=m.split(String.valueOf(';'), -1);
     int count = arr1.length - 1;
     for(int i=0;i<=count;i++){ 
        String[] arr2 =arr1[i].split(String.valueOf(','), -1);
        
        int x=Integer.parseInt(arr2[0].replaceAll("\\s+", ""));
        int y=Integer.parseInt(arr2[1].replaceAll("\\s+", ""));
        arr.add(new Point2D(x,y));        
     }
             return arr;
 }
public void start(Stage primaryStage)
{

	 String mdlFilename = "Example.mdl"; 
    int[] systemLocation = new int[4];
    ArrayList<Blocks> blocks = new ArrayList<Blocks>();
    ArrayList<Lines> liness = new ArrayList<Lines>();

    try {
        // Load the Simulink model file
        byte[] fileBytes = readAllBytes(new File(mdlFilename));
        String fileContents = new String(fileBytes, "utf-8");
        Scanner scan=new Scanner(fileContents);
        String line=scan.nextLine();
        while(scan.hasNextLine()){
            line=scan.nextLine();
            if(line.contains("<System>")){
                break;
            }
            
        }
        while(!(line.contains("</System>"))){
            line=scan.nextLine();
            if(line.contains("Location")){
               systemLocation=extractNumbers(line);
            }
            if(line.contains("<Block")){
                String name=line.substring(line.indexOf("Name")+6,line.indexOf('"',line.indexOf("Name")+6)); 
                String SID=line.substring(line.indexOf("ID")+4,line.indexOf('"',line.indexOf("ID")+4));
                int ID=Integer.parseInt(SID);
                int ports;
                int[] position;
                line=scan.nextLine();
                if(line.contains("Ports")){
                    ports=Integer.parseInt(line.substring(21, 22));
                    position=extractNumbers(scan.nextLine());
                    Blocks b=new Blocks(name,ID,position);
                    b.setPorts(ports);
                    blocks.add(b);
                  
                }
               else{
                     position=extractNumbers(line);
                     Blocks b=new Blocks(name,ID,position);
                     blocks.add(b);
                  
                }   
            }
            if(line.contains("<Line")){
                Lines l=new Lines();
            
                String t;
                while(!(line.contains("</Line"))){
                    line=scan.nextLine();
                    
                    if(line.contains("Src")){
                        t = line.substring(line.indexOf('>')+1, line.indexOf('#'));
                        l.srcID=Integer.parseInt(t);
                        t = line.substring(line.indexOf(':')+1, line.indexOf('/')-1);
                        l.srcPort=Integer.parseInt(t);
                       
                    }
                    if(line.contains("Dst")){
                        t = line.substring(line.indexOf('>')+1, line.indexOf('#'));
                        l.dstID=Integer.parseInt(t);
                        t = line.substring(line.indexOf(':')+1, line.indexOf('/')-1);
                        l.dstPort=Integer.parseInt(t);
                        
                    }
                    if(line.contains("<Branch>")){
                        Branch m=new Branch();
						if(l.branches ==null)
                        l.branches=new ArrayList<Branch>();
                       // int dstID1=0;
                       // int dstPort1=0; 
                        String k;
                        ArrayList<Point2D> branchPoints = new ArrayList<Point2D>();
                        while(!(line.contains("</Branch"))){
                             line=scan.nextLine();
                             if(line.contains("Dst")){
                                k = line.substring(line.indexOf('>')+1, line.indexOf('#'));
                                m.dstID=Integer.parseInt(k);
                                k = line.substring(line.indexOf(':')+1, line.indexOf('/')-1);
                                m.dstPort=Integer.parseInt(k);
                            }
                              if(line.contains("Points")){
                                  m.points=extractPoints(line);
                             }
                        }
                      
                        l.branches.add(m);
                        System.out.println(m.toString());
                    }
                    if(line.contains("Points")){
                        l.points=extractPoints(line);
                    }
                }
      
                liness.add(l);
                System.out.println(l.toString());
            }
        }
        
      
    } catch (IOException e) {
        e.printStackTrace();
    }
    

Pane pane= new Pane();
ArrayList <Rectangle> rectangles = new ArrayList <Rectangle> ();
ArrayList <Arrow> arrows = new ArrayList <Arrow> ();
ArrayList<Line> line = new ArrayList<Line> ();
ArrayList<Text> labels = new ArrayList<Text> ();
StackPane panes1 = new StackPane ();
StackPane panes2 = new StackPane ();
StackPane panes3 = new StackPane ();
StackPane panes4 = new StackPane ();
StackPane panes5 = new StackPane ();
int numOfArrows = blocks.get(0).ports;

for(int i = 0 ;i <blocks.size(); i++)
{
	int [] coordinates = blocks.get(i).position;
	rectangles.add(new Rectangle(coordinates[0],coordinates[1],coordinates[2]-coordinates[0], coordinates[3]-coordinates[1]));
	labels.add(new Text(blocks.get(i).getName()));
}

for(int i = 0; i < liness.size();i++)
{
	int source = 0, destination = 0; 
	for(int j = 0; j<blocks.size();j++)
	{
		if(blocks.get(j).ID == liness.get(i).srcID)
		{
			source = j;
				
		}
		if(blocks.get(j).ID == liness.get(i).dstID)
		{
			destination = j;
				
		}
		
	}
	if(liness.get(i).points != null)
	{
		double x1 = 0,x2 = 0,y1 = 0,y2 = 0;
		if(liness.get(i).points.get(0).getX() < 0)
		{
	           x1 = blocks.get(source).getLeft();
	           y1 = (blocks.get(source).getTop()+blocks.get(source).getBottom())/2;
	           x2 = blocks.get(source).getLeft()+liness.get(i).points.get(0).getX();
			   y2 = (blocks.get(source).getTop()+blocks.get(source).getBottom())/2+  liness.get(i).points.get(0).getY();
		}
		else 
		{
			        x1 = blocks.get(source).getRight();
			        y1 = (blocks.get(source).getTop()+blocks.get(source).getBottom())/2;
					x2 = blocks.get(source).getRight()+liness.get(i).points.get(0).getX();
					y2 = (blocks.get(source).getTop()+blocks.get(source).getBottom())/2+  liness.get(i).points.get(0).getY();
		}
		
		for(int k = 0; k <liness.get(i).points.size();k++)
		{
	
		line.add( new Line(x1,y1,x2,y2));
	
		
		if(k < liness.get(i).points.size()-1)
		{
		x1 = x2;
		y1= y2;
		
		x2 =x1 +liness.get(i).points.get(k+1).getX();
		y2 = y1 +  liness.get(i).points.get(k+1).getY();
		
		}
		}
		
		if(liness.get(i).branches != null)
		{ 
			int  destOfBranch = 0; 
			for(int l = 0; l < liness.get(i).branches.size();l++ )
			{
				for(int m = 0; m < blocks.size();m++)
				{
					if(blocks.get(m).ID == liness.get(i).branches.get(l).dstID)
					{
						destOfBranch = m;
							
					}
				}
				
				
                if(liness.get(i).branches.get(l).points != null)
                	
                {
                	double beginX = x2, beginY = y2, endX= beginX + liness.get(i).branches.get(l).points.get(0).getX(),
                			endY = beginY + liness.get(i).branches.get(l).points.get(0).getY();
                
                	
                	for(int q = 0; q < liness.get(i).branches.get(l).points.size(); q++)
                	{
                		
                		line.add(new Line(beginX,beginY,endX,endY));
                		if(q < liness.get(i).branches.get(l).points.size() -1)
                		{
            
                			beginX = endX;
                			beginY = endY;
                			endX =  beginX + liness.get(i).branches.get(l).points.get(q+1).getX();
                			endY =  beginY + liness.get(i).branches.get(l).points.get(q+1).getY();
                		}
                				
                	}
                	if(liness.get(i).branches.get(l).dstID != 0)
                    {
                       if(blocks.get(destOfBranch).getLeft()  < endX)
                    	   arrows.add(new Arrow(endX,endY,blocks.get(destOfBranch).getRight(),endY));
                       else
                    	arrows.add(new Arrow(endX,endY,blocks.get(destOfBranch).getLeft(),endY));
                    }
                	
                	
                }
                  else if(liness.get(i).branches.get(l).dstID != 0)
                {
                	  
                	  if(blocks.get(destOfBranch).getLeft() < x2)
                   	   arrows.add(new Arrow(x2,y2,blocks.get(destOfBranch).getRight(),y2));
                	  else
                	arrows.add(new Arrow(x2,y2,blocks.get(destOfBranch).getLeft(),y2));
                }
				
			
			}
		}
		else if(liness.get(i).dstID != 0)
		{
			 if(blocks.get(destination).getLeft() < x2)
             	   arrows.add(new Arrow(x2,y2,blocks.get(destination).getRight(),y2));
          	  else
			arrows.add(new Arrow(x2,y2,blocks.get(destination).getLeft(),y2));
			
		}
		}
	else if(liness.get(i).dstID != 0 )
	{
		
	arrows.add(new Arrow(blocks.get(source).getRight(), (blocks.get(source).getTop()+ 
			blocks.get(source).getBottom())/2, blocks.get(destination).getLeft(), 
			(blocks.get(source).getTop()+ blocks.get(source).getBottom())/2));
	}
}
	
	
		
		




for(int i = 0; i < blocks.size(); i++)
{
 
	
rectangles.get(i).setFill(Color.TRANSPARENT);
rectangles.get(i).setStroke(Color.ROYALBLUE);

labels.get(i).setY(rectangles.get(i).getY() +  1.5 *(blocks.get(i).getBottom()- blocks.get(i).getTop()) );
labels.get(i).setTextAlignment(TextAlignment.CENTER);
labels.get(i).setFill(Color.ROYALBLUE);
labels.get(i).setStroke(Color.ROYALBLUE);
pane.getChildren().add(labels.get(i));
if(i==0)
{
	labels.get(i).setX(rectangles.get(i).getX()+3);
}
else if(i==1)
{
	labels.get(i).setX(rectangles.get(i).getX()-5);
}
else if(i==2)
{
	labels.get(i).setX(rectangles.get(i).getX()-9);
}
else if(i==3)
{
	labels.get(i).setX(rectangles.get(i).getX());
}
else if(i==4)
{
	labels.get(i).setX(rectangles.get(i).getX()-10);
}



}


Text text1 = new Text();
text1.setText("1");

ImageView imageView = new ImageView("Capture.png");
imageView.setFitWidth(20);
imageView.setFitHeight(20);

Text text2 = new Text("+ \n+ \n+");
text2.setFont(new Font(7));

Rectangle rectangle1 = new Rectangle();
rectangle1.setFill(Color.TRANSPARENT);
rectangle1.setStroke(Color.BLACK);
rectangle1.setArcWidth(3.0); 
rectangle1.setArcHeight(3.0); 
rectangle1.setWidth(20);
rectangle1.setHeight(15);

ImageView imageView2 = new ImageView("Capture2.png");
imageView2.setFitWidth(15);
imageView2.setFitHeight(27);






panes1.getChildren().addAll(text1, rectangles.get(1));
panes1.setLayoutX(rectangles.get(1).getX()+10);
panes1.setLayoutY(rectangles.get(1).getY()+7);

panes2.getChildren().addAll(imageView,rectangles.get(2));
panes2.setLayoutX(rectangles.get(2).getX()+8);
panes2.setLayoutY(rectangles.get(2).getY()+5);

panes3.getChildren().addAll(text2,rectangles.get(0));
panes3.setLayoutX(rectangles.get(0).getX()+5);
panes3.setLayoutY(rectangles.get(0).getY());

panes4.getChildren().addAll(rectangle1,rectangles.get(3));
panes4.setLayoutX(rectangles.get(3).getX()+4.5);
panes4.setLayoutY(rectangles.get(3).getY()+5);

panes5.getChildren().addAll(imageView2,rectangles.get(4));
panes5.setLayoutX(rectangles.get(4).getX()+8);
panes5.setLayoutY(rectangles.get(4).getY()+5);

pane.getChildren().addAll(panes1,panes2,panes3,panes4,panes5);
for(int i = 0; i < line.size(); i++)
{
	pane.getChildren().add(line.get(i));
}
for(int i = 0; i < blocks.size(); i++)
{
	pane.getChildren().add(rectangles.get(i));
}
for(int i  =0 ; i < arrows.size(); i++)
{
	pane.getChildren().add(arrows.get(i));
}
Scene sc = new Scene(pane);

primaryStage.setTitle("Block Diagram");
primaryStage.setScene(sc);
primaryStage.show();

}

public static void main(String[] args) {
	
	
Application.launch(args);
	
	
	
 }

	
}
