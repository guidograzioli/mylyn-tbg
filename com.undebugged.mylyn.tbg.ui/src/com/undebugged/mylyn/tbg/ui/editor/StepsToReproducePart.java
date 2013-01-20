package com.undebugged.mylyn.tbg.ui.editor;

import com.undebugged.mylyn.tbg.core.TBGTaskAttributes;


public class StepsToReproducePart extends AbstractRichTextPart {
    protected static final String LABEL_SECTION_STEPS = "Steps To Reproduce";
    
    public StepsToReproducePart(boolean expandedByDefault) {

        super(LABEL_SECTION_STEPS, TBGTaskAttributes.STEPS_TO_REPRODUCE.getKey(), expandedByDefault);
    }
    
}