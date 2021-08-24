<%--
- form.jsp
-
- Copyright (C) 2012-2021 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox  code="anonymous.workplan.list.label.startDate" path="startDate" />
	<acme:form-textbox  code="anonymous.workplan.list.label.endDate" path="endDate"/>
	<acme:form-textbox  code="anonymous.workplan.list.label.workLoad" path="workLoad" />
	<acme:form-textbox  code="anonymous.workplan.list.label.publicPlan" path="publicPlan" />
	
	<table class="table table-striped table-condensed table-hover nowrap w-100"> 
	<thead><tr>
		<th><acme:message code="anonymous.workplan.tasks.title"/></th>
		<th><acme:message code="anonymous.workplan.tasks.description"/></th>
		<th><acme:message code="anonymous.workplan.tasks.workflow"/></th>
	</tr></thead>
	<tbody>
		<jstl:forEach items="${tasks}" var="task">
			<tr class="table-light">
				<td><jstl:out value="${task.title}"/></td>
				<td><jstl:out value="${task.description}"/></td>
				<td><jstl:out value="${task.workFlow}"/></td>
			</tr>
		</jstl:forEach>
	</tbody>
	</table>
	
	<acme:form-return code="anonymous.provider.form.button.return"/>
</acme:form>
