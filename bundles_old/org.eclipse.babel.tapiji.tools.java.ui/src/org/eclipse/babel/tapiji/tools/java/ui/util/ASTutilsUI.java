/*******************************************************************************
 * Copyright (c) 2012 Martin Reiterer, Alexej Strelzow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Martin Reiterer - initial API and implementation
 *     Alexej Strelzow - seperation of ui/non-ui (methods moved from ASTUtils)
 ******************************************************************************/

package org.eclipse.babel.tapiji.tools.java.ui.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.babel.core.configuration.DirtyHack;
import org.eclipse.babel.core.message.IMessagesBundle;
import org.eclipse.babel.core.message.IMessagesBundleGroup;
import org.eclipse.babel.core.message.manager.RBManager;
import org.eclipse.babel.tapiji.tools.core.Logger;
import org.eclipse.babel.tapiji.tools.core.ui.ResourceBundleManager;
import org.eclipse.babel.tapiji.tools.core.ui.dialogs.KeyRefactoringDialog;
import org.eclipse.babel.tapiji.tools.core.ui.dialogs.KeyRefactoringDialog.DialogConfiguration;
import org.eclipse.babel.tapiji.tools.core.ui.dialogs.KeyRefactoringSummaryDialog;
import org.eclipse.babel.tapiji.tools.core.ui.utils.LocaleUtils;
import org.eclipse.babel.tapiji.tools.java.ui.refactoring.Cal10nEnumRefactoringVisitor;
import org.eclipse.babel.tapiji.tools.java.ui.refactoring.Cal10nRefactoringVisitor;
import org.eclipse.babel.tapiji.tools.java.ui.refactoring.PrimitiveRefactoringVisitor;
import org.eclipse.babel.tapiji.tools.java.util.ASTutils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.ui.SharedASTProvider;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.widgets.Display;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

public class ASTutilsUI {

	public static ICompilationUnit getCompilationUnit(IResource resource) {
		return (ICompilationUnit) JavaCore.create(resource,
				JavaCore.create(resource.getProject()));
	}

	public static CompilationUnit getAstRoot(ITypeRoot typeRoot) {
		// get a reference to the shared AST of the loaded CompilationUnit
		CompilationUnit cu = SharedASTProvider.getAST(typeRoot,
		// do not wait for AST creation
				SharedASTProvider.WAIT_YES, null);

		return cu;
	}

	public static String insertNewBundleRef(IDocument origDocument,
			IResource resource, int startPos, int endPos,
			String resourceBundleId, String key) {
		boolean createRBReference = false;
		String reference = "";

		try {
			final ICompilationUnit cu = getCompilationUnit(resource);
			final CompilationUnit astRoot = getAstRoot(cu);
			final AST ast = astRoot.getAST();
			final ASTRewrite rewriter = ASTRewrite.create(ast);
			IDocument document;

			if (origDocument == null) {
				final String source = cu.getSource();
				document = new Document(source);
			} else {
				document = origDocument;
			}

			String variableName = ASTutils.resolveRBReferenceVar(document,
					resource, startPos, resourceBundleId, astRoot);
			if (variableName == null) {
				variableName = ASTutils.getNonExistingRBRefName(
						resourceBundleId, astRoot);
				createRBReference = true;
			}

			try {
				reference = ASTutils.createResourceReference(resourceBundleId,
						key, null, resource, startPos, variableName, ast,
						rewriter, astRoot);

				if (reference != null) {
					if (startPos > 0
							&& document.get().charAt(startPos - 1) == '\"') {
						startPos--;
						endPos++;
					}

					if ((startPos + endPos) < document.getLength()
							&& document.get().charAt(startPos + endPos) == '\"') {
						endPos++;
					}

					if ((startPos + endPos) < document.getLength()
							&& document.get().charAt(startPos + endPos - 1) == ';') {
						endPos--;
					}

					document.replace(startPos, endPos, reference);

				}
				// create non-internationalisation-comment
				ASTutils.createReplaceNonInternationalisationComment(astRoot,
						document, startPos, reference == null ? 0 : 1);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}

			if (createRBReference) {
				ASTutils.createResourceBundleReference(resource, startPos,
						document, resourceBundleId, null, true, variableName,
						astRoot, ast, rewriter);
			}

			// computation of the text edits
			TextEdit edits = rewriter.rewriteAST(document, null);

			// computation of the new source code
			try {
				edits.apply(document);

				if (origDocument == null) {
					String newSource = document.get();

					// update of the compilation unit
					cu.getBuffer().setContents(newSource);
				}
			} catch (MalformedTreeException e) {
				Logger.logError(e);
			} catch (BadLocationException e) {
				Logger.logError(e);
			}
		} catch (JavaModelException e) {
			Logger.logError(e);
		}

		return reference;
	}

