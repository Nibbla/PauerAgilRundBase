import java.util.ArrayList;

public class Joint {
    private final double radius;
    private JointType jointTypeX;
    private JointType jointTypeY;
    private ArrayList<Edge> edges = new ArrayList<>(3);
    private ArrayList<Joint> neighbourJoints = new ArrayList<>(3);

    public Joint(double r, JointType jointType) {
        this.radius = r;
        this.jointTypeX = jointType;
        for (int i = 0; i < JointType.values().length; i++) {
            JointType type = JointType.values()[i];
            if (type.equals(jointTypeX)) jointTypeY = JointType.values()[i+1];
        }
    }

    public double getRadius() {
        return radius;
    }

    public ArrayList<Edge> getEdges() {
        return edges;

    }

    public double getGlobalX(double centerX) {
        return centerX;
    }

    public double getGlobalY(double centerY) {

        return centerY;
    }

    public static void connect(Joint j1, Joint j2, int minDistance, int maxDistance) {
        Edge e = new Edge(j1,j2,minDistance,maxDistance);
        j1.edges.add(e);
        j1.neighbourJoints.add(j2);
        j2.edges.add(e);
        j2.neighbourJoints.add(j1);
    }

    public ArrayList<Joint> getNeihbourJoints() {
        return neighbourJoints;
    }

    public JointType getJointTypeX() {
        return jointTypeX;
    }

    public JointType getJointTypeY() {
        return jointTypeY;
    }
}
