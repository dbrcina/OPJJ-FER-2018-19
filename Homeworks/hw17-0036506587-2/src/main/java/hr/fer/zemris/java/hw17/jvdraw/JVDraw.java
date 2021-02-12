package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw17.jvdraw.actions.ExitAction;
import hr.fer.zemris.java.hw17.jvdraw.actions.ExportAction;
import hr.fer.zemris.java.hw17.jvdraw.actions.OpenAction;
import hr.fer.zemris.java.hw17.jvdraw.actions.SaveAction;
import hr.fer.zemris.java.hw17.jvdraw.actions.SaveAsAction;
import hr.fer.zemris.java.hw17.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.color.JColorInfo;
import hr.fer.zemris.java.hw17.jvdraw.geobject.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geobject.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModelImpl;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingObjectListModel;
import hr.fer.zemris.java.hw17.jvdraw.tool.ToolButton;
import hr.fer.zemris.java.hw17.jvdraw.tool.CircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tool.FilledCircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tool.LineTool;
import hr.fer.zemris.java.hw17.jvdraw.tool.Tool;

/**
 * An implementation of simple <b><i>Paint</i></b> program.
 * 
 * <p>
 * It supports painting regular lines, circles and filled circles whose button
 * tools are provided in toolbar as mutually exclusive buttons which means that
 * only one object at the time can be paintet while others can't.
 * </p>
 * 
 * <p>
 * Its content is always dynamically rendered. There is a scrollable list
 * ({@link JList}) that keeps track about every change that occured like adding
 * new object, removing existing object etc. It's also clickable which means
 * that one can change object's properties, change their order or deleting them
 * from the canvas anytime.
 * </p>
 * 
 * <p>
 * There are few actions that are provided in menu of this program like
 * <b><i>Open, Save, SaveAs, Export</i></b> and <b><i>Exit</i></b> which are
 * self-explanatory.
 * </p>
 * 
 * @author dbrcina
 *
 */
public class JVDraw extends JFrame {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Path of currently opened file.
	 */
	private Path path;
	/**
	 * Instance of {@link DrawingModel}.
	 */
	private DrawingModel model;
	/**
	 * Instance of {@link JDrawingCanvas}.
	 */
	private JDrawingCanvas canvas;
	/**
	 * Instance of {@link JList}.
	 */
	private JList<GeometricalObject> jList;
	/**
	 * Foreground {@link JColorArea} component;
	 */
	private JColorArea fgColor;
	/**
	 * Background {@link JColorArea} component;
	 */
	private JColorArea bgColor;
	/**
	 * Line {@link ToolButton} component;
	 */
	private ToolButton lineBtn;
	/**
	 * Circle {@link ToolButton} component;
	 */
	private ToolButton circleBtn;
	/**
	 * Filled circle {@link ToolButton} component;
	 */
	private ToolButton filledCircleBtn;
	/**
	 * Instance of {@link Tool}.
	 */
	private Tool currentTool;
	/**
	 * Color info component.
	 */
	private JColorInfo infoLabel;
	/**
	 * Representation of {@link OpenAction} action.
	 */
	private Action openAction;
	/**
	 * Representation of {@link SaveAction} action.
	 */
	private Action saveAction;
	/**
	 * Representation of {@link SaveAsAction} action.
	 */
	private Action saveAsAction;
	/**
	 * Representation of {@link ExportAction} action.
	 */
	private Action exportAction;
	/**
	 * Representation of {@link ExitAction} action.
	 */
	private Action exitAction;

	/**
	 * Getter for currently opened file's path.
	 * 
	 * @return path.
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Setter for currently opened file's path.
	 * 
	 * @param path path-
	 */
	public void setPath(Path path) {
		this.path = path;
	}

	/**
	 * Getter for {@link DrawingModel} model of this program.
	 * 
	 * @return model.
	 */
	public DrawingModel getModel() {
		return model;
	}