	public static String insertExistingBundleRef(IDocument origDocument,
			IResource resource, int startPos, int length,
			String resourceBundleId, String key, Locale locale) {
		boolean createRBReference = false;
		String reference = "";

		try {
			final ICompilationUnit cu = getCompilationUnit(resource);
			final CompilationUnit astRoot = getAstRoot(cu);
			final AST ast = astRoot.getAST();
			final ASTRewrite rewriter = ASTRewrite.create(ast);
			IDocument document;

			if (origDocument == null) {
				final String source = cu.getSource();
				document = new Document(source);
			} else {
				document = origDocument;
			}

			String variableName = ASTutils.resolveRBReferenceVar(document,
					resource, startPos, resourceBundleId, astRoot);
			if (variableName == null) {
				variableName = ASTutils.getNonExistingRBRefName(
						resourceBundleId, astRoot);
				createRBReference = true;
			}

			reference = ASTutils.createResourceReference(resourceBundleId, key,
					locale, resource, startPos, variableName, ast, rewriter,
					astRoot);

			if (reference != null) {
				try {
					document.replace(startPos, length, reference);
				} catch (BadLocationException e) {
					Logger.logError(e);
					return null;
				}
			}
			// create non-internationalisation-comment
			ASTutils.createReplaceNonInternationalisationComment(astRoot,
					document, startPos, reference == null ? 0 : 1);

			// TODO retrieve cu in the same way as in createResourceReference
			// the current version does not parse method bodies

			if (createRBReference) {
				ASTutils.createResourceBundleReference(resource, startPos,
						document, resourceBundleId, locale, true, variableName,
						astRoot, ast, rewriter);
			}

			// computation of the text edits
			TextEdit edits = rewriter.rewriteAST(document, null);

			// computation of the new source code
			try {
				edits.apply(document);
				
				if (origDocument == null) {
					String newSource = document.get();
	
					// update of the compilation unit
					cu.getBuffer().setContents(newSource);
				}
			} catch (MalformedTreeException e) {
				Logger.logError(e);
			} catch (BadLocationException e) {
				Logger.logError(e);
			}

		} catch (JavaModelException e) {
			Logger.logError(e);
		}

		return reference;
	}

