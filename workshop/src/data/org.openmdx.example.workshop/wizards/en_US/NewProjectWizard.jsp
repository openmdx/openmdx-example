<%@  page contentType= "text/html;charset=UTF-8" language="java" pageEncoding= "UTF-8" %>
<%@ page session="true" import="
java.util.*,
java.io.*,
java.text.*,
org.openmdx.base.accessor.jmi.cci.*,
org.openmdx.base.exception.*,
org.openmdx.portal.servlet.*,
org.openmdx.portal.servlet.attribute.*,org.openmdx.portal.servlet.component.*,
org.openmdx.kernel.id.*,
org.openmdx.base.text.conversion.*,
org.openmdx.portal.servlet.control.*,
org.openmdx.portal.servlet.wizards.*,
org.openmdx.portal.servlet.action.*,
org.openmdx.base.naming.*,
org.openmdx.base.query.*,
org.openmdx.kernel.exception.BasicException
" 
%><%

	final String WIZARD_NAME = "NewProjectWizard.jsp";

	ApplicationContext app = (ApplicationContext)session.getValue(WebKeys.APPLICATION_KEY);
	ViewsCache viewsCache = (ViewsCache)session.getValue(WebKeys.VIEW_CACHE_KEY_SHOW);
	String requestId = request.getParameter(Action.PARAMETER_REQUEST_ID);
	String objectXri = request.getParameter("xri");
	if(objectXri == null || app == null || viewsCache.getView(requestId) == null) {
		response.sendRedirect(
			request.getContextPath() + "/" + WebKeys.SERVLET_NAME
		);
		return;
	}
    javax.jdo.PersistenceManager pm = app.getNewPmData();
	RefObject_1_0 obj = (RefObject_1_0)pm.getObjectById(new Path(objectXri));	
	Texts_1_0 texts = app.getTexts();

%>
<!--[if IE]><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><![endif]-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
  <title>openMDX Workshop - Create New Project</title>
  <meta name="label" content="Create New Project">
  <meta name="toolTip" content="Create New Project">
  <meta name="targetType" content="_self">
  <meta name="forClass" content="org:openmdx:example:workshop1:Segment">
  <meta name="order" content="10">
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <link href="../../_style/colors.css" rel="stylesheet" type="text/css">
  <link href="../../_style/n2default.css" rel="stylesheet" type="text/css">
  <link rel='shortcut icon' href='../../images/favicon.ico' />
  <script language="javascript" type="text/javascript">
    var selectedObjTab = null;
    var panelsObj = new Array(
      'panelObj0'
    );

  </script>
  <script language="javascript" type="text/javascript" src="../../javascript/guicontrol.js"></script>
</head>
<body>
<div id="container">
	<div id="wrap">
		<div id="fixheader" style="height:90px;">
      <div id="logoTable">
        <table id="headerlayout">
          <tr id="headRow">
            <td id="head" colspan="2">
              <table id="info">
                <tr>
                  <td id="headerCellLeft"><img id="logoLeft" src="../../images/logoLeft.gif" alt="openCRX" title="" /></td>
                  <td id="headerCellSpacerLeft"></td>
                  <td id="headerCellMiddle">&nbsp;</td>
                  <td id="headerCellRight"><img id="logoRight" src="../../images/logoRight.gif" alt="" title="" /></td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </div>
    </div>
    <div id="content-wrap">
    	<div id="content" style="padding:0px 0.5em 0px 0.5em;">
