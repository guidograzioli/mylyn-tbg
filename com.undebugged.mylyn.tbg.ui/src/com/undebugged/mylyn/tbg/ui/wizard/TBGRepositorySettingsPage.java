package com.undebugged.mylyn.tbg.ui.wizard;

import org.eclipse.mylyn.tasks.core.RepositoryTemplate;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.ui.wizards.AbstractRepositorySettingsPage;
import org.eclipse.swt.widgets.Composite;

import com.undebugged.mylyn.tbg.core.TBGCorePlugin;

public class TBGRepositorySettingsPage extends AbstractRepositorySettingsPage {

	public TBGRepositorySettingsPage(TaskRepository taskRepository) {
		super("The Bug Genie Repository Setings", "Settings for The Bug Genie Repository", taskRepository);
		setNeedsAnonymousLogin(false);
		setNeedsEncoding(false);
		setNeedsTimeZone(false);
		setNeedsAdvanced(false);
		setNeedsCertAuth(false);
		setNeedsProxy(false);
		setNeedsHttpAuth(false);
		setNeedsValidation(false);
		setNeedsValidateOnFinish(false);
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		addRepositoryTemplatesToServerUrlCombo();
	}
	
	@Override
	public String getConnectorKind() {
		return TBGCorePlugin.CONNECTOR_KIND;
	}

	@Override
	protected void createAdditionalControls(Composite parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Validator getValidator(TaskRepository repository) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isValidUrl(String url) {
		if (url == null || url.trim().length() < 8) return false;
		return super.isValidUrl(url);
	}
	
	@Override
	protected void repositoryTemplateSelected(RepositoryTemplate template) {
		repositoryLabelEditor.setStringValue(template.label);
		setUrl(template.repositoryUrl);
		setUserId("username");
		setPassword("password");
		getContainer().updateButtons();
	}
}
