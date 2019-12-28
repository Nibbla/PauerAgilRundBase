package Utils;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.logging.Logger;

/** © Markus Niebisch 18.01.2019
 * Created by Niebisch Markus on 23.02.2018.
 */
public class FrameFactory implements Observer{
    static final Logger logger = Logger.getLogger(FrameFactory.class.getName());
    private static List<ObservedJFrame> allObservedFrames = new ArrayList<>(30);

    public static JFrame getNewJFrame() {
        return new ObservedJFrame();
    }

    @Override
    public void update(java.util.Observable o, Object arg) {

    }

    private static void addObservedJFrame(ObservedJFrame observedJFrame) {
        allObservedFrames.add(observedJFrame);
    }

    private static void componentMoved(ObservedJFrame observedJFrame, ComponentEvent e) {

    }

    private static void componentResized(ObservedJFrame observedJFrame, ComponentEvent e) {

    }
    private static void windowClosed(ObservedJFrame observedJFrame, WindowEvent e) {
        allObservedFrames.remove(observedJFrame);

    }
    private static void componentHidden(ObservedJFrame observedJFrame, ComponentEvent e) {

    }

    private static void componentShown(ObservedJFrame observedJFrame, ComponentEvent e) {

    }

    private static void windowDeiconified(ObservedJFrame observedJFrame, WindowEvent e) {

    }

    private static void windowIconified(ObservedJFrame observedJFrame, WindowEvent e) {

    }

    private static void windowOpened(ObservedJFrame observedJFrame, WindowEvent e) {

    }


    private static class ObservedJFrame extends JFrame{
        public ObservedJFrame() {
            this.addWindowListener(getWindowListener(this));
            this.addComponentListener(getComponentListeners(this));
            FrameFactory.addObservedJFrame(this);
            this.setSize(400,400);
        }

        private ComponentListener getComponentListeners(ObservedJFrame observedJFrame) {
            ComponentListener cl = new ComponentListener() {
                @Override
                public void componentResized(ComponentEvent e) {
                    FrameFactory.componentResized(observedJFrame,e);
                }

                @Override
                public void componentMoved(ComponentEvent e) {
                    FrameFactory.componentMoved(observedJFrame,e);
                }

                @Override
                public void componentShown(ComponentEvent e) {
                    FrameFactory.componentShown(observedJFrame,e);
                }

                @Override
                public void componentHidden(ComponentEvent e) {
                    FrameFactory.componentHidden(observedJFrame,e);
                }
            };
            return cl;
        }

        private WindowListener getWindowListener(ObservedJFrame observedJFrame) {
            WindowListener w = new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {
                    FrameFactory.windowOpened(observedJFrame,e);
                }

                @Override
                public void windowClosing(WindowEvent e) {

                }

                @Override
                public void windowClosed(WindowEvent e) {
                    FrameFactory.windowClosed(observedJFrame,e);
                }

                @Override
                public void windowIconified(WindowEvent e) {
                    FrameFactory.windowIconified(observedJFrame,e);
                }

                @Override
                public void windowDeiconified(WindowEvent e) {
                    FrameFactory.windowDeiconified(observedJFrame,e);
                }

                @Override
                public void windowActivated(WindowEvent e) {

                }

                @Override
                public void windowDeactivated(WindowEvent e) {

                }
            };
            return w;
        }


    }



}
