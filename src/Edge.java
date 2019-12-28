public class Edge {
    private final Joint j1;
    private final Joint j2;
    private final int minDistanceSquared;
    private final int maxDistanceSquared;

    public Edge(Joint j1, Joint j2, int minDistance, int maxDistance) {
        this.j1 = j1;
        this.j2 = j2;
        this.minDistanceSquared = minDistance*minDistance;
        this.maxDistanceSquared = maxDistance*maxDistance;
    }

    public Joint getOpposite(Joint current) {
        if (current.equals(j1))return j2;
        if (current.equals(j2))return j1;
        return null;
    }

    public boolean isLegal(JointState js) {
        int val1 = js.getValue(j1.getJointTypeX());
        int val2 = js.getValue(j1.getJointTypeY());
        int val3 = js.getValue(j2.getJointTypeX());
        int val4 = js.getValue(j2.getJointTypeY());
        int distanceSquared = (val1-val3)*(val1-val3)+(val2-val4)*(val2-val4);
        if (distanceSquared<minDistanceSquared||distanceSquared>maxDistanceSquared)return false;
        return true;
    }

    public void setNeighbourToArbitaryLegalValue(Joint current, Joint neighbour, JointState js) {

        int x = js.getValue(current.getJointTypeX());
        int y = js.getValue(current.getJointTypeY());

        double r = Math.sqrt((minDistanceSquared+maxDistanceSquared)/2.);
        double PI = 3.1415926535;
        double i, angle, x1, y1;

        for (i = 0; i < 360; i += 1) {
            angle = i;
            x1 = r * Math.cos(angle * PI / 180.);
            y1 = r * Math.sin(angle * PI / 180.);

            long ElX = Math.round(x + x1);
            long ElY = Math.round(y + y1);
            if (ElX < 0 || ElX > 31||ElY < 0 || ElY > 31)
                continue;
            js.setValue(neighbour.getJointTypeX(), (int) ElX);
            js.setValue(neighbour.getJointTypeY(), (int) ElY);
            break;
        }

    }
}
