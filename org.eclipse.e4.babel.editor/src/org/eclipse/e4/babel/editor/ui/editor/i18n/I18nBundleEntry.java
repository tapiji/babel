package org.eclipse.e4.babel.editor.ui.editor.i18n;

import javax.inject.Inject;

import org.eclipse.core.commands.Command;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipselabs.e4.tapiji.logger.Log;
import org.eclipselabs.e4.tapiji.resource.TapijiResourceConstants;

public final class I18nBundleEntry extends Composite
		implements KeyListener, TraverseListener, SelectionListener, FocusListener, MouseListener {

	private static final int UNDO_LEVEL = 20;
	private static final String TAG = I18nBundleEntry.class.getSimpleName();
	private TextViewerUndoManager undoManager = new TextViewerUndoManager(UNDO_LEVEL);
	private TextViewer textView;
	private IBundleEntryComposite listener;
	private GridData textViewStyleData;
	private IBabelResourceProvider resourceProvider;
	private Label expandIcon;
	private StyledText textViewStyle;
	private Composite toolbar;
 
	public interface IBundleEntryComposite {

		void onLocaleClick();

		void setNextFocusDown();

		void setNextFocusUp();

		void onFocusChange(I18nBundleEntry bundleEntryComposite);

	}

	private I18nBundleEntry(final Composite parent, final IBabelResourceProvider resourceProvider, final int style) {
		super(parent, style);
		this.resourceProvider = resourceProvider;

		final GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.horizontalSpacing = 0;
		gridLayout.verticalSpacing = 2;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;

		setLayout(gridLayout);
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createView();
	}

	private void createView() {
		toolbar = new Composite(this, SWT.NONE);
		final GridLayout toolbarLayout = new GridLayout();
		toolbarLayout.numColumns = 6;
		toolbarLayout.horizontalSpacing = 5;
		toolbarLayout.verticalSpacing = 0;
		toolbarLayout.marginWidth = 0;
		toolbarLayout.marginHeight = 0;
		toolbar.setLayout(toolbarLayout);
		toolbar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		collapseExpandIcon(toolbar);
		localeNameLabel("Albanien", toolbar);
		localeImageLabel(toolbar);
		duplicateButton(toolbar);
		uncommentButton(toolbar);
		goToButton(toolbar);

		textViewer();
	}

	private void textViewer() {
		textView = new TextViewer(this, SWT.MULTI | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		textView.setDocument(new Document());
		textView.setUndoManager(undoManager);
		textView.activatePlugins();

		textViewStyle = textView.getTextWidget();
		textViewStyleData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		textViewStyleData.minimumHeight = 40;

		textViewStyle.setLayoutData(textViewStyleData);
		textViewStyle.addFocusListener(this);
		textViewStyle.addTraverseListener(this);
		textViewStyle.addKeyListener(this);
	}

	private void duplicateButton(Composite toolBar) {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.END;
		gridData.grabExcessHorizontalSpace = true;
		Button button = new Button(toolBar, SWT.TOGGLE);
		button.setText("=");
		button.setLayoutData(gridData);
		button.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				System.out.println("Duplicate button click " + event);

			}
		});
	}

	private void uncommentButton(Composite toolBar) {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.END;
		Button button = new Button(toolBar, SWT.TOGGLE);
		button.setText("#");
		button.setLayoutData(gridData);
		button.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				System.out.println("Uncomment button click" + event);

			}
		});
	}

	private void goToButton(Composite toolBar) {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.END;
		Button button = new Button(toolBar, SWT.ARROW | SWT.RIGHT);
		button.setText("");
		button.setToolTipText("Go to the corresponding property file.");
		button.setLayoutData(gridData);
		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Go to button click " + e);
				// todo redirect to property file
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// no-op
			}
		});
	}

	private void localeNameLabel(final String string, Composite toolBar) {
		final Label localeName = new Label(toolBar, SWT.NONE);
		FontDescriptor boldDescriptor = FontDescriptor.createFrom(localeName.getFont()).setStyle(SWT.BOLD);
		Font boldFont = boldDescriptor.createFont(localeName.getDisplay());
		localeName.setFont(boldFont);
		localeName.setText("Albanien");
	}

	private void localeImageLabel(Composite toolBar) {
		final Label localeImage = new Label(toolBar, SWT.NONE);
		localeImage.setImage(resourceProvider.loadImage(TapijiResourceConstants.IMG_FOLDER_FLAGS + "AT.png"));
	}

	private void collapseExpandIcon(Composite toolBar) {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.END;
		this.expandIcon = new Label(toolBar, SWT.FLAT);
		this.expandIcon.setLayoutData(gridData);
		this.expandIcon.setImage(resourceProvider.loadImage(TapijiResourceConstants.IMG_COLLAPSE));
		this.expandIcon.addMouseListener(this);
		this.expandIcon.setToolTipText("Expand/Collapse");
	}

	private static boolean isKeyCombination(final KeyEvent event, final int mask, final int keyCode) {
		return ((event.keyCode == keyCode) && (event.stateMask == mask));
	}

	@Override
	public void keyPressed(final KeyEvent event) {
	}

	@Override
	public void keyReleased(final KeyEvent event) {
		Log.d(TAG, "keyReleased: " + event);
		if (isKeyCombination(event, SWT.CTRL, 'z')) {
			undoManager.undo();
		} else if (isKeyCombination(event, SWT.CTRL, 'y')) {
			undoManager.redo();
		} else if (isKeyCombination(event, SWT.CTRL, 'a')) {
			textView.setSelectedRange(0, textView.getDocument().getLength());
		} else {

		}
	}

	@Override
	public void keyTraversed(final TraverseEvent event) {
		Log.d(TAG, "keyTraversed: " + event.toString());
		if (listener != null) {
			if (event.character == SWT.TAB) {
				event.doit = true;
				event.detail = SWT.TRAVERSE_NONE;
				if (event.stateMask == 0) {
					listener.setNextFocusDown();
				} else if (event.stateMask == SWT.SHIFT) {
					listener.setNextFocusUp();
				}
			} else if ((event.keyCode == SWT.ARROW_DOWN) && (event.stateMask == SWT.CTRL)) {
				event.doit = true;
				event.detail = SWT.TRAVERSE_NONE;
			} else if ((event.keyCode == SWT.ARROW_UP) && (event.stateMask == SWT.CTRL)) {
				event.doit = true;
				event.detail = SWT.TRAVERSE_NONE;
			}
		}
	}

	public static I18nBundleEntry create(final Composite parent, final IBabelResourceProvider resourceProvider) {
		return new I18nBundleEntry(parent, resourceProvider, SWT.NONE);
	}

	private void expandTextView(final boolean expand) {
		if (expand) {
			GridData gridData = ((GridData) textViewStyle.getLayoutData());
			gridData.verticalAlignment = SWT.BEGINNING;
			gridData.grabExcessVerticalSpace = false;
			textViewStyle.setLayoutData(gridData);

			gridData = (GridData) getLayoutData();

			gridData.heightHint = toolbar.getSize().y;
			gridData.verticalAlignment = SWT.BEGINNING;
			gridData.grabExcessVerticalSpace = false;
			setLayoutData(gridData);

			getParent().pack();
			getParent().layout(true, true);
			layout(true, true);
			textView.getTextWidget().setVisible(false);
			expandIcon.setImage(resourceProvider.loadImage(TapijiResourceConstants.IMG_EXPAND));
		} else {
			GridData gridData = new GridData();
			gridData.verticalAlignment = SWT.FILL;
			gridData.grabExcessVerticalSpace = true;
			gridData.horizontalAlignment = SWT.FILL;
			gridData.grabExcessHorizontalSpace = true;
			textViewStyle.setLayoutData(gridData);

			GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
			gridData.heightHint = 40;
			setLayoutData(gd);
			getParent().pack();
			getParent().layout(true, true);
			layout(true, true);
			textView.getTextWidget().setVisible(true);
			expandIcon.setImage(resourceProvider.loadImage(TapijiResourceConstants.IMG_COLLAPSE));
		}
	}

	@Override
	public void widgetSelected(final SelectionEvent event) {
		Log.d(TAG, "widgetSelected: " + event.toString());
		if (null != listener) {
			listener.onLocaleClick();
		}
	}

	@Override
	public void widgetDefaultSelected(final SelectionEvent e) {
	}

	public void addListener(final IBundleEntryComposite entryListener) {
		if (null != entryListener) {
			listener = entryListener;
		}
	}

	@Override
	public void focusGained(final FocusEvent event) {
		Log.d(TAG, "focusGained: " + event.toString());
		if (null != listener) {
			listener.onFocusChange(this);
		}
	}

	@Override
	public void focusLost(final FocusEvent e) {
	}

	public void setFocusTextView() {
		if (null != textView) {
			final StyledText styledText = textView.getTextWidget();
			styledText.setFocus();
		}
	}

	@Override
	public void mouseDoubleClick(final MouseEvent e) {

	}

	@Override
	public void mouseDown(final MouseEvent e) {
	}

	@Override
	public void mouseUp(final MouseEvent e) {
		expandTextView(textView.getTextWidget().isVisible());
	}
}
