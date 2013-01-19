package com.undebugged.mylyn.tbg.ui.editor;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.ui.editors.AbstractAttributeEditor;
import org.eclipse.mylyn.tasks.ui.editors.AbstractTaskEditorPage;
import org.eclipse.mylyn.tasks.ui.editors.AbstractTaskEditorPart;
import org.eclipse.mylyn.tasks.ui.editors.AttributeEditorFactory;
import org.eclipse.mylyn.tasks.ui.editors.TaskEditor;
import org.eclipse.mylyn.tasks.ui.editors.TaskEditorPartDescriptor;

import com.undebugged.mylyn.tbg.core.TBGCorePlugin;

public class TBGTaskEditorPage extends AbstractTaskEditorPage {

	public static final String ID_PART_STEPSTOREPRODUCE = "com.undebugged.mylyn.tbg.ui.editor.part.stepstoreproduce";
	
	public TBGTaskEditorPage(TaskEditor editor) {
		super(editor, TBGCorePlugin.CONNECTOR_KIND);

		//setNeedsPrivateSection(true);
		setNeedsSubmitButton(true);
		setNeedsSubmit(true);
	}
	@Override
	protected Set<TaskEditorPartDescriptor> createPartDescriptors() {
		Set<TaskEditorPartDescriptor> descriptors = super.createPartDescriptors();

		descriptors = insertPart(descriptors, new TaskEditorPartDescriptor(ID_PART_STEPSTOREPRODUCE) {
			@Override
			public AbstractTaskEditorPart createPart() {
                return new StepsToReproducePart(false);

			}
		}.setPath(PATH_COMMENTS), ID_PART_DESCRIPTION);
		return descriptors;
	}
	
	protected Set<TaskEditorPartDescriptor> insertPart(Set<TaskEditorPartDescriptor> originalDescriptors, TaskEditorPartDescriptor newDescriptor, String insertAfterId ) {

	    Set<TaskEditorPartDescriptor> newDescriptors = new LinkedHashSet<TaskEditorPartDescriptor>();

	    boolean added = false;

		for (TaskEditorPartDescriptor taskEditorPartDescriptor : originalDescriptors) {
			newDescriptors.add(taskEditorPartDescriptor);
			if (taskEditorPartDescriptor.getId().equals(insertAfterId)) {
				newDescriptors.add(newDescriptor);
				added = true;
			}
		}

		if ( !added )
		    throw new IllegalArgumentException("Did not find a part with id " + insertAfterId + " to insert the newDescriptor after");

		return newDescriptors;
	}
	
	@Override
	protected AttributeEditorFactory createAttributeEditorFactory() {

	    return new AttributeEditorFactory(getModel(), getTaskRepository(), getEditorSite()) {

	        @Override
	        public AbstractAttributeEditor createEditor(String type, TaskAttribute taskAttribute) {

	            return super.createEditor(type, taskAttribute);
	        }
	    };
	}
}
