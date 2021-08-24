<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<acme:form>
<jstl:if test="${command=='create' }">
	<acme:form-moment code="manager.workplan.form.label.startDate" path="startDate"/>
	<acme:form-moment code="manager.workplan.form.label.endDate" path="endDate"/>
	<acme:form-submit code="manager.workplan.form.button.create" action="/manageracc/workplan/create"/>
	
</jstl:if>

<jstl:if test="${command!='create' && canUpdate}">
	<acme:form-moment code="manager.workplan.form.label.startDate" path="startDate"/>
	<acme:message code="manager.workplan.message.recommend"/>
	<fmt:formatDate value="${startRecommend}" pattern="yyyy/MM/dd HH:mm" type = "both" dateStyle="short"/>
	<acme:form-moment code="manager.workplan.form.label.endDate" path="endDate"/>
	<acme:message code="manager.workplan.message.recommend"/>

	<fmt:formatDate value="${finalRecommend}" pattern="yyyy/MM/dd HH:mm" type = "both" dateStyle="short"/>

	<acme:form-double readonly="true" code="manager.workplan.form.label.workLoad" path="workLoad"/>
	<acme:form-checkbox readonly="true" code="manager.workplan.form.label.publicPlan" path="publicPlan"/>

	<acme:form-submit code="manager.workplan.form.button.update" action="/manageracc/workplan/update"/>
	<acme:form-submit code="manager.workplan.form.button.delete" action="/manageracc/workplan/delete"/>
</jstl:if>

<jstl:if test="${command!='create' && !canUpdate}">
	<acme:form-moment readonly="true" code="manager.workplan.form.label.startDate" path="startDate"/>
	<acme:form-moment readonly="true" code="manager.workplan.form.label.endDate" path="endDate"/>

	<acme:form-double readonly="true" code="manager.workplan.form.label.workLoad" path="workLoad"/>
	<acme:form-checkbox readonly="true" code="manager.workplan.form.label.publicPlan" path="publicPlan"/>
</jstl:if>
<jstl:if test="${command=='delete'}">
	<acme:form-submit code="manager.workplan.form.button.delete" action="/manageracc/workplan/delete"/>
</jstl:if>
<jstl:if test="${command!='create' && !publicTask && !publicPlan}"> 
	<acme:form-submit code="manager.workplan.form.button.publish" action="/manageracc/workplan/publish"/>
</jstl:if>

	<acme:form-return  code= "manager.workplan.form.button.return"/>
</acme:form>

<br>
<jstl:if test="${command!='create'}">
<table class="table table-striped table-condensed table-hover nowrap w-100"> 
		<thead><tr>
			<th scope="col"><acme:message code="manager.workplan.tasks.title"/></th>
			<th scope="col"><acme:message code="manager.workplan.tasks.description"/></th>
			<th scope="col"><acme:message code="manager.workplan.tasks.workflow"/></th>
			<th scope="col"></th>
		</tr></thead>
<jstl:if test="${tasks.size()>0 }">
		<tbody>
			<jstl:forEach items="${tasks}" var="task">
				<tr class="table-light">
					<td><jstl:out value="${task.title}"/></td>
					<td><jstl:out value="${task.description}"/></td>
					<td><jstl:out value="${task.workFlow}"/></td>
					 <jstl:if test="${canUpdate}"><td><acme:form>
						<input type="hidden" name="deleteTask" value="${task.id}"/>
						<acme:form-hidden path="id"/>
						<acme:form-submit code="manager.workplan.form.button.delete" action="/manageracc/workplan/delete_task"/>
					</acme:form></td></jstl:if>
				</tr>
			</jstl:forEach>
		</tbody>
		 </jstl:if>
</table>
</jstl:if>

 
 	 <jstl:if test="${command!='create' && canUpdate}">
	<acme:form>
		<acme:form-select code="manager.workplan.form.addTask" path="addTask">
			<jstl:forEach items="${tasksInsert}" var="task">
				<acme:form-option code="${task.title}" value="${task.id}"/>
			</jstl:forEach>
		</acme:form-select>
		<acme:form-submit code="manager.workplan.form.button.addTask" action="/manageracc/workplan/add_task"/>
	</acme:form>
	</jstl:if>



