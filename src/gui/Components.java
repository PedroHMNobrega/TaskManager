package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class Components
{
    public static void panelRefresh(JPanel panel)
    {
        panel.revalidate();
        panel.repaint();
    }

    public static JButton createButton(String title, Color color, ActionListener al)
    {
        JButton bt = new JButton(title);
        bt.setBackground(color);
        bt.setForeground(Color.white);
        bt.setBorder(new LineBorder(color, 10, true));
        bt.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bt.addActionListener(al);
        return bt;
    }

    public static JButton createButton2(String title, Color color, ActionListener al)
    {
        JButton bt = new JButton(title);
        bt.setBackground(color);
        bt.setForeground(Color.white);
        bt.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bt.addActionListener(al);
        return bt;
    }

    public static JLabel createLabel(String title, Color color, Font font, float xAligment)
    {
        JLabel label = new JLabel(title);
        label.setForeground(color);
        label.setFont(font);
        label.setAlignmentX(xAligment);
        return label;
    }

    public static JScrollPane createScroller(JPanel panel)
    {
        JScrollPane scroller = new JScrollPane(panel);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroller.setBorder(BorderFactory.createEmptyBorder());
        return scroller;
    }

    public static JDialog createDialog(JFrame frame, String title, String text)
    {
        final JDialog dialog = new JDialog(frame, title, Dialog.ModalityType.MODELESS);
        dialog.setSize(200, 45);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(frame);
        return dialog;
    }

    public static void showMessage(JPanel panel, String message)
    {
        panel.removeAll();
        panelRefresh(panel);
        JLabel messageLabel = createLabel(message, Color.white,
                new Font("arial", Font.BOLD, 20), Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalGlue());
        panel.add(messageLabel);
        panel.add(Box.createVerticalGlue());
    }
}
