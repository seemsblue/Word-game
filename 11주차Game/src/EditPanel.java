import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditPanel extends JPanel{
	private JTextField wordInput = new JTextField(10);
	private TextSource textSource = null;
	
	public EditPanel(TextSource textSource) {
		this.textSource =textSource;
		setBackground(Color.CYAN);
		
		add(wordInput);
		JButton save = new JButton("Save");
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textSource.addText("dd");	//임시
			}
			});
		add(save);
		add(new JButton("Save As"));

	}
}
