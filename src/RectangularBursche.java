import Utils.PaintPanel;
import Utils.Visualizable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RectangularBursche implements Visualizable{

    private JFrame pw;
    private PaintPanel<Visualizable> pp;
    private double centerX = 20;
    private double centerY = 20;
    private JointStructure jointStructure = new JointStructure();
    private JointState js = jointStructure.getLegalState(16,16);

    @Override
    public JFrame getPaintWindow() {
        return pw;
    }

    @Override
    public void setPaintWindow(JFrame paintWindow) {
        this.pw = paintWindow;
    }

    @Override
    public void paint(Graphics g) {

        ArrayList<Joint> allJoints =  jointStructure.getJoints();
        for (Joint j : allJoints){
            int localX = js.getValue(j.getJointTypeX());
            int localY = js.getValue(j.getJointTypeY());

            int r = (int) j.getRadius();
            g.fillOval((int)(localX+centerX-r)*4,(int)(localY+ centerY-r)*4,r,r);
            ArrayList<Joint> njs =  j.getNeihbourJoints();

            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(3));
            for (Joint nj : njs){
                int localNX = js.getValue(nj.getJointTypeX());
                int localNY = js.getValue(nj.getJointTypeY());
                g2d.drawLine((int)(localX+centerX)*4,(int)(localY+centerY)*4,(int)(localNX+centerX)*4,(int)(localNY+centerY)*4);
            }
        }

    }

    @Override
    public void setPaintPanel(PaintPanel<Visualizable> pp) {
        this.pp = pp;
    }

    @Override
    public JPanel getPaintPanel() {
        return pp;
    }
}
