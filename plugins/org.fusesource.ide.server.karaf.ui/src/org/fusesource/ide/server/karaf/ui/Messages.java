package org.fusesource.ide.server.karaf.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.fusesource.ide.server.karaf.ui.l10n.messages"; //$NON-NLS-1$
	
	public static String ConnectionDetailsEditorSection_no_srv_conn;
	public static String ConnectionDetailsEditorSection_password_label;
	public static String ConnectionDetailsEditorSection_password_op;
	public static String ConnectionDetailsEditorSection_port_num_label;
	public static String ConnectionDetailsEditorSection_port_num_op;
	public static String ConnectionDetailsEditorSection_section_desc;
	public static String ConnectionDetailsEditorSection_section_name;
	public static String ConnectionDetailsEditorSection_user_name_label;
	public static String ConnectionDetailsEditorSection_user_name_op;
	public static String ConnectionDetailsEditorSection_hostname_label;
	public static String ConnectionDetailsEditorSection_hostname_op;
	
	public static String AbstractKarafRuntimeComposite_bin_karaf;
	public static String AbstractKarafRuntimeComposite_bin_karaf_bat;
	public static String AbstractKarafRuntimeComposite_browse_text;
	public static String AbstractKarafRuntimeComposite_install_dir_label;
	public static String AbstractKarafRuntimeComposite_invalid_dir;
	public static String AbstractKarafRuntimeComposite_no_dir;
	public static String AbstractKarafRuntimeComposite_not_a_dir;
	public static String AbstractKarafRuntimeComposite_txt_info_msg;
	public static String AbstractKarafRuntimeComposite_wizard_desc;
	public static String AbstractKarafRuntimeComposite_wizard_help_msg;
	public static String AbstractKarafRuntimeComposite_wizard_tite;
	public static String KarafServerPorpertiesComposite_host_name_label;
	public static String KarafServerPorpertiesComposite_password_label;
	public static String KarafServerPorpertiesComposite_port_number_label;
	public static String KarafServerPorpertiesComposite_user_name_label;
	public static String KarafServerPorpertiesComposite_wizard_desc;
	public static String KarafServerPorpertiesComposite_wizard_title;
	public static String ServiceMixServerPorpertiesComposite_wizard_desc;
	public static String ServiceMixServerPorpertiesComposite_wizard_title;
	public static String FuseESBServerPorpertiesComposite_wizard_desc;
	public static String FuseESBServerPorpertiesComposite_wizard_title;
	public static String ServiceMixRuntimeComposite_wizard_desc;
	public static String ServiceMixRuntimeComposite_wizard_tite;
	public static String FuseESBRuntimeComposite_wizard_tite;
	public static String FuseESBRuntimeComposite_wizard_desc;
	
	public static String FuseESBRuntimeComposite_bin_fuseesb;
	public static String FuseESBRuntimeComposite_bin_fuseesb_bat;
	
	public static String ServiceMix4xRuntimeComposite_bin_servicemix;
	public static String ServiceMix4xRuntimeComposite_bin_servicemix_bat;
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
