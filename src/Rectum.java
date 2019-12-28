import java.util.ArrayList;
import java.util.HashSet;

public class Rectum {
    public static void main(String[] args){
        JointState js = new JointState(0,0,0,0,0,0,0,0,0,0,0);

        System.out.println("arsch");
        RectangularBursche rb = new RectangularBursche();
        rb.setVisible(true);
        Thread t = new Thread() {
            public void run() {
                HashSet<JointState> stateSpace = rb.getJointStructure().getStateSpace(rb.getJointState());
                rb.setStateSpace(stateSpace);
            }
        };
        t.start();

        while (true){
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ArrayList<JointState> neighbouring = rb.getNeighbouringStates();
            //System.out.println("isCurrentLegal: " + rb.getJointStructure().isLegal(rb.getJointState()));
            if (neighbouring.size()>0){
                JointState randomState = neighbouring.get((int) Math.floor(Math.random()*neighbouring.size()));
                rb.setJointState(randomState);
            }

            rb.getPaintWindow().repaint();
        }

    }
}
