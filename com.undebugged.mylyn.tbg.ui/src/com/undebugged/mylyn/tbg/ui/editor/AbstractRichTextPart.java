package com.undebugged.mylyn.tbg.ui.editor;
import org.eclipse.mylyn.internal.tasks.ui.editors.TaskEditorRichTextPart;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.ui.editors.AbstractTaskEditorPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author Robert Munteanu
 * 
 */
@SuppressWarnings("restriction")
public class AbstractRichTextPart extends TaskEditorRichTextPart {

    private String _key;

    public AbstractRichTextPart(String label, String key, boolean expandedByDefault) {

        setPartName(label);
        setExpandVertically(true);

        if (!expandedByDefault)
            collapse();

        _key = key;
    }

    @Override
    public void initialize(AbstractTaskEditorPage taskEditorPage) {

        super.initialize(taskEditorPage);

        TaskAttribute attribute = getTaskData().getRoot().getAttribute(_key);
        setAttribute(attribute);

        if ( attribute != null && attribute.getValue() != null && 
        		attribute.getValue().length() > 0)
            expand();

    }
    
    @Override
    public void createControl(Composite parent, FormToolkit toolkit) {
    
        super.createControl(parent, toolkit);
        
        getEditor().enableAutoTogglePreview();
        if (!getTaskData().isNew())
            getEditor().showPreview();
    }

    private void collapse() {

        setSectionStyle(getSectionStyle() & ~ExpandableComposite.EXPANDED);
    }
    
    private void expand() {
    	
    	setSectionStyle(getSectionStyle() | ExpandableComposite.EXPANDED);
    }

}