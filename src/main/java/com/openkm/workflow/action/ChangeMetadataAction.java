package com.openkm.workflow.action;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMPropertyGroup;
import com.openkm.module.db.stuff.DbSessionManager;

public class ChangeMetadataAction implements ActionHandler {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(ChangeMetadataAction.class);

	private static final String OKM_PROPERTY = "okg:metadata";
	private static final String OKM_PROPERTY_STATUS = "okp:metadata.status";

	String status;

	public void execute(ExecutionContext executionContext) throws Exception {
		String sysToken = DbSessionManager.getInstance().getSystemToken();
		log.debug("workflow sysToken: {}", sysToken);
		String uuid = (String) executionContext.getContextInstance().getVariable("uuid");
		log.debug("workflow uuid: {}", uuid);

		log.debug("workflow status property: {}", status);
		if (!OKMPropertyGroup.getInstance().hasGroup(sysToken, uuid, OKM_PROPERTY)) {
			OKMPropertyGroup.getInstance().addGroup(sysToken, uuid, OKM_PROPERTY);
		}
		OKMPropertyGroup.getInstance().setPropertySimple(sysToken, uuid, OKM_PROPERTY, OKM_PROPERTY_STATUS, status);
		log.debug("workflow instance complete");
		executionContext.getProcessInstance().signal();
	}

}
