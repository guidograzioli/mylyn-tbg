package com.undebugged.mylyn.tbg.ui;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.ui.wizards.AbstractRepositoryQueryPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import com.undebugged.mylyn.tbg.core.TBGCorePlugin;
import com.undebugged.mylyn.tbg.core.TBGIssueType;
import com.undebugged.mylyn.tbg.core.TBGService;
import com.undebugged.mylyn.tbg.core.TBGServiceException;
import com.undebugged.mylyn.tbg.core.TBGState;

public class TBGRepositoryQueryPage extends AbstractRepositoryQueryPage {

    private List statusList;
    private List typeList;
    private Text queryTitleText;
    private Text titleText;
    private Text assigneeText;
    private Text projectText;
    
    TBGService bbs;

    private final ModifyListener modifyListener = new ModifyListener() {
        public void modifyText(ModifyEvent e) {
            if (isControlCreated()) {
                setPageComplete(isPageComplete());
            }
        }
    };

    /**
     * Constructor.
     * @param taskRepository
     * @param query
     */
    public TBGRepositoryQueryPage(TaskRepository taskRepository, IRepositoryQuery query) {
        super("Bitbucket", taskRepository, query);
        setTitle("Bitbucket search query parameters");
        setDescription("Enter query title and parameters.");
        //setImageDescriptor(BitbucketImages.getIcon());
        setPageComplete(false);
        bbs = TBGService.get(taskRepository);
        
    }

    @Override
    public boolean isPageComplete() {
        if (getQueryTitle().isEmpty()) return false;
        setErrorMessage(null);
        return true;
    }

    @Override
    public String getQueryTitle() {
        return queryTitleText.getText();
    }
    
    @Override
    public void applyTo(IRepositoryQuery query) {
        query.setSummary(getQueryTitle());
        
        setAttributeIfNotEmpty(query, TBGCorePlugin.TBG_QUERY_STATE, statusList.getSelection());
        setAttributeIfNotEmpty(query, TBGCorePlugin.TBG_QUERY_TYPE, typeList.getSelection());
        query.setAttribute(TBGCorePlugin.TBG_QUERY_PROJECT, projectText.getText());
//        setAttributeIfNotEmpty(query, Bitbucket.QUERY_KEY_MILESTONE, milestoneList.getSelection());
//        setAttributeIfNotEmpty(query, Bitbucket.QUERY_KEY_COMPONENT, componentList.getSelection());
//        setAttributeIfNotEmpty(query, Bitbucket.QUERY_KEY_PRIORITY, priorityList.getSelection());
//        query.setAttribute(Bitbucket.QUERY_KEY_TITLE, titleText.getText());
//        query.setAttribute(Bitbucket.QUERY_KEY_ASSIGNEE, assigneeText.getText());
        System.err.println(query.getAttributes().toString());        
    }
    
    protected void setAttributeIfNotEmpty(IRepositoryQuery query, String attribute, String[] params) {
        if (params.length == 0) return;
        StringBuilder value = new StringBuilder();
        for (String v : params) {
            value.append(v).append(",");
        }
        value.setLength(value.length() - ",".length());
        query.setAttribute(attribute, value.toString());
    }
    
    public void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayoutFactory.swtDefaults().applyTo(composite);
        createGeneralControl(composite);
        try {
        	createIssueAttributesControl(composite);
        } catch (TBGServiceException e) {
        	throw new RuntimeException(e);//not sure what else to do superclass doesnt handle dynamic lookups
        }
        setControl(composite);
    }

    private Composite createGeneralControl(Composite parent) {
        Composite group = new Composite(parent, SWT.NONE);
        GridLayoutFactory.swtDefaults().numColumns(2).applyTo(group);
        {
            Label label = new Label(group, SWT.LEFT);
            label.setText("Query Title:");
            queryTitleText = new Text(group, SWT.BORDER);
            if (getQuery() != null) queryTitleText.setText(getQuery().getSummary());
            queryTitleText.addModifyListener(modifyListener);
            GridDataFactory.defaultsFor(queryTitleText).applyTo(queryTitleText);
        }
        GridDataFactory.fillDefaults().grab(true, false).applyTo(group);
        return group;
    }

    private Composite createIssueAttributesControl(Composite parent) throws TBGServiceException {
        
        
        
        IRepositoryQuery oldQuery = getQuery();
        String[] kinds = TBGIssueType.asArray();
        String[] statuses = TBGState.asArray();
        
        Composite titleGroup = new Composite(parent, SWT.NONE);
        GridLayoutFactory.swtDefaults().numColumns(2).applyTo(titleGroup);
        {
            Label label = new Label(titleGroup,SWT.LEFT);
            label.setText("Project");
        }
        {
            projectText = new Text(titleGroup, SWT.BORDER);
            if (oldQuery != null) {
                    projectText.setText(oldQuery.getAttribute(TBGCorePlugin.TBG_QUERY_PROJECT));
            }
        }

        {
                Label label = new Label(titleGroup,SWT.LEFT);
                label.setText("Title");
        }
        {
                titleText = new Text(titleGroup, SWT.BORDER);
                if (oldQuery != null && oldQuery.getAttribute(TBGCorePlugin.TBG_QUERY_TITLE) != null) {
                        titleText.setText(oldQuery.getAttribute(TBGCorePlugin.TBG_QUERY_TITLE));
                }
        }
        {
                Label label = new Label(titleGroup,SWT.LEFT);
                label.setText("Assignee");
        }
        {
                assigneeText = new Text(titleGroup, SWT.BORDER);
                if (oldQuery != null && oldQuery.getAttribute(TBGCorePlugin.TBG_QUERY_ASSIGNEE) != null) {
                	assigneeText.setText(oldQuery.getAttribute(TBGCorePlugin.TBG_QUERY_ASSIGNEE));
                }
        }
        Composite group = new Composite(parent, SWT.NONE);
        GridLayoutFactory.swtDefaults().numColumns(6).applyTo(group);
        { // Label
            Label label = new Label(group, SWT.LEFT);
            label.setText("Type");
        }
        {
            Label label = new Label(group, SWT.LEFT);
            label.setText("Priority");
        }
        {
            Label label = new Label(group, SWT.LEFT);
            label.setText("Status");
        }
//        {
//            Label label = new Label(group, SWT.LEFT);
//            label.setText("Version");
//        }
//        {
//            Label label = new Label(group, SWT.LEFT);
//            label.setText("Milestone");
//        }
//        {
//            Label label = new Label(group, SWT.LEFT);
//            label.setText("Component");
//        }
        {
            typeList = new List(group, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
            GridDataFactory.defaultsFor(typeList).applyTo(typeList);
            for (String text : kinds) {
                typeList.add(text);
            }
            if (oldQuery != null) {
                String kindValues = oldQuery.getAttribute(TBGCorePlugin.TBG_QUERY_TYPE);
                for (int i = 0; i < kinds.length; i++) {
                    if (kindValues.contains(kinds[i])) {
                        typeList.select(i);
                    }
                }
            } else {
                typeList.select(new int[] { 0, 1 });
            }
        }
        {
            statusList = new List(group, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
            GridDataFactory.defaultsFor(statusList).applyTo(statusList);
            for (String text : statuses) {
                statusList.add(text);
            }
            if (oldQuery != null) {
                String statusValues = oldQuery.getAttribute(TBGCorePlugin.TBG_QUERY_STATE);
                for (int i = 0; i < statuses.length; i++) {
                    if (statusValues.contains(statuses[i])) {
                        statusList.select(i);
                    }
                }
            } else {
                statusList.select(new int[] { 0 });
            }
        }
        return group;
    }

}