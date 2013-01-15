package com.undebugged.mylyn.tbg.ui;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.ITaskMapping;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.ui.AbstractRepositoryConnectorUi;
import org.eclipse.mylyn.tasks.ui.wizards.ITaskRepositoryPage;
import org.eclipse.mylyn.tasks.ui.wizards.NewTaskWizard;
import org.eclipse.mylyn.tasks.ui.wizards.RepositoryQueryWizard;

import com.undebugged.mylyn.tbg.core.TBGCorePlugin;
import com.undebugged.mylyn.tbg.ui.wizard.TBGRepositorySettingsPage;

public class TBGConnectorUI extends AbstractRepositoryConnectorUi {

	public TBGConnectorUI() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getConnectorKind() {
		return TBGCorePlugin.CONNECTOR_KIND;
	}

	@Override
	public ITaskRepositoryPage getSettingsPage(TaskRepository taskRepository) {
		return new TBGRepositorySettingsPage(taskRepository);
	}

	@Override
	public IWizard getQueryWizard(TaskRepository repository, IRepositoryQuery query) {
		RepositoryQueryWizard wizard = new RepositoryQueryWizard(repository);
        wizard.addPage(new TBGRepositoryQueryPage(repository, query));
        return wizard;
	}

	@Override
	public IWizard getNewTaskWizard(TaskRepository taskRepository, ITaskMapping selection) {
		return new NewTaskWizard(taskRepository, selection);
	}

	@Override
	public boolean hasSearchPage() {
		return true;
	}

}
