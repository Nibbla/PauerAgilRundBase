import Utils.FrameFactory;
import Utils.PaintPanel;
import Utils.Visualizable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class RectangularBursche implements Visualizable{

    private JFrame pw;
    private PaintPanel<Visualizable> pp;
    private double centerX = 0;
    private double centerY = 0;
    private JointStructure jointStructure = new JointStructure();
    private JointState js = jointStructure.getLegalState(16,16);
    private JLabel textLabel;
    private JPanel buttonPane;
    private static JointState currentJointStateSearch;
    private HashSet<JointState> stateSpace;

    public static void setCurrentJointStateSearch(JointState currentJointStateSearch) {
        RectangularBursche.currentJointStateSearch = currentJointStateSearch;
    }

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
        updateText();
        centerX = 0;
        centerY = 0;
        double xRatio = this.getPaintPanel().getWidth()/32;
        double yRatio = this.getPaintPanel().getHeight()/32;
        double radiusRatio = Math.sqrt((xRatio+yRatio)/2.);
        ArrayList<JointState> neighbouring = getNeighbouringStates();
        Color c = new Color(0,0,0,0.1f);
        for (JointState js : neighbouring){
            drawJointState(g ,js,xRatio,yRatio,radiusRatio, c);
        }
        c = new Color(0,0,0,1f);
        drawJointState(g ,js,xRatio,yRatio,radiusRatio, c);
        g.setColor(Color.RED);
        if (currentJointStateSearch!=null&&false) {
            JointState toPaint = currentJointStateSearch;
            c = new Color(1f,0,0,0.5f);
            drawJointState(g,toPaint,xRatio,yRatio,radiusRatio,c);
        }
        if (stateSpace != null&&false){
            Iterator<JointState> it = stateSpace.iterator();
            while (it.hasNext()){
                c = new Color(1f,0,0,0.1f);
                drawJointState(g,it.next(),xRatio,yRatio,radiusRatio,c);
            }
        }

    }

    private void drawJointState(Graphics g, JointState js, double xRatio, double yRatio, double averageRatio, Color c) {
        g.setColor( c);
        ArrayList<Joint> allJoints =  jointStructure.getJoints();
        for (Joint j : allJoints){
            int localX = (int) (js.getValue(j.getJointTypeX())*xRatio);
            int localY = (int) (js.getValue(j.getJointTypeY())*yRatio);
           // System.out.println(js.getValue(j.getJointTypeX()) + " " + js.getValue(j.getJointTypeY()));
            int r = (int) (j.getRadius()*averageRatio);
            g.fillOval((int)(localX+centerX-r),(int)(localY+ centerY-r),r*2,r*2);
            ArrayList<Joint> njs =  j.getNeihbourJoints();

            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(4));
            for (Joint nj : njs){
                int localNX = (int) (js.getValue(nj.getJointTypeX())*xRatio);
                int localNY = (int) (js.getValue(nj.getJointTypeY())*yRatio);
                g2d.drawLine((int)(localX+centerX),(int)(localY+centerY),(int)(localNX+centerX),(int)(localNY+centerY));
            }
        }
    }

    private void updateText() {
        js.printAllValues(textLabel);
    }

    public JFrame createPaintWindow(Visualizable v) {

        JFrame Jf = FrameFactory.getNewJFrame();
        PaintPanel<Visualizable> pp = (PaintPanel<Visualizable>) getPaintPanel();
        if (getPaintPanel()==null) {
            pp = new PaintPanel<>(v);
        }
        this.setPaintPanel(pp);
        Jf.setLayout(new BorderLayout());
        Jf.add(pp,BorderLayout.CENTER);

        JPanel textPanel = createTextPane();
        Jf.add(textPanel,BorderLayout.WEST);

        buttonPane = createButtonPane();
        Jf.add(buttonPane,BorderLayout.EAST);

        return Jf;
    }

    private JPanel createTextPane() {
        JPanel textPane = new JPanel();
        textPane.setLayout(new BoxLayout(textPane, BoxLayout.Y_AXIS));
        textPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        textPane.add(Box.createHorizontalGlue());
        textLabel = new JLabel("bla");
        textPane.add(textLabel);
        textPane.add(Box.createRigidArea(new Dimension(10, 0)));
        return textPane;
    }

    private JPanel createButtonPane() {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.Y_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPane.add(Box.createHorizontalGlue());
        JCheckBox chinButton = new JCheckBox("Chin");
        JCheckBox glassesButton = new JCheckBox("Glasses");
        JCheckBox hairButton = new JCheckBox("Hair");
        JCheckBox teethButton = new JCheckBox("Teeth");

        ItemListener il = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

            }
        };

        //Register a listener for the check boxes.
        chinButton.addItemListener(il);
        glassesButton.addItemListener(il);
        hairButton.addItemListener(il);
        teethButton.addItemListener(il);


        buttonPane.add(chinButton);
        buttonPane.add(glassesButton);
        buttonPane.add(hairButton);
        buttonPane.add(teethButton);
        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));

        return buttonPane;
    }

    @Override
    public void setPaintPanel(PaintPanel<Visualizable> pp) {
        this.pp = pp;
    }

    @Override
    public JPanel getPaintPanel() {
        return pp;
    }

    public JointState getJointState() {
        return js;
    }

    public void setJointState(JointState jointState) {
        this.js = jointState;
    }

    public ArrayList<JointState> getNeighbouringStates() {


        return js.getNeighbouringStates(jointStructure);
    }

    public JointStructure getJointStructure() {
        return jointStructure;
    }

    public void setStateSpace(HashSet<JointState> stateSpace) {
        this.stateSpace = stateSpace;
    }
}
