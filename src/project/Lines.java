/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project;

import java.util.ArrayList;
import javafx.geometry.Point2D;
/**
 *
 * @author user
 */
public class Lines {
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
