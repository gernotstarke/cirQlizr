package org.gs.numviz

import javax.swing.JFrame
import java.awt.EventQueue

// see end-of-file for license information


class NumberVisualizer extends JFrame {

        public NumberVisualizer() {

            initUI();
        }

        private void initUI() {

            add(new SampleGraphic());

            setTitle("Simple Java 2D example");
            setSize(400, 400);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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