<%
	boolean hadException = false;
	try {
		boolean actionOk = request.getParameter("OK.Button") != null;
		boolean actionCancel = request.getParameter("Cancel.Button") != null;
		String projectName = request.getParameter("projectName") == null ? "" : request.getParameter("projectName");
		String projectDescription = request.getParameter("projectDescription");
		String task1Name = request.getParameter("task1Name");
		String task1Definition = request.getParameter("task1Definition");
		String task2Name = request.getParameter("task2Name");
		String task2Definition = request.getParameter("task2Definition");

	    Path objectPath = obj.refGetPath();
	    String providerName = objectPath.get(2);
	    String segmentName = objectPath.get(4);

	    // Get workshop1 package
	    org.openmdx.example.workshop1.jmi1.Workshop1Package workshop1Pkg =
			(org.openmdx.example.workshop1.jmi1.Workshop1Package)((RefObject_1_0)pm.newInstance(
				org.openmdx.example.workshop1.jmi1.Segment.class)
			).refOutermostPackage().refPackage(
	    		org.openmdx.example.workshop1.jmi1.Workshop1Package.class.getName()
	    	);
	
	    // Get example1 segment
	    org.openmdx.example.workshop1.jmi1.Segment example1Segment =
	      (org.openmdx.example.workshop1.jmi1.Segment)pm.getObjectById(
	        new Path("xri:@openmdx:org.openmdx.example.workshop1/provider/" + providerName + "/segment/" + segmentName)
	       );

		if(actionOk && (projectName != null) && (projectName.length() > 0)) {

	    try {
        pm.currentTransaction().begin();

        // create TeamMember "The Boss" if it does not exist yet
   	    boolean notFoundException = false;
   	    String theBossId = "theBoss";
   	    org.openmdx.example.workshop1.jmi1.TeamMember theBoss = null;
        try {
          theBoss = (org.openmdx.example.workshop1.jmi1.TeamMember)example1Segment.getTeamMember(theBossId);
        }
        catch(Exception e) {
    	    ServiceException e0 = new ServiceException(e);
    	    notFoundException = e0.getExceptionCode() == BasicException.Code.NOT_FOUND;
        }
        if(notFoundException) {
            theBoss = workshop1Pkg.getTeamMember().createTeamMember();
            theBoss.setName("The Boss");
            theBoss.setSecurityNumber("I_don't_dial_911");
            example1Segment.addTeamMember(
				false,
                theBossId,
                theBoss
            );
        }

        //
        // create Project
        //
        org.openmdx.example.workshop1.jmi1.Project project = workshop1Pkg.getProject().createProject();
        example1Segment.addProject(
			UUIDConversion.toUID(UUIDs.newUUID()),
			project
		);
        project.setName(projectName);
        project.setManager(
        	(org.openmdx.example.workshop1.jmi1.TeamMember)pm.getObjectById(
        		new Path("xri:@openmdx:org.openmdx.example.workshop1/provider/" + providerName + "/segment/" + segmentName + "/teamMember/" + theBossId)
           ));
        project.setDescription(projectDescription);

        //
        // create mandatory Task "Kick-off"
        //
        org.openmdx.example.workshop1.jmi1.Task task = workshop1Pkg.getTask().createTask();		
        project.addTask(
			false,
			org.openmdx.kernel.id.UUIDs.getGenerator().next().toString(),
			task
		);
        task.setName("Kick-off");
        task.setDefinition("initialize project");
        //
        // and now assign TeamMember "The Boss"
        //
        org.openmdx.example.workshop1.jmi1.TeamMemberAssignment assignment = workshop1Pkg.getTeamMemberAssignment().createTeamMemberAssignment();
        task.addAssignment(
			false,
			org.openmdx.kernel.id.UUIDs.getGenerator().next().toString(),
			assignment
		);
        assignment.setTeamMember(
        	(org.openmdx.example.workshop1.jmi1.TeamMember)pm.getObjectById(
        		new Path("xri:@openmdx:org.openmdx.example.workshop1/provider/" + providerName + "/segment/" + segmentName + "/teamMember/" + theBossId)
           ));
        assignment.setMemberRole("Project manager");

        if ((task1Name != null) && (task1Name.length() > 0)) {
          //
          // create optional Task 1
          //
          task = workshop1Pkg.getTask().createTask();
          project.addTask(
			false,
			org.openmdx.kernel.id.UUIDs.getGenerator().next().toString(),
			task
		  );
          task.setName(task1Name);
          task.setDefinition(task1Definition);
        }

        if ((task2Name != null) && (task2Name.length() > 0)) {
          //
          // create optional Task 2
          //
          task = workshop1Pkg.getTask().createTask();
          project.addTask(
			false,
			org.openmdx.kernel.id.UUIDs.getGenerator().next().toString(),
			task
		  );
          task.setName(task2Name);
          task.setDefinition(task2Definition);
        }

        //
        // create mandatory Task "Wrap-up"
        //
        task = workshop1Pkg.getTask().createTask();
        project.addTask(
			false,
			org.openmdx.kernel.id.UUIDs.getGenerator().next().toString(),
			task
		);
        task.setName("Wrap-up");
        task.setDefinition("finalize and close project");
        // and now assign TeamMember "The Boss"
        assignment = workshop1Pkg.getTeamMemberAssignment().createTeamMemberAssignment();
        task.addAssignment(
			false,
			org.openmdx.kernel.id.UUIDs.getGenerator().next().toString(),
			assignment
		);
        assignment.setTeamMember(
        	(org.openmdx.example.workshop1.jmi1.TeamMember)pm.getObjectById(
        		new Path("xri:@openmdx:org.openmdx.example.workshop1/provider/" + providerName + "/segment/" + segmentName + "/teamMember/" + theBossId)
           ));
        assignment.setMemberRole("Project manager");

        pm.currentTransaction().commit();
      }
      catch(Exception e) {
        hadException = true;
        out.println("<p><b>!! JSP <i>" + WIZARD_NAME + "</i> failed !!<br><br>The following exception(s) occured:</b><br><br><pre>");
        PrintWriter pw = new PrintWriter(out);
        ServiceException e0 = new ServiceException(e);
        pw.println(e0.getMessage());
        pw.println(e0.getCause());
        out.println("</pre></p>");
      	try {
      		pm.currentTransaction().rollback();
        }
        catch (Exception ex) {
          hadException = true;
          out.println("<p><b>!! Rollback failed !!<br><br>The following exception(s) occured:</b><br><br><pre>");
          PrintWriter pwx = new PrintWriter(out);
          ServiceException e1 = new ServiceException(ex);
          pwx.println(e1.getMessage());
          pwx.println(e1.getCause());
          out.println("</pre></p>");
        }
        throw e;
      }
	  if(!hadException) {
		Action nextAction = new ObjectReference(obj, app).getSelectObjectAction();
		response.sendRedirect(
			request.getContextPath() + "/" + nextAction.getEncodedHRef()
		);
  	  }
%>
      <h1>New project successfully created / <%= new java.util.Date() %></h1>
      <br />
      <br />
      <INPUT type="Submit" name="Cancel.Button" tabindex="9010" value="Close Window" onClick="javascript:window.close();" />
<%
    }
    if(actionCancel) {
	    // Go back to previous view
      Action nextAction =
        new Action(
    	  SelectObjectAction.EVENT_ID,
          new Action.Parameter[]{
            new Action.Parameter(Action.PARAMETER_OBJECTXRI, objectXri)
            },
          "", true
		  );
      response.sendRedirect(
         request.getContextPath() + "/" + nextAction.getEncodedHRef()
      );
    }
    else {
%>
      <form name="CreateProject" accept-charset="utf-8" method="post" action="<%= WIZARD_NAME %>">
      <input type="hidden" name="xri" value="<%= objectXri %>" /> <!-- preserve xri of calling object -->
      <table cellspacing="8" class="tableLayout">
        <tr>
          <td class="cellObject">
            <noscript>
              <div class="panelJSWarning" style="display: block;">
                <a href="../../helpJsCookie.html" target="_blank"><img class="popUpButton" src="../../images/help.gif" width="16" height="16" border="0" onclick="javascript:void(window.open('helpJsCookie.html', 'Help', 'fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no,width=400'));" alt="" /></a> <%= texts.getPageRequiresScriptText() %>
              </div>
            </noscript>
            <table class="objectTitle">
              <tr>
                <td>
                  <div style="padding-left:5px; padding-bottom: 3px;">
                    Create New Project
                  </div>
                </td>
              </tr>
            </table>
            <br />

            <div class="panel" id="panelObj0" style="display: block">

              <div class="fieldGroupName"><br /></div>
      	      <table class="fieldGroup" title="<%= projectName.length() == 0 ? "Project name missing" : "" %>">
      	        <tr>
      	          <td class="<%= CssClass.fieldLabel %>"><span class="nw">Project name <font color="red">*</font></span></td>
      	          <td>
      	            <input type="text" class="valueL" name="projectName" tabindex="100" value="<%= projectName %>" />
      	          </td>
      	          <td class="addon"><font color="red"><%= projectName.length() == 0 ? "!" : "" %></td>
      	          <td class="<%= CssClass.fieldLabel %>"><span class="nw">Project description</span></td>
      	          <td>
      	            <input type="text" class="valueL" name="projectDescription" tabindex="200" value="<%= projectDescription == null ? "" : projectDescription %>" />
      	          </td>
      	          <td class="addon"></td>
      	        </tr>
      	        <tr>
      	          <td class="<%= CssClass.fieldLabel %>"><span class="nw">Project manager</span></td>
      	          <td>
      	            <input type="text" class="valueLLocked" name="projectManager" readonly value="The Boss" />
      	          </td>
      	          <td class="addon"></td>
      	          <td class="<%= CssClass.fieldLabel %>"><span class="nw"></span></td>
      	          <td>&nbsp;</td>
      	          </td>
      	          <td class="addon"></td>
      	        </tr>
      	      </table>

              <br />
              <br />

              <div class="fieldGroupName"><br />Optional tasks in addition to mandatory tasks "Kick-off" and "Wrap-up"<br /><br /></div>
      	      <table class="fieldGroup">
      	        <tr>
      	          <td class="<%= CssClass.fieldLabel %>"><span class="nw">Task 0 name</span></td>
      	          <td>
      	            <input type="text" class="valueLLocked" name="task0Name" readonly value="Kick-off" />
      	          </td>
      	          <td class="addon"></td>
      	          <td class="<%= CssClass.fieldLabel %>"><span class="nw">Definition</span></td>
      	          <td>
      	            <input type="text" class="valueLLocked" name="task0Definition" readonly value="initialize project" />
      	          </td>
      	          <td class="addon"></td>
      	        </tr>
      	        <tr>
      	          <td class="<%= CssClass.fieldLabel %>"><span class="nw">Task 1 name</span></td>
      	          <td>
      	            <input type="text" class="valueL" name="task1Name" tabindex="1010" value="<%= task1Name == null ? "" : task1Name %>" />
      	          </td>
      	          <td class="addon"></td>
      	          <td class="<%= CssClass.fieldLabel %>"><span class="nw">Definition</span></td>
      	          <td>
      	            <input type="text" class="valueL" name="task1Definition" tabindex="1011" value="<%= task1Definition == null ? "" : task1Definition %>" />
      	          </td>
      	          <td class="addon"></td>
      	        </tr>
      	        <tr>
      	          <td class="<%= CssClass.fieldLabel %>"><span class="nw">Task 2 name</span></td>
      	          <td>
      	            <input type="text" class="valueL" name="task2Name" tabindex="1020" value="<%= task2Name == null ? "" : task2Name %>" />
      	          </td>
      	          <td class="addon"></td>
      	          <td class="<%= CssClass.fieldLabel %>"><span class="nw">Definition</span></td>
      	          <td>
      	            <input type="text" class="valueL" name="task2Definition" tabindex="1021" value="<%= task2Definition == null ? "" : task2Definition %>" />
      	          </td>
      	          <td class="addon"></td>
      	        </tr>
      	        <tr>
      	          <td class="<%= CssClass.fieldLabel %>"><span class="nw">Task 3 name</span></td>
      	          <td>
      	            <input type="text" class="valueLLocked" name="task3Name" readonly value="Wrap-up" />
      	          </td>
      	          <td class="addon"></td>
      	          <td class="<%= CssClass.fieldLabel %>"><span class="nw">Definition</span></td>
      	          <td>
      	            <input type="text" class="valueLLocked" name="task3Definition" readonly value="finalize and close project" />
      	          </td>
      	          <td class="addon"></td>
      	        </tr>
      	      </table>

              <br />

      	      <table class="fieldGroup">
      	        <tr>
      	          <td>
      	            <input type="Submit" name="OK.Button" class="<%= CssClass.btn.toString() + " " + CssClass.btnDefault.toString() %>" tabindex="9000" value="Create" />
            		<input type="Submit" name="Cancel.Button" class="<%= CssClass.btn.toString() + " " + CssClass.btnDefault.toString() %>" tabindex="9010" value="Cancel" />
      	          </td>
      	        </tr>
      	      </table>

            </div>
        	</td>
        </tr>
      </table>
      </form>
      <script language="javascript" type="text/javascript">
        document.forms.CreateProject.projectName.focus()
      </script>
<%
    }
    if(pm != null) {
    	pm.close();
    }
  }
  catch (Exception ex) {
    out.println("<p><b>!! JSP <i>" + WIZARD_NAME + "</i> failed !!<br><br>The following exception(s) occured:</b><br><br><pre>");
    PrintWriter pw = new PrintWriter(out);
    ServiceException e0 = new ServiceException(ex);
    pw.println(e0.getMessage());
    pw.println(e0.getCause());
    out.println("</pre></p>");
  }
  if (hadException) {
    try {
			Action nextAction = new ObjectReference(obj, app).getSelectObjectAction();
%>
      <br />
      <br />
      <INPUT type="Submit" name="Continue.Button" tabindex="1" value="Continue" onClick="javascript:location='<%= request.getContextPath() + "/" + nextAction.getEncodedHRef() %>';" />
<%
	  }
	  catch (Exception e) {};
  }
%>
      </div> <!-- content -->
    </div> <!-- content-wrap -->
	<div> <!-- wrap -->
</div> <!-- container -->
</body>
</html>
