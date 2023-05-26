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
public class Branch {
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
