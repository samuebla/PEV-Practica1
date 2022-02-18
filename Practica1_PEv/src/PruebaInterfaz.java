import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Combo;

public class PruebaInterfaz extends Composite {
	private Text text;
	private Text text_1;
	private Text text_2;
	
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(1, false));

//		PruebaInterfaz ayMisChiles = new PruebaInterfaz(shell,SWT.NONE);

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public PruebaInterfaz(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(3, false));
		
		Label lblMarika = new Label(this, SWT.NONE);
		lblMarika.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblMarika.setText("Marika1");
		
		text = new Text(this, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label lblMarika_1 = new Label(this, SWT.NONE);
		lblMarika_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblMarika_1.setText("Marika2");
		
		text_1 = new Text(this, SWT.BORDER);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label lblUgaBuga = new Label(this, SWT.NONE);
		lblUgaBuga.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblUgaBuga.setText("Uga buga");
		
		text_2 = new Text(this, SWT.BORDER);
		text_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		new Label(this, SWT.NONE);
		
		Button btnCancel = new Button(this, SWT.NONE);
		btnCancel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnCancel.setText("Cancel");
		
		Button btnOk = new Button(this, SWT.NONE);
		btnOk.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnOk.setText("OK");
		
		Group grpGrupo = new Group(this, SWT.NONE);
		grpGrupo.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpGrupo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		grpGrupo.setText("Grupo");
		
		Slider slider = new Slider(grpGrupo, SWT.NONE);
		
		Combo combo = new Combo(grpGrupo, SWT.NONE);
		combo.setItems(new String[] {"wee", "wewe", "wewqeqwf2"});
		
		SashForm sashForm = new SashForm(this, SWT.NONE);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 3, 1));
		
		Composite composite_1 = new Composite(sashForm, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		DateTime dateTime = new DateTime(composite_1, SWT.BORDER | SWT.CALENDAR);
		
		Composite composite = new Composite(sashForm, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Browser browser = new Browser(composite, SWT.NONE);
		browser.setUrl("http://www.google.com");
		sashForm.setWeights(new int[] {1, 1});
		slider.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				grpGrupo.setText("Grupo "+ slider.getSelection());
			}
		});

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
