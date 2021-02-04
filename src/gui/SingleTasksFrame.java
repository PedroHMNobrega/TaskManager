package gui;

import domain.Task;
import infra.controllers.SingleTasksController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SingleTasksFrame extends JFrame
{
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;

    private JPanel mainPanel, tasksPanel;
    private SingleTasksController controller;

    public SingleTasksFrame(String title, SingleTasksController controller) {
        super(title);

        this.controller = controller;
        this.mainPanel = new JPanel();
        this.tasksPanel = new JPanel();

        this.setResizable(false);
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);

        renderContent();

        this.setVisible(true);
    }

    private void renderContent()
    {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Colors.LIGHT_GRAY);

        mainPanel.add(renderTitle("Task: " + this.controller.getName()));
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

        ArrayList<Task> tasks = this.controller.getTasks();
        if(tasks == null) {
            taskError(tasksPanel);
            return tasksPanel;
        } else if(tasks.size() == 0) {
            this.controller.closeWindow();
        }

        taskScroller(tasksPanel, tasks);
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

    private void taskScroller(JPanel tasksPanel, ArrayList<Task> tasks)
    {
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
        scrollPanel.setBackground(Colors.LIGHT_GRAY);

        JScrollPane scroller = Components.createScroller(scrollPanel);
        scroller.getVerticalScrollBar().setUnitIncrement(16);
        tasksPanel.add(scroller);

        for (Task task : tasks) {
            String taskGroupName = task.getName();
            String memoryUsage = String.format("Memory: %.2f%%", task.getMemoryUsage());
            String cpuUsage = String.format("CPU: %.2f%%", task.getCpuUsage());

            JPanel taskPanel = new JPanel();
            taskPanel.setBackground(Colors.LIGHT_GRAY);
            taskPanel.setLayout(new GridLayout(1, 4));
            taskPanel.setPreferredSize(new Dimension(WIDTH, 50));
            taskPanel.setMaximumSize(new Dimension(WIDTH, 50));
            taskPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Colors.WHITE));

            JLabel nameLabel = Components.createLabel(
                    taskGroupName,
                    Colors.WHITE,
                    new Font("arial",Font.BOLD, 16),
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
            taskPanel.add(Components.createButton2("Kill", Colors.RED, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    int confirm = JOptionPane.showConfirmDialog(
                            tasksPanel,
                            "Are you sure?",
                            "Kill " + task.getName(),
                            JOptionPane.YES_NO_OPTION
                    );
                    if(confirm == JOptionPane.YES_OPTION) {
                        controller.kill(task);
                    }
                }
            }));

            scrollPanel.add(taskPanel);
        }
    }

    private JPanel renderButtons()
    {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Colors.GRAY);
        buttonPanel.setMaximumSize(new Dimension(WIDTH, 50));

        JButton refreshTasksButton = Components.createButton("Kill All", Colors.RED, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int confirm = JOptionPane.showConfirmDialog(
                        tasksPanel,
                        "Are you sure?",
                        "Kill All " + controller.getName(),
                        JOptionPane.YES_NO_OPTION
                );
                if(confirm == JOptionPane.YES_OPTION) {
                    controller.killAll();
                }
            }
        });

        buttonPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        buttonPanel.add(refreshTasksButton);

        return buttonPanel;
    }

    public JPanel getTasksPanel()
    {
        return tasksPanel;
    }
}