	/**
	 * Performs the refactoring of messages key. The key can be a {@link String}
	 * or an Enumeration! If it is an enumeration, then the enumPath needs to be
	 * provided!
	 * 
	 * @param projectName
	 *            The name of the project, where the resource bundle file is in
	 * @param resourceBundleId
	 *            The Id of the resource bundle, which contains the old key
	 * @param selectedLocale
	 *            The {@link Locale} to change
	 * @param oldKey
	 *            The name of the key to change
	 * @param newKey
	 *            The name of the key, which replaces the old one
	 * @param enumPath
	 *            The path of the enum file (needs:
	 *            {@link IPath#toPortableString()})
	 */
	public static void refactorKey(final String projectName,
			final String resourceBundleId, final String selectedLocale,
			final String oldKey, final String newKey, final String enumPath) {

		// contains file and line
		final List<String> changeSet = new ArrayList<String>();

		ResourceBundleManager manager = ResourceBundleManager
				.getManager(projectName);
		IProject project = manager.getProject();

		try {
			project.accept(new IResourceVisitor() {

				/**
				 * First step of filtering. Only classes, which import
				 * java.util.ResourceBundle or ch.qos.cal10n.MessageConveyor
				 * will be changed. An exception is the enum file, which gets
				 * referenced by the Cal10n framework.
				 * 
				 * {@inheritDoc}
				 */
				@Override
				public boolean visit(IResource resource) throws CoreException {
					if (!(resource instanceof IFile)
							|| !resource.getFileExtension().equals("java")) {
						return true;
					}

					final ICompilationUnit icu = getCompilationUnit(resource);
					final CompilationUnit cu = getAstRoot(icu);

					// step 1: import filter
					for (Object obj : cu.imports()) {
						ImportDeclaration imp = (ImportDeclaration) obj;
						String importName = imp.getName().toString();
						if ("java.util.ResourceBundle".equals(importName)) {
							PrimitiveRefactoringVisitor prv = new PrimitiveRefactoringVisitor(
									cu, resourceBundleId, oldKey, newKey,
									changeSet);
							cu.accept(prv);
							prv.saveChanges();
							break;
						} else if ("ch.qos.cal10n.MessageConveyor"
								.equals(importName)) { // Cal10n
							Cal10nRefactoringVisitor crv = new Cal10nRefactoringVisitor(
									cu, oldKey, newKey, enumPath, changeSet);
							cu.accept(crv);
							crv.saveChanges();
							break;
						}
					}

					return false;
				}
			});
		} catch (CoreException e) {
			Logger.logError(e);
		}

		if (enumPath != null) { // Cal10n support, change the enum file
			IFile file = project.getFile(enumPath.substring(project.getName()
					.length() + 1));
			final CompilationUnit enumCu = getAstRoot(getCompilationUnit(file));

			Cal10nEnumRefactoringVisitor enumVisitor = new Cal10nEnumRefactoringVisitor(
					enumCu, oldKey, newKey, changeSet);
			enumCu.accept(enumVisitor);
		}

		// change backend
		RBManager rbManager = RBManager.getInstance(projectName);
		IMessagesBundleGroup messagesBundleGroup = rbManager
				.getMessagesBundleGroup(resourceBundleId);

		DirtyHack.setFireEnabled(false); // now the editor won't get dirty
		// but with this change, we have to write it manually down ->
		// rbManager.writeToFile
		if (KeyRefactoringDialog.ALL_LOCALES.equals(selectedLocale)) {
			messagesBundleGroup.renameMessageKeys(oldKey, newKey);

		} else {
			IMessagesBundle messagesBundle = messagesBundleGroup
					.getMessagesBundle(LocaleUtils.getLocaleByDisplayName(
							manager.getProvidedLocales(resourceBundleId),
							selectedLocale));
			messagesBundle.renameMessageKey(oldKey, newKey);
			// rbManager.fireResourceChanged(messagesBundle); ??
		}
		DirtyHack.setFireEnabled(true);

		rbManager.fireEditorChanged(); // notify Resource Bundle View
		rbManager.writeToFile(rbManager
				.getMessagesBundleGroup(resourceBundleId));

		// show the summary dialog
		KeyRefactoringSummaryDialog summaryDialog = new KeyRefactoringSummaryDialog(
				Display.getDefault().getActiveShell());

		DialogConfiguration config = summaryDialog.new DialogConfiguration();
		config.setPreselectedKey(oldKey);
		config.setNewKey(newKey);
		config.setPreselectedBundle(resourceBundleId);
		config.setProjectName(projectName);

		summaryDialog.setDialogConfiguration(config);
		summaryDialog.setChangeSet(changeSet);

		summaryDialog.open();
	}

}
