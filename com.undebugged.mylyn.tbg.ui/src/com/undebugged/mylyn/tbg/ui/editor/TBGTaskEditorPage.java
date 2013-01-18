package com.undebugged.mylyn.tbg.ui.editor;

import org.eclipse.mylyn.tasks.ui.editors.AbstractTaskEditorPage;
import org.eclipse.mylyn.tasks.ui.editors.TaskEditor;

import com.undebugged.mylyn.tbg.core.TBGCorePlugin;

public class TBGTaskEditorPage extends AbstractTaskEditorPage {

	public TBGTaskEditorPage(TaskEditor editor) {
		super(editor, TBGCorePlugin.CONNECTOR_KIND);

		setNeedsPrivateSection(true);
		setNeedsSubmitButton(true);
		setNeedsSubmit(true);
	}
	
	
}
