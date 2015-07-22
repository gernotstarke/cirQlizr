package org.gs.numviz

import javax.swing.JFrame
import java.awt.EventQueue

// see end-of-file for license information


class NumberVisualizer extends JFrame {

        final static int X_SIZE = 800
        final static int Y_SIZE = 700

        public NumberVisualizer() {

            initUI();
        }

        private void initUI() {

            add(new NumberGraph());

            setTitle("Number Visualizer");
            setSize(X_SIZE, Y_SIZE);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable( false ) // issue #9
        }

        public static void main(String[] args) {

            EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    NumberVisualizer ex = new NumberVisualizer();
                    ex.setVisible(true);
                }
            });
        }
    }



