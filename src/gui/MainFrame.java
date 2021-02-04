package gui;

import domain.Task;
import infra.controllers.MainFrameController;
import infra.io.ImageHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MainFrame extends JFrame
{
    public static final int WIDTH = 700;
    public static final int HEIGHT = 700;

    private JPanel mainPanel, tasksPanel;
    private JScrollPane scroller;
    private ImageHelper imageHelper;
    private MainFrameController controller;

    public MainFrame(String title, MainFrameController controller)
    {
        super(title);
        this.controller = controller;
        this.mainPanel = new JPanel();
        this.tasksPanel = new JPanel();
        this.imageHelper = new ImageHelper();

        this.setResizable(false);
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BufferedImage img = imageHelper.getIconImage("/icon.png");
        this.setIconImage(img);

        renderContent();

        this.setVisible(true);
    }

    private void renderContent()
    {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Colors.LIGHT_GRAY);

        mainPanel.add(renderTitle("Task Manager"));
        mainPanel.add(renderTasks());
        mainPanel.add(renderButtons());

        this.getContentPane().add(BorderLayout.CENTER, mainPanel);
    }

    private JPanel renderTitle(String title)
    {
        JPanel titlePanel = new JPanel();
        titlePanel.setMaximumSize(new Dimension(WIDTH, 60));
        titlePanel.setBackground(Colors.GRAY);

        JLabel label = Components.createLabel(
                title,
                Color.white,
                new Font("arial", Font.BOLD, 22),
                Component.CENTER_ALIGNMENT
        );

        titlePanel.add(Box.createRigidArea(new Dimension(0, 50)));
        titlePanel.add(label);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 50)));

        return titlePanel;
    }

    public JPanel renderTasks()
    {
        tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));
        tasksPanel.setBackground(Colors.LIGHT_GRAY);

        ArrayList<ArrayList<Task>> taskGroups = this.controller.getTaskGroups();
        if(taskGroups == null) {
            taskError(tasksPanel);
            return tasksPanel;
        }
        
        taskScroller(tasksPanel, taskGroups);
        return tasksPanel;
    }

    private void taskError(JPanel tasksPanel)
    {
        JLabel errorLabel = Components.createLabel(
                "An Error Occurred! Try Again.",
                Colors.RED,
                new Font("arial",Font.BOLD, 25),
                Component.CENTER_ALIGNMENT
        );

        tasksPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        tasksPanel.add(errorLabel);
    }

    private void taskScroller(JPanel tasksPanel, ArrayList<ArrayList<Task>> taskGroups)
    {
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new GridLayout(taskGroups.size(), 1));
        scrollPanel.setBackground(Colors.LIGHT_GRAY);

        scroller = Components.createScroller(scrollPanel);
        scroller.getVerticalScrollBar().setUnitIncrement(16);
        tasksPanel.add(scroller);

        for (ArrayList<Task> taskGroup : taskGroups) {
            String taskGroupName = taskGroup.get(0).getName();
            String memoryUsage = this.controller.getMemoryUsage(taskGroup);
            String cpuUsage = this.controller.getCpuUsage(taskGroup);

            JPanel taskPanel = new JPanel();
            taskPanel.setBackground(Colors.LIGHT_GRAY);
            taskPanel.setLayout(new GridLayout(1, 3));
            taskPanel.setPreferredSize(new Dimension(WIDTH, 50));
            taskPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Colors.WHITE));

            JLabel nameLabel = Components.createLabel(
                    taskGroupName,
                    Colors.WHITE,
                    new Font("arial",Font.BOLD, 17),
                    Component.CENTER_ALIGNMENT
            );

            JLabel memoryUsageLabel = Components.createLabel(
                    memoryUsage,
                    Colors.WHITE,
                    new Font("arial",Font.BOLD, 16),
                    Component.CENTER_ALIGNMENT
            );

            JLabel cpuUsageLabel = Components.createLabel(
                    cpuUsage,
                    Colors.WHITE,
                    new Font("arial",Font.BOLD, 16),
                    Component.CENTER_ALIGNMENT
            );

            nameLabel.setHorizontalAlignment(JLabel.CENTER);
            memoryUsageLabel.setHorizontalAlignment(JLabel.CENTER);
            cpuUsageLabel.setHorizontalAlignment(JLabel.CENTER);

            taskPanel.add(nameLabel);
            taskPanel.add(memoryUsageLabel);
            taskPanel.add(cpuUsageLabel);

            taskPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            taskPanel.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    controller.openTaskGroup(taskGroup);
                }
            });
            scrollPanel.add(taskPanel);
        }
    }

    private JPanel renderButtons()
    {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Colors.GRAY);

        JButton refreshTasksButton = Components.createButton("Refresh Tasks", Colors.LIGHT_GRAY, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.refreshTasks();
            }
        });

        buttonPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        buttonPanel.add(refreshTasksButton);

        return buttonPanel;
    }

    public JScrollPane getScroller()
    {
        return scroller;
    }

    public JPanel getTasksPanel()
    {
        return tasksPanel;
    }
}
