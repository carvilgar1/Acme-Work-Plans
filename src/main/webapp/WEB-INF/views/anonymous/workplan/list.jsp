<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="anonymous.workplan.list.label.startDate" path="startDate" width="40%"/>
	<acme:list-column code="anonymous.workplan.list.label.endDate" path="endDate" width="40%"/>
	<acme:list-column code="anonymous.workplan.list.label.workLoad" path="workLoad" width="20%"/>
</acme:list>