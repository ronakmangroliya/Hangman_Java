import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LaunchPage extends JFrame implements ActionListener {
    private JPanel titlePanel;
    private JPanel groupPanel;
    private JPanel launcherPanel;
    private JButton launcherButton;
    LaunchPage(){
        setMainFrame();
        setSubjectInfoPage();
        setGroupLayout();
        setLauncherButton();
        createLayout();
    }

    public void setMainFrame() {
        setTitle("Hangman Game"); // set title of frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit out of application
        setResizable(false); // prevent frame of being resized
        setSize(Utils.FRAME_WIDTH, Utils.FRAME_HEIGHT); // set the x and y dimension of frame
        setLocationRelativeTo(null); //centre window
        setLayout(new BorderLayout());
    }

    private void setSubjectInfoPage() {
        JLabel gameLabel = new JLabel("Hangman Game");
        gameLabel.setFont(new Font(null,Font.BOLD,18));
        JLabel subjectCodeLabel = new JLabel("Database Programming using Java-ITC-5201-IGB");
        JLabel assignmentLabel = new JLabel("Assignment 1");
        JLabel professorLabel = new JLabel("Prof. Mehrnaz Zhian");
        titlePanel = new JPanel();
        titlePanel.setBorder(new EmptyBorder(new Insets(50, 140, 20, 140)));
        titlePanel.setLayout(new BoxLayout(titlePanel,BoxLayout.Y_AXIS));
        titlePanel.add(gameLabel);
        titlePanel.add(assignmentLabel);
        titlePanel.add(professorLabel);
        titlePanel.add(subjectCodeLabel);
    }

    private void setGroupLayout() {
        JLabel createdBy = new JLabel("Develop By : ");
        createdBy.setFont(new Font(null,Font.BOLD,14));
        JLabel firstStudentName = new JLabel("Ronak Patel,");
        JLabel secondStudentName = new JLabel(" Ravikumar Gelani,");
        JLabel thirdStudentName = new JLabel(" Kishan Patel");
        groupPanel= new JPanel();
        groupPanel.add(createdBy);
        groupPanel.add(firstStudentName);
        groupPanel.add(secondStudentName);
        groupPanel.add(thirdStudentName);
    }

    private void createLayout() {
        add(titlePanel,BorderLayout.NORTH);
        add(groupPanel,BorderLayout.SOUTH);
        add(launcherPanel,BorderLayout.CENTER);
        setVisible(true);
    }

    private void setLauncherButton() {
        launcherButton = new JButton("Launch The Game");
        launcherButton.addActionListener(this);
        launcherPanel = new JPanel();
        launcherPanel.add(launcherButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==launcherButton){
            dispose(); // close the frame while opening the other frame
            new Hangman();
        }
    }

    public static void main(String args[]){
        new LaunchPage();
    }
}
