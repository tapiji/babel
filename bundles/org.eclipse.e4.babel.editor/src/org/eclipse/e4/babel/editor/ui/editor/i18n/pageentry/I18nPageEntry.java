package org.eclipse.e4.babel.editor.ui.editor.i18n.pageentry;

import java.util.Locale;

import org.eclipse.e4.babel.core.api.IResourceManager;
import org.eclipse.e4.babel.core.preference.PropertyPreferences;
import org.eclipse.e4.babel.core.utils.UIUtils;
import org.eclipse.e4.babel.editor.ui.editor.i18n.page.I18nPageView;
import org.eclipse.e4.babel.editor.model.bundle.BundleEntry;
import org.eclipse.e4.babel.editor.model.bundle.BundleGroup;
import org.eclipse.e4.babel.editor.text.model.SourceEditor;
import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract.View;
import org.eclipse.e4.babel.editor.ui.editor.i18n.page.I18nPageContract;
import org.eclipse.e4.babel.editor.ui.editor.i18n.pageentry.I18nPageEntryContract.Presenter;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
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
import org.eclipse.swt.events.SelectionListener;
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

public final class I18nPageEntry extends Composite implements KeyListener, TraverseListener, SelectionListener,
		FocusListener, MouseListener, I18nPageEntryContract.View {

	private static final int UNDO_LEVEL = 20;
	private static final String TAG = I18nPageEntry.class.getSimpleName();

	private TextViewerUndoManager undoManager = new TextViewerUndoManager(UNDO_LEVEL);
	private TextViewer textView;
	private I18nPageContract.View listener;
	private Label expandIcon;
	private StyledText textWidget;
	private Composite toolbar;
	private ScrolledComposite i18nPage;
	private Presenter presenter;

	private I18nPageEntry(final Composite parent, ScrolledComposite scrolled, final int style) {
		super(parent, style);

		this.i18nPage = scrolled;

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
		localeNameLabel("Albanien", toolbar);
		countryFlag(toolbar);
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

		textWidget = textView.getTextWidget();
		final GridData textViewStyleData = new GridData(SWT.FILL, SWT.BEGINNING, true, true, 0, 0);
		textViewStyleData.minimumHeight = PropertyPreferences.getInstance().getI18nEditorHeight();
		textWidget.setLayoutData(textViewStyleData);
		textWidget.addFocusListener(this);
		textWidget.addTraverseListener(this);
		textWidget.addKeyListener(this);
	}

	private void duplicateButton(Composite toolBar) {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.END;
		gridData.grabExcessHorizontalSpace = true;
		Button button = new Button(toolBar, SWT.TOGGLE);
		button.setText("=");
		button.setLayoutData(gridData);
		button.addListener(SWT.Selection, (e) -> System.out.println("Duplicate button click " + e));
	}

	private void uncommentButton(Composite toolBar) {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.END;
		Button button = new Button(toolBar, SWT.TOGGLE);
		button.setText("#");
		button.setLayoutData(gridData);
		button.addListener(SWT.Selection, (e) -> System.out.println("Uncomment button click" + e));
	}

	private void goToButton(Composite toolBar) {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.END;
		Button button = new Button(toolBar, SWT.ARROW | SWT.RIGHT);
		button.setText("");
		button.setToolTipText("Go to the corresponding property file.");
		button.setLayoutData(gridData);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				presenter.goToTab();
			}
		});
	}

	private void localeNameLabel(final String string, Composite toolBar) {
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
		((I18nPageView) i18nPage).refreshLayout();
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

	@Override
	public void addPageListener(final I18nPageContract.View pageListener) {
		if (null != pageListener) {
			listener = pageListener;
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
		((GridData) textWidget.getLayoutData()).minimumHeight = PropertyPreferences.getInstance().getI18nEditorHeight();
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

	public static I18nPageEntry create(final Composite parent, ScrolledComposite comp,
			final IBabelResourceProvider resourceProvider, IResourceManager resourceManager, Locale locale,
			View editorView) {
		final I18nPageEntry pageView = new I18nPageEntry(parent, comp, SWT.NONE);
		I18nPageEntryPresenter.create(pageView, resourceManager, resourceProvider, locale, editorView);
		pageView.onCreate();
		return pageView;
	}

	@Override
	public void refresh(String key) {
		BundleGroup bundleGroup = presenter.getResourceManager().getBundleGroup();

		IDocument document = new Document();
		if (key != null && bundleGroup.isKey(key)) {
			BundleEntry bundleEntry = bundleGroup.getBundleEntry(presenter.getLocale(), key);
			SourceEditor sourceEditor = presenter.getResourceManager().getSourceEditor(presenter.getLocale());
			if (bundleEntry == null) {
				document.set("");
			} else {
				String value = bundleEntry.getValue();
				document.set(value);
			}
			
	            textWidget.setEnabled(!sourceEditor.isReadOnly());
	            textWidget.setEditable(true);
	            textWidget.setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
	           /* gotoButton.setEnabled(true);
	            if (RBEPreferences.getReportDuplicateValues()) {
	                findDuplicates(bundleEntry);
	            } else {
	                duplVisitor = null;
	            }
	            if (RBEPreferences.getReportSimilarValues()) {
	                findSimilar(bundleEntry);
	            } else {
	                similarVisitor = null;
	            }*/
		}else {
          /*  commentedCheckbox.setSelection(false);
            commentedCheckbox.setEnabled(false);
            document.set("");
            textBox.setEnabled(false);
            gotoButton.setEnabled(false);
            duplButton.setVisible(false);
            simButton.setVisible(false);
            textBox.setEditable(false);*/
			textWidget.setBackground(new Color(getDisplay(), 245, 245, 245));
        }
		textView.setDocument(document);   
	}
}
