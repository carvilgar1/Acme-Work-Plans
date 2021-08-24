<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:if test="${command=='create' }">
	<acme:form>
		<acme:form-textbox code= "manager.mytasks.form.label.title" path="title"/>
		<acme:form-moment code= "manager.mytasks.form.label.startDate" path="startDate"/>
		<acme:form-moment code= "manager.mytasks.form.label.endDate" path="endDate"/>
		<acme:form-textarea code= "manager.mytasks.form.label.description" path="description"/>
		<acme:form-url code="manager.mytasks.form.label.url" path="url"/>
			
		<acme:form-submit code= "manager.mytasks.form.button.create" action="/manageracc/task/create"/>
		<acme:form-return  code= "manager.mytasks.form.button.return"/>
	</acme:form>
</jstl:if>
<jstl:if test="${command!='create' }">
	<acme:form>
		<acme:form-textbox code= "manager.mytasks.form.label.title" path="title"/>
		<acme:form-moment  readonly="true" code= "manager.mytasks.form.label.startDate" path="startDate"/>
		<acme:form-moment code= "manager.mytasks.form.label.endDate" path="endDate"/>
		<acme:form-double code= "manager.mytasks.form.label.workFlow" path="workFlow"/>
		<acme:form-textarea code= "manager.mytasks.form.label.description" path="description"/>
		<jstl:if test="${publicTask==false }">
			<acme:form-checkbox code="manager.mytasks.form.label.publicTask" path="publicTask"/>
		</jstl:if>
		<jstl:if test="${publicTask==true }">
			<acme:form-checkbox code="manager.mytasks.form.label.publicTask" path="publicTask" readonly="true"/>
		</jstl:if>
		<acme:form-url code="manager.mytasks.form.label.url" path="url"/>
			
		<acme:form-submit code= "manager.mytasks.form.button.update" action="/manageracc/task/update"/>
     		<acme:form-submit code="manager.mytasks.form.button.delete" action="/manageracc/task/delete"/>
		<acme:form-return  code= "manager.mytasks.form.button.return"/>
	</acme:form>
</jstl:if>