package dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class DialogRectangle extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textXcor;
	private JTextField textYcor;
	private JTextField textHeight;
	private JTextField textWidth;
	private boolean ok;
	private Color fillColor;
	private Color edgeColor;
	private JButton btnEdgeColor;
	private JButton btnFillColor;
	JLabel lblInsert;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DialogRectangle dlg = new DialogRectangle();
			dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dlg.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
		 * Create the dialog.
		 */
		public DialogRectangle() {
			setBounds(100, 100, 450, 300);
			getContentPane().setLayout(new BorderLayout());
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			setTitle("Rectangle");
			contentPanel.setBackground(Color.LIGHT_GRAY);
			this.setModal(true);
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			JLabel lblUpperLeftPointX = new JLabel("UpperLeftPointX:");
			JLabel lblUpperLeftPointY = new JLabel("UpperLeftPointY:");
			JLabel lblHeight = new JLabel("Height:");
			JLabel lblWidth = new JLabel("Width:");
			textXcor = new JTextField();
			textXcor.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					char t=e.getKeyChar();
					if(t=='-') {
						e.consume();
						getToolkit().beep();
					}
				}
			});
			textXcor.setColumns(10);
			textYcor = new JTextField();
			textYcor.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					char t=e.getKeyChar();
					if(t=='-') {
						e.consume();
						getToolkit().beep();
					}
				}
			});
			textYcor.setText("");
			textYcor.setColumns(10);
			textHeight = new JTextField();
			textHeight.setText("");
			textHeight.setColumns(10);
			textWidth = new JTextField();
			textWidth.setText("");
			textWidth.setColumns(10);
			
			btnFillColor = new JButton("Fill color");
		    btnFillColor.setBackground(fillColor);
			btnFillColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fillColor=JColorChooser.showDialog(null, "Choose a inner color!", fillColor);
					btnFillColor.setBackground(fillColor);
				}
			});
			
			btnEdgeColor = new JButton("Edge color");
			btnEdgeColor.setBackground(edgeColor);
			btnEdgeColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					edgeColor=JColorChooser.showDialog(null, "Choose a outer color!",edgeColor);
					btnEdgeColor.setBackground(edgeColor);
				}
			});
			GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
			gl_contentPanel.setHorizontalGroup(
				gl_contentPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPanel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addComponent(lblUpperLeftPointX)
								.addGap(18)
								.addComponent(textXcor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
									.addComponent(lblUpperLeftPointY)
									.addComponent(lblHeight)
									.addComponent(lblWidth))
								.addGap(18)
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
									.addComponent(textWidth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(textHeight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(textYcor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addComponent(btnFillColor)
							.addComponent(btnEdgeColor))
						.addContainerGap(228, Short.MAX_VALUE))
			);
			gl_contentPanel.setVerticalGroup(
				gl_contentPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPanel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblUpperLeftPointX)
							.addComponent(textXcor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblUpperLeftPointY)
							.addComponent(textYcor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblHeight)
							.addComponent(textHeight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
							.addComponent(lblWidth)
							.addComponent(textWidth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnFillColor)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnEdgeColor)
						.addContainerGap(51, Short.MAX_VALUE))
			);
			contentPanel.setLayout(gl_contentPanel);
			{
				JPanel buttonPane = new JPanel();
				buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
				getContentPane().add(buttonPane, BorderLayout.SOUTH);
				{
					JButton okButton = new JButton("OK");
					okButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try
							{
							setOk(true);
							dispose();
							}
							catch(NumberFormatException ex)
							{
								JOptionPane.showMessageDialog(new JFrame(), "Popunite sva polja ili proverite tip podataka koji ste uneli!", "Greska", JOptionPane.WARNING_MESSAGE);
							
							} catch (Exception e1) {
								
								JOptionPane.showMessageDialog(new JFrame(), "Visina i sirina moraju biti pozitivne!", "Greska", JOptionPane.WARNING_MESSAGE);
							}
						}
					});
					okButton.setActionCommand("OK");
					buttonPane.add(okButton);
					getRootPane().setDefaultButton(okButton);
				}
				{
					JButton cancelButton = new JButton("Cancel");
					cancelButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							setOk(false);
							dispose();
						}
					});
					cancelButton.setActionCommand("Cancel");
					buttonPane.add(cancelButton);
				}
			}
		}

	public Color getEdgeColor() {
		return edgeColor;
	}

	public void setEdgeColor(Color edgeColor) {
		this.edgeColor = edgeColor;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public void setTextHeight(String textHeight) {
		this.textHeight.setText(textHeight);
	}

	public void setTextWidth(String textWidth) {
		this.textWidth.setText(textWidth);
	}

	public String getTextXcor() {
		return textXcor.getText();
	}

	public void setTextXcor(String textXcor) {
		this.textXcor.setText(textXcor);
	}

	public String getTextYcor() {
		return textYcor.getText();
	}

	public void setTextYcor(String textYcor) {
		this.textYcor.setText(textYcor);
	}

	public String getTextHeight() {
		return textHeight.getText();
	}

	public String getTextWidth() {
		return textWidth.getText();
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public void setTextXcorEditable(boolean b) {
		this.textXcor.setEditable(b);
	}

	public void setTextYcorEditable(boolean b) {
		this.textYcor.setEditable(b);
	}

	public void setTextHeightEditable(boolean b) {
		this.textHeight.setEditable(b);
	}

	public void setTextWidthEditable(boolean b) {
		this.textWidth.setEditable(b);
	}

	public JButton getBtnEdgeColor() {
		return btnEdgeColor;
	}

	public void setBtnEdgeColor(JButton btnEdgeColor) {
		this.btnEdgeColor = btnEdgeColor;
	}

	public JButton getBtnFillColor() {
		return btnFillColor;
	}

	public void setBtnFillColor(JButton btnFillColor) {
		this.btnFillColor = btnFillColor;
	}
}