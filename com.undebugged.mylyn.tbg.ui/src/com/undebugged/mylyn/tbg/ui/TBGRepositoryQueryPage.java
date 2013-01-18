package com.undebugged.mylyn.tbg.ui;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.ui.wizards.AbstractRepositoryQueryPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;

import com.undebugged.mylyn.tbg.core.TBGCorePlugin;
import com.undebugged.mylyn.tbg.core.TBGService;
import com.undebugged.mylyn.tbg.core.TBGServiceException;
import com.undebugged.mylyn.tbg.core.model.TBGIssueType;
import com.undebugged.mylyn.tbg.core.model.TBGProject;
import com.undebugged.mylyn.tbg.core.model.TBGProjects;

public class TBGRepositoryQueryPage extends AbstractRepositoryQueryPage {

    private List statusList;
    private List typeList;
    private List projectList;
    private ListViewer projectViewer;
    private List assigneeList;
    private Text queryTitleText;
    
    TBGService service;

    private final ModifyListener modifyListener = new ModifyListener() {
        public void modifyText(ModifyEvent e) {
            if (isControlCreated()) {
                setPageComplete(isPageComplete());
            }
        }
    };
    private Composite composite;

    /**
     * Constructor.
     * @param taskRepository
     * @param query
     */
    public TBGRepositoryQueryPage(TaskRepository taskRepository, IRepositoryQuery query) {
        super("The Bug Genie", taskRepository, query);
        setImageDescriptor(ResourceManager.getPluginImageDescriptor("com.undebugged.mylyn.tbg.ui", "icons/thebuggenie.png"));
        setTitle("The Bug Genie search query parameters");
        setDescription("Enter query title, select project and optional parameters.");
        setPageComplete(false);
        service = TBGService.get(taskRepository);
        
    }

    @Override
    public boolean isPageComplete() {
        if (getQueryTitle().isEmpty()) return false;
        if (((IStructuredSelection)projectViewer.getSelection()).isEmpty()) return false;
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
        setAttributeIfNotEmpty(query, TBGCorePlugin.TBG_QUERY_ASSIGNEE, assigneeList.getSelection());
        TBGProject project = (TBGProject) ((IStructuredSelection)projectViewer.getSelection()).getFirstElement();
        query.setAttribute(TBGCorePlugin.TBG_QUERY_PROJECT, project.getProjectKey());
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
        composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(1, false));
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
        String[] issueTypes = TBGIssueType.asArray();
        String[] statuses = new String[] {"all", "open","closed" };
        String[] assignees = new String[] {"all", "me", "none" };
        TBGProjects projects = service.doGet(new TBGProjects());
        

        Composite group = new Composite(parent, SWT.NONE);
        group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        GridLayoutFactory.swtDefaults().numColumns(4).applyTo(group);
        group.setLayout(new GridLayout(4, true));
        {
            Label lblProject = new Label(group, SWT.LEFT);
            lblProject.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
            lblProject.setText("Project");
        }
        {
            Label lblAssignee = new Label(group, SWT.LEFT);
            lblAssignee.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
            lblAssignee.setText("Assignee");
        }        
        {
            Label label = new Label(group, SWT.LEFT);
            label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
            label.setText("Type");
        }
        {
            Label lblState = new Label(group, SWT.LEFT);
            lblState.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
            lblState.setText("State");
        }
        {
            projectList = new List(group, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
            projectList.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
            GridDataFactory.defaultsFor(projectList).applyTo(projectList);
            projectViewer = new ListViewer(projectList);
            projectViewer.setContentProvider(new ArrayContentProvider());
            projectViewer.setLabelProvider(new LabelProvider() {
				@Override
				public String getText(Object element) {
					return ((TBGProject)element).getProjectName();
				}
            });
            projectViewer.setInput(projects.getProjects());
            if (oldQuery != null) {
                String projectValue = oldQuery.getAttribute(TBGCorePlugin.TBG_QUERY_PROJECT);
                projectViewer.setSelection(new StructuredSelection(new TBGProject(projectValue,"")));
            }
            projectViewer.addSelectionChangedListener(new ISelectionChangedListener() {
				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					setPageComplete(isPageComplete());
				}
			});
        }
        {
            assigneeList = new List(group, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
            assigneeList.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
            GridDataFactory.defaultsFor(assigneeList).applyTo(assigneeList);
            assigneeList.setItems(assignees);
            if (oldQuery != null) {
                String assigneeValue = oldQuery.getAttribute(TBGCorePlugin.TBG_QUERY_ASSIGNEE);
                if (assigneeValue != null) 
                	for (int i = 0; i < assignees.length; i++) {
	                    if (assigneeValue.equals(assignees[i])) {
	                    	assigneeList.select(i);
	                    }
	                }
            }
        }        
        {
            typeList = new List(group, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
            typeList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
            GridDataFactory.defaultsFor(typeList).applyTo(typeList);
            typeList.setItems(issueTypes);
            if (oldQuery != null) {
                String kindValues = oldQuery.getAttribute(TBGCorePlugin.TBG_QUERY_TYPE);
                for (int i = 0; i < issueTypes.length; i++) {
                    if (kindValues.contains(issueTypes[i])) {
                        typeList.select(i);
                    }
                }
            }
        }
        {
            statusList = new List(group, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
            GridData gd_statusList = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
            gd_statusList.widthHint = 22;
            statusList.setLayoutData(gd_statusList);
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