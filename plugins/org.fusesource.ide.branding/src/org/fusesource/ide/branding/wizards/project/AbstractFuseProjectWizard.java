package org.fusesource.ide.branding.wizards.project;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkingSet;


public abstract class AbstractFuseProjectWizard extends Wizard {

  protected IStructuredSelection selection;

  protected List<IWorkingSet> workingSets = new ArrayList<IWorkingSet>();

  /*
  protected ProjectImportConfiguration importConfiguration = new ProjectImportConfiguration();

  private IMavenDiscovery discovery;

  private IImportWizardPageFactory pageFactory;
*/
  
  public void init(IWorkbench workbench, IStructuredSelection selection) {
    this.selection = selection;
    /*
    this.importConfiguration = new ProjectImportConfiguration();
    this.discovery = M2EUIPluginActivator.getDefault().getMavenDiscovery();
    this.pageFactory = M2EUIPluginActivator.getDefault().getImportWizardPageFactory();
    */
    IWorkingSet workingSet = SelectionUtil.getSelectedWorkingSet(selection);
    if(workingSet != null) {
      this.workingSets.add(workingSet);
    }
  }

  /*
  @Override
  public void dispose() {
    M2EUIPluginActivator.getDefault().ungetMavenDiscovery(discovery);
    super.dispose();
  }

  public ProjectImportConfiguration getProjectImportConfiguration() {
    return importConfiguration;
  }

  public IMavenDiscovery getDiscovery() {
    return discovery;
  }

  public IImportWizardPageFactory getPageFactory() {
    return pageFactory;
  }
  */
}
