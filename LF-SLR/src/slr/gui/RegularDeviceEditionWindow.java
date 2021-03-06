package slr.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import slr.control.UIController;
import slr.expression.RegularExpression;

/**
 * Janela de edição de dispositivos regulares.
 */
public class RegularDeviceEditionWindow extends JFrame {

	private static final long serialVersionUID = 4660641017859238291L;
	private static final String REGULAR_EXPRESSION_TIP = "Insira uma expressão regular " +
			"utilizando os símbolos (  )  +  *  ?  |  .  a-z  0-9  " + RegularExpression.EPSILON +
			".\nQuebras de linha são consideradas concatenação.\nExemplo: (ab)+ | b(c?(aa)*)";
	private static final String REGULAR_GRAMMAR_TIP = "Insira as produções da gramática " +
			"regular.\nQuebras de linha definem o fim de uma " +
			"produção. O primeiro símbolo não terminal será o símbolo inicial da gramática. " +
			"Utilize os símbolos não-terminais A-Z e terminais " +
			"a-z, 0-9, " + RegularExpression.EPSILON + ".\nExemplo:\nS -> aS | bB | a | b\nB -> bB | b";
    private JButton btnCancel;
    private ButtonGroup btnGroupRegularDeviceType;
    private JButton btnOk;
    private JPanel panelDescription;
    private JPanel panelDeviceType;
    private JPanel panelTip;
    private JRadioButton radBtnRegularExpression;
    private JRadioButton radBtnRegularGrammar;
    private JScrollPane scrollPaneDescription;
    private JScrollPane scrollPaneTip;
    private JTextArea textAreaDescription;
    private JTextArea textAreaTip;
	private UIController uiController;
	private boolean isEditionMode;
	private String regularDeviceLabel;
	
    public RegularDeviceEditionWindow(final UIController uiController) {
		this.uiController = uiController;
		this.isEditionMode = false;
		this.regularDeviceLabel = "";
        initComponents();
		this.setLocationRelativeTo(null);
    }

    private void initComponents() {

        btnGroupRegularDeviceType = new ButtonGroup();
        panelDeviceType = new JPanel();
        radBtnRegularExpression = new JRadioButton();
        radBtnRegularGrammar = new JRadioButton();
        panelDescription = new JPanel();
        scrollPaneDescription = new JScrollPane();
        textAreaDescription = new JTextArea();
        panelTip = new JPanel();
        scrollPaneTip = new JScrollPane();
        textAreaTip = new JTextArea(REGULAR_EXPRESSION_TIP);
        btnOk = new JButton();
        btnCancel = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Inserção/Edição de Dispositivos Regulares");
        setResizable(false);

        panelDeviceType.setBorder(BorderFactory.createTitledBorder("Tipo do Dispositivo"));

        btnGroupRegularDeviceType.add(radBtnRegularExpression);
        radBtnRegularExpression.setSelected(true);
        radBtnRegularExpression.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				RegularDeviceEditionWindow.this.deviceTypeChanged(
						RegularDeviceEditionWindow.this.radBtnRegularExpression.isSelected());
				
			}
		});
        radBtnRegularExpression.setText("Expressão Regular");

        btnGroupRegularDeviceType.add(radBtnRegularGrammar);
        radBtnRegularGrammar.setText("Gramática Regular");

        GroupLayout panelDeviceTypeLayout = new GroupLayout(panelDeviceType);
        panelDeviceType.setLayout(panelDeviceTypeLayout);
        panelDeviceTypeLayout.setHorizontalGroup(
            panelDeviceTypeLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelDeviceTypeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(radBtnRegularExpression)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radBtnRegularGrammar)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDeviceTypeLayout.setVerticalGroup(
            panelDeviceTypeLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelDeviceTypeLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(radBtnRegularExpression)
                .addComponent(radBtnRegularGrammar))
        );

        panelDescription.setBorder(BorderFactory.createTitledBorder("Descrição Textual"));

        scrollPaneDescription.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        textAreaDescription.setColumns(20);
        textAreaDescription.setRows(5);
        textAreaDescription.setMargin(new java.awt.Insets(3, 3, 3, 3));
        scrollPaneDescription.setViewportView(textAreaDescription);

        GroupLayout panelDescriptionLayout = new GroupLayout(panelDescription);
        panelDescription.setLayout(panelDescriptionLayout);
        panelDescriptionLayout.setHorizontalGroup(
            panelDescriptionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelDescriptionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneDescription)
                .addContainerGap())
        );
        panelDescriptionLayout.setVerticalGroup(
            panelDescriptionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelDescriptionLayout.createSequentialGroup()
                .addComponent(scrollPaneDescription, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelTip.setBorder(BorderFactory.createTitledBorder("Dica"));

        scrollPaneTip.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        textAreaTip.setEditable(false);
        textAreaTip.setColumns(20);
        textAreaTip.setLineWrap(true);
        textAreaTip.setRows(5);
        textAreaTip.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        textAreaTip.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        textAreaTip.setMargin(new java.awt.Insets(3, 3, 3, 3));
        scrollPaneTip.setViewportView(textAreaTip);

        GroupLayout panelTipLayout = new GroupLayout(panelTip);
        panelTip.setLayout(panelTipLayout);
        panelTipLayout.setHorizontalGroup(
            panelTipLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, panelTipLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneTip)
                .addContainerGap())
        );
        panelTipLayout.setVerticalGroup(
            panelTipLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelTipLayout.createSequentialGroup()
                .addComponent(scrollPaneTip, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        btnOk.setText("Ok");
        btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegularDeviceEditionWindow.this.insertUpdateRegularDevice();
			}
		});

        btnCancel.setText("Cancelar");
        btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegularDeviceEditionWindow.this.dispose();
			}
		});

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelDeviceType, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDescription, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelTip, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelDeviceType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDescription, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk)
                    .addComponent(btnCancel))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }
    
    public void setDeviceType(boolean isRegularExpression) {
    	this.radBtnRegularExpression.setSelected(isRegularExpression);
    	this.radBtnRegularGrammar.setSelected(!isRegularExpression);
    }
    
    public void setDeviceDescription(final String text) {
    	this.textAreaDescription.setText(text);
    }

    public void setDeviceLabel(final String label) {
    	this.regularDeviceLabel = label;
    }
    
    public void setEditionMode(boolean isEditionMode) {
    	this.isEditionMode = isEditionMode;
    }

    private void deviceTypeChanged(boolean isRegularExpression) {
    	if(isRegularExpression) {
    		this.textAreaTip.setText(REGULAR_EXPRESSION_TIP);
    	} else {
    		this.textAreaTip.setText(REGULAR_GRAMMAR_TIP);    		
    	}
    	this.textAreaTip.setCaretPosition(0);
    }
    
    private void insertUpdateRegularDevice() {
    	if(!this.isEditionMode) {
    		this.uiController.insertRegularDevice(this.radBtnRegularExpression.isSelected(),
    				this.textAreaDescription.getText());
    	} else {
    		if(!this.regularDeviceLabel.substring(6).equals(this.textAreaDescription.getText())) {
    			this.uiController.updateRegularDevice(this.radBtnRegularExpression.isSelected(),
    					this.regularDeviceLabel, this.textAreaDescription.getText());
    		} else {
    			this.uiController.disposeRegularDeviceEditionWindow();
    		}
    	}
    }
    
}
