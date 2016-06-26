package org.eclipse.e4.babel.editor.ui.editor.i18n.pageentry;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Locale;

import org.eclipse.e4.babel.core.api.IResourceManager;
import org.eclipse.e4.babel.core.preference.PropertyPreferences;
import org.eclipse.e4.babel.core.utils.UIUtils;
import org.eclipse.e4.babel.editor.text.document.SourceViewerDocument;
import org.eclipse.e4.babel.editor.ui.editor.i18n.page.I18nPageContract;
import org.eclipse.e4.babel.editor.ui.editor.i18n.page.I18nPagePresenter.LocalBehaviour;
import org.eclipse.e4.babel.editor.ui.editor.i18n.page.I18nPageView;
import org.eclipse.e4.babel.editor.ui.editor.i18n.pageentry.I18nPageEntryContract.Presenter;
import org.eclipse.e4.babel.resource.BabelResourceConstants;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextListener;
import org.eclipse.jface.text.TextEvent;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipselabs.e4.tapiji.logger.Log;
import org.eclipselabs.e4.tapiji.resource.TapijiResourceConstants;

public final class I18nPageEntryView extends Composite implements KeyListener, TraverseListener, FocusListener,
		MouseListener, I18nPageEntryContract.View, ITextListener {

	private static final int UNDO_LEVEL = 20;
	private static final String TAG = I18nPageEntryView.class.getSimpleName();

	private TextViewerUndoManager undoManager = new TextViewerUndoManager(UNDO_LEVEL);
	private Collection<FocusListener> focusListeners = new LinkedList<FocusListener>();
	private TextViewer textView;

	private Label expandIcon;
	private StyledText textWidget;
	private Composite toolbar;
	private Presenter presenter;
	private Button goToButton;
	private Button duplicateButton;
	private Button similarButton;
	private String textBeforeUpdate;

	private I18nPageEntryView(final Composite parent, final int style) {
		super(parent, style);

		final GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.horizontalSpacing = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;

		setLayout(gridLayout);
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
	}

	private void onCreate() {
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
		localeNameLabel(toolbar);
		countryFlag(toolbar);
		duplicateButton(toolbar);
		similarButton(toolbar);
		goToButton(toolbar);

		textViewer();
	}

	private void textViewer() {
		textView = new TextViewer(this, SWT.MULTI | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		textView.setDocument(new Document());
		textView.setUndoManager(undoManager);
		textView.activatePlugins();
		textView.addTextListener(this);

		textWidget = textView.getTextWidget();
		final GridData textViewStyleData = new GridData(SWT.FILL, SWT.BEGINNING, true, true, 0, 0);
		textViewStyleData.minimumHeight = PropertyPreferences.getInstance().getI18nEditorHeight();
		textWidget.setLayoutData(textViewStyleData);
		textWidget.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent event) {
				textBeforeUpdate = textWidget.getText();
			}

			public void focusLost(FocusEvent event) {
				presenter.updateBundleOnChanges();
			}
		});
		textWidget.addTraverseListener(this);
		textWidget.addKeyListener(this);
		textWidget.addFocusListener(this);

	}

	private void duplicateButton(Composite toolBar) {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.END;
		gridData.grabExcessHorizontalSpace = true;
		duplicateButton = new Button(toolBar, SWT.TOGGLE);
		duplicateButton.setImage(presenter.loadImage(BabelResourceConstants.IMG_SIMILAR));
		duplicateButton.setLayoutData(gridData);
		duplicateButton.setVisible(false);
		duplicateButton.addListener(SWT.Selection, (e) -> {
			StringBuilder foundKeys = new StringBuilder();
			foundKeys.append("\n\n");
			presenter.getDuplicates().forEach(duplicate -> {
				foundKeys.append("        ");
				foundKeys.append(duplicate.getKey());
				foundKeys.append("\n");
			});
			showVisitorDialog("Gleiche(r) Wert(e) gefunden.",
					"Die Werte folgender Schl\u00FCssel sind innerhalb der Lokalen \"{1}\" beim Schl\u00FCssel \"{0}\" identisch:",
					foundKeys);
		});
	}

	private void similarButton(Composite toolBar) {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.END;
		similarButton = new Button(toolBar, SWT.TOGGLE);
		similarButton.setImage(presenter.loadImage(BabelResourceConstants.IMG_SIMILAR));
		similarButton.setLayoutData(gridData);
		similarButton.setVisible(false);
		similarButton.addListener(SWT.Selection, (e) -> {
			StringBuilder foundKeys = new StringBuilder();
			foundKeys.append("\n\n");
			presenter.getSimilars().forEach(similar -> {
				foundKeys.append("        ");
				foundKeys.append(similar.getKey());
				foundKeys.append("\n");
			});
			showVisitorDialog("Ähnliche Werte gefunden. Klicken für Details.",
					"Die Werte folgender Schl\u00FCssel sind innerhalb der Lokalen \"{1}\" beim Schl\u00FCssel \"{0}\" ähnlich:",
					foundKeys);
		});
	}

	private void showVisitorDialog(String head, String message, StringBuilder foundKeys) {
		final String body = MessageFormat.format(message, presenter.getActiveKey(),
				UIUtils.getDisplayName(presenter.getLocale())) + foundKeys.toString();
		MessageDialog.openInformation(getShell(), head, body);
	}

	private void goToButton(Composite toolBar) {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.END;

		this.goToButton = new Button(toolBar, SWT.ARROW | SWT.RIGHT);
		this.goToButton.setText("");
		this.goToButton.setToolTipText("Go to the corresponding property file.");
		this.goToButton.setLayoutData(gridData);
		this.goToButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				presenter.goToTab();
			}
		});
	}

	private void localeNameLabel(Composite toolBar) {
		final Label localeName = new Label(toolBar, SWT.NONE);
		FontDescriptor boldDescriptor = FontDescriptor.createFrom(localeName.getFont()).setStyle(SWT.BOLD);
		Font boldFont = boldDescriptor.createFont(localeName.getDisplay());
		localeName.setFont(boldFont);
		localeName.setText(UIUtils.getDisplayName(presenter.getLocale()));
	}

	private void countryFlag(Composite toolBar) {
		final Label localeImage = new Label(toolBar, SWT.NONE);
		final Locale countryLocale = presenter.getLocale();
		String countryCode = null;
		if (countryLocale != null && countryLocale.getCountry() != null) {
			countryCode = countryLocale.getCountry().toLowerCase();
		}

		if (countryCode != null && countryCode.length() > 0) {
			Image flag = presenter.loadImage(TapijiResourceConstants.IMG_FOLDER_FLAGS + countryCode + ".png");
			if (flag != null) {
				localeImage.setImage(flag);
			}
		}
	}

	private void collapseExpandIcon(Composite toolBar) {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.END;
		this.expandIcon = new Label(toolBar, SWT.FLAT);
		this.expandIcon.setLayoutData(gridData);
		this.expandIcon.setImage(presenter.loadImage(TapijiResourceConstants.IMG_COLLAPSE));
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
			StyledText eventBox = (StyledText) event.widget;
			SourceViewerDocument editor = presenter.getResourceManager().getSourceEditor(presenter.getLocale())
					.getDocument();
			// Text field has changed: make editor dirty if not already
			if (textBeforeUpdate != null && !textBeforeUpdate.equals(eventBox.getText())) {
				// Make the editor dirty if not already. If it is,
				// we wait until field focus lost (or save) to
				// update it completely.
				/*
				 * if (!editor.isDirty()) { int caretPosition =
				 * eventBox.getCaretOffset(); presenter.updateBundleOnChanges();
				 * eventBox.setSelection(caretPosition); }
				 */
			}
		}
	}

	@Override
	public void keyTraversed(final TraverseEvent event) {
		Log.d(TAG, "keyTraversed: " + event.toString());
		if (event.character == SWT.TAB && !PropertyPreferences.getInstance().isFieldTabInsert()) {
			event.doit = true;
			event.detail = SWT.TRAVERSE_NONE;
			if (event.stateMask == 0) {
				textView.setSelectedRange(0, 0);
				presenter.getI18nPageView().setNextFocusDown();
			} else if (event.stateMask == SWT.SHIFT) {
				textView.setSelectedRange(0, 0);
				presenter.getI18nPageView().setNextFocusUp();
			}
		} else if ((event.keyCode == SWT.ARROW_DOWN) && (event.stateMask == SWT.CTRL)) {
			event.doit = true;
			event.detail = SWT.TRAVERSE_NONE;
			presenter.getI18nPageView().getPresenter().selectNextTreeEntry();
		} else if ((event.keyCode == SWT.ARROW_UP) && (event.stateMask == SWT.CTRL)) {
			event.doit = true;
			event.detail = SWT.TRAVERSE_NONE;
			presenter.getI18nPageView().getPresenter().selectPreviousTreeEntry();
		}
	}

	private void expandTextView(final boolean expand) {
		GridData data = (GridData) textWidget.getLayoutData();
		if (expand) {
			data.exclude = expand;
			textWidget.setVisible(!expand);
			expandIcon.setImage(presenter.loadImage(TapijiResourceConstants.IMG_EXPAND));
		} else {
			data.exclude = expand;
			textWidget.setVisible(!expand);
			expandIcon.setImage(presenter.loadImage(TapijiResourceConstants.IMG_COLLAPSE));
		}
		presenter.getI18nPageView().refreshLayout();
	}

	@Override
	public void focusGained(final FocusEvent e) {
		e.widget = I18nPageEntryView.this;
		focusListeners.forEach(listener -> listener.focusGained(e));
	}

	@Override
	public void focusLost(final FocusEvent e) {
		e.widget = I18nPageEntryView.this;
		focusListeners.forEach(listener -> listener.focusLost(e));
	}

	@Override
	public void addFocusListener(FocusListener listener) {
		if (!focusListeners.contains(listener))
			focusListeners.add(listener);
	}

	@Override
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

	@Override
	public void updateEditorHeight() {
		((GridData) this.textWidget.getLayoutData()).minimumHeight = PropertyPreferences.getInstance()
				.getI18nEditorHeight();
		layout(true, true);
	}

	@Override
	public int getCoordinateY() {
		return getLocation().y;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		presenter.init();
	}

	public static I18nPageEntryView create(final Composite parent, Locale locale) {
		final I18nPageEntryView pageView = new I18nPageEntryView(((I18nPageContract.View)parent).getI18NPage(), SWT.NONE);
		I18nPageEntryPresenter.create(pageView, locale, (I18nPageContract.View) parent);
		pageView.onCreate();
		return pageView;
	}

	@Override
	public String getText() {
		return this.textView.getDocument().get();
	}

	@Override
	public void updateTextView(IDocument document, boolean enabled) {
		if (enabled) {
			this.textWidget
					.setEnabled(!presenter.getResourceManager().getSourceEditor(presenter.getLocale()).isReadOnly());
			this.textWidget.setEditable(true);
			this.textWidget.setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
			this.goToButton.setEnabled(true);
		} else {
			this.goToButton.setEnabled(false);
			this.duplicateButton.setVisible(false);
			this.similarButton.setVisible(false);
			this.textWidget.setEnabled(false);
			this.textView.setEditable(false);
			this.textWidget.setBackground(new Color(getDisplay(), 245, 245, 245));
		}
		this.textView.setDocument(document);
	}

	@Override
	public Presenter getPresenter() {
		return this.presenter;
	}

	@Override
	public void setDuplicateButtonVisibility(boolean visible) {
		this.duplicateButton.setVisible(visible);
	}

	@Override
	public void setSimilarButtonVisibility(boolean visible) {
		this.similarButton.setVisible(visible);
	}

	String _oldText = null;

	@Override
	public void textChanged(TextEvent event) {
	}

	@Override
	public void focusTextBox() {
		textWidget.setFocus();
		textView.setSelectedRange(0, textView.getDocument().getLength());
	}
	
	@Override
	public void dispose() {
		focusListeners.clear();
		focusListeners = null;
		undoManager = null;
		super.dispose();
	}

	@Override
	public void addLocalListener(LocalBehaviour localBehaviour) {
		addFocusListener(localBehaviour);
	}

}