	/**
	 * Getter for current tool of this program.
	 * 
	 * @return current tool.
	 */
	public Tool getCurrentTool() {
		return currentTool;
	}

	/**
	 * Getter for save action.
	 * 
	 * @return save action.
	 */
	public Action getSaveAction() {
		return saveAction;
	}

	public JVDraw() {
		initProperties();
		initMetadata();
		initGUI();
		initListeners();
	}

	/**
	 * Helper method used for initialization of this program properties.
	 */
	private void initProperties() {
		model = new DrawingModelImpl();
		canvas = new JDrawingCanvas(this);
		jList = new JList<>(new DrawingObjectListModel(model));
		fgColor = new JColorArea(Color.RED, "foreground");
		bgColor = new JColorArea(Color.BLUE, "background");
		lineBtn = new ToolButton("Line");
		circleBtn = new ToolButton("Circle");
		filledCircleBtn = new ToolButton("Filled circle");
		currentTool = new LineTool(model, canvas, fgColor);
		infoLabel = new JColorInfo(fgColor, bgColor);
		openAction = new OpenAction(this);
		saveAction = new SaveAction(this);
		saveAsAction = new SaveAsAction(this);
		exportAction = new ExportAction(this);
		exitAction = new ExitAction(this);
	}

	/**
	 * Helper method used for initialization of some metadata of this program.
	 */
	private void initMetadata() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setTitle("JVDraw");
		setLocation(100, 100);
		setSize(1000, 700);
	}

	private void initGUI() {
		setJMenuBar(createMenuBar());
		Container cp = getContentPane();
		cp.add(createToolBar(), BorderLayout.PAGE_START);
		cp.add(canvas, BorderLayout.CENTER);
		cp.add(createJList(), BorderLayout.EAST);
		cp.add(infoLabel, BorderLayout.PAGE_END);
	}

	/**
	 * Helper method used for initialization of all listeners that are being used in
	 * this program.
	 */
	private void initListeners() {
		windowListener();
		buttonListeners();
		jListListeners();
	}

	/**
	 * Helper method used for creation of {@link JMenuBar}.
	 * 
	 * @return new instance of {@link JMenuBar}.
	 */
	private JMenuBar createMenuBar() {
		JMenuBar mb = new JMenuBar();
		JMenu file = new JMenu("File");
		file.add(new JMenuItem(openAction));
		file.add(new JMenuItem(saveAction));
		file.add(new JMenuItem(saveAsAction));
		file.add(new JSeparator());
		file.add(new JMenuItem(exportAction));
		file.add(new JSeparator());
		file.add(new JMenuItem(exitAction));
		mb.add(file);
		return mb;
	}

	/**
	 * Helper method used for creation of {@link JToolBar}.
	 * 
	 * @return new instance of {@link JToolBar}.
	 */
	private JToolBar createToolBar() {
		JToolBar tb = new JToolBar();
		tb.setLayout(new FlowLayout(FlowLayout.LEFT));
		tb.setBorder(BorderFactory.createStrokeBorder(new BasicStroke()));
		tb.add(fgColor);
		tb.add(bgColor);
		buttonGroup(tb);
		return tb;

	}

	/**
	 * Helper method used initialization of mutually exclusive buttons. They are
	 * collected into one {@link ButtonGroup} which is then added into toolbar.
	 * 
	 * @param tb toolbar.
	 */
	private void buttonGroup(JToolBar tb) {
		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(lineBtn);
		btnGroup.add(circleBtn);
		btnGroup.add(filledCircleBtn);
		tb.add(lineBtn);
		tb.add(circleBtn);
		tb.add(filledCircleBtn);
	}

	/**
	 * Helper method used for creation of {@link JList}. List is <i>"masked"</i>
	 * inside an instance of {@link JScrollPane}.
	 * 
	 * @return new instance of {@link JScrollPane} with {@link JList} in it.
	 */
	private JScrollPane createJList() {
		jList.setFixedCellWidth((int) (getWidth() * 0.22));
		JScrollPane jp = new JScrollPane(jList);
		jp.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.BLACK));
		return jp;
	}

	/**
	 * Initialization of {@link WindowListener}.
	 */
	private void windowListener() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitAction.actionPerformed(new ActionEvent(e.getWindow(), ActionEvent.ACTION_PERFORMED, "Exit"));
			}
		});
	}

	/**
	 * Initialization of {@link ToolButton} listeners.
	 */
	private void buttonListeners() {
		lineBtn.setSelected(true);
		lineBtn.addActionListener(l -> currentTool = new LineTool(model, canvas, fgColor));
		circleBtn.addActionListener(l -> currentTool = new CircleTool(model, canvas, fgColor));
		filledCircleBtn.addActionListener(l -> currentTool = new FilledCircleTool(model, canvas, fgColor, bgColor));
	}

	/**
	 * Initialization of {@link JList} listeners.
	 */
	private void jListListeners() {
		mouseListener();
		keyListener();
		modelListener();
	}

	/**
	 * Initialization of {@link MouseListener} on {@link JList}.<br>
	 * When an element from list is double clicked, an editor panel for that element
	 * is opened.
	 */
	private void mouseListener() {
		jList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = jList.getSelectedIndex();
					if (index >= 0) {
						while (true) {
							GeometricalObjectEditor editor = model.getObject(index).createGeometricalObjectEditor();
							int answer = JOptionPane.showConfirmDialog(
									JVDraw.this, 
									editor, 
									"Edit properties",
									JOptionPane.OK_CANCEL_OPTION
							);
							if (answer == JOptionPane.CANCEL_OPTION) {
								break;
							}
							try {
								editor.checkEditing();
								editor.acceptEditing();
								break;
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(JVDraw.this, ex.getMessage());
							}
						}
						jList.clearSelection();
					}
				}
			}
		});

	}

	/**
	 * Initialization of {@link KeyListener} on {@link JList}.<br>
	 * <li>When key <i>"DEL"</i> is pressed, a selected element is removed from list
	 * and from drawing model.</li>
	 * <li>When key <i>"+"</i> is pressed, a position of selected element is shifted
	 * with the next one(if it exist, i.e. if index + 1 is valid).</li>
	 * <li>When key <i>"-"</i> is pressed, the results are equal as with <i>"+"</i>
	 * and the only difference is that with <i>"-"</i> key, a position of selected
	 * element is shifted with the previous one.</li>
	 */
	private void keyListener() {
		jList.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int index = jList.getSelectedIndex();
				switch (e.getKeyCode()) {
				case KeyEvent.VK_DELETE:
					model.remove(model.getObject(index));
					break;
				case KeyEvent.VK_PLUS:
					model.changeOrder(model.getObject(index), 1);
					break;
				case KeyEvent.VK_MINUS:
					model.changeOrder(model.getObject(index), -1);
					break;
				default:
					break;
				}
				jList.clearSelection();
			}
		});

	}

	/**
	 * Initialization of {@link DrawingModelListener}.<br>
	 * It regulates whether some actions are enabled or not as determined by
	 * previous actions.
	 */
	private void modelListener() {
		model.addDrawingModelListener(new DrawingModelListener() {
			@Override
			public void objectsRemoved(DrawingModel source, int index0, int index1) {
				saveAction.setEnabled(true);
				if (source.getSize() == 0) {
					exportAction.setEnabled(false);
				}
			}

			@Override
			public void objectsChanged(DrawingModel source, int index0, int index1) {
				saveAction.setEnabled(true);
			}

			@Override
			public void objectsAdded(DrawingModel source, int index0, int index1) {
				saveAction.setEnabled(true);
				exportAction.setEnabled(true);
			}
		});
	}
	
	/**
	 * Main entry of this program.
	 * 
	 * @param args arguments through command line.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JVDraw().setVisible(true));
	}
}
