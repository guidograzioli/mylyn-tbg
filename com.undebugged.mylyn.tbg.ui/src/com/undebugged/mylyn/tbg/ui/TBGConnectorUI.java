package com.undebugged.mylyn.tbg.ui;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.ITaskMapping;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.ui.AbstractRepositoryConnectorUi;
import org.eclipse.mylyn.tasks.ui.wizards.ITaskRepositoryPage;

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
	public IWizard getQueryWizard(TaskRepository taskRepository,
			IRepositoryQuery queryToEdit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IWizard getNewTaskWizard(TaskRepository taskRepository,
			ITaskMapping selection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasSearchPage() {
		// TODO Auto-generated method stub
		return false;
	}

}
