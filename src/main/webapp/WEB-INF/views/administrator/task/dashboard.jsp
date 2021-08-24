<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list2 readonly="true">
	<acme:list-column code="administrator.task.list.label.publicTasks" path="publicTasks" width="25%"/>
	<acme:list-column code="administrator.task.list.label.privateTasks" path="privateTasks" width="25%"/>
	<acme:list-column code="administrator.task.list.label.finishedTasks" path="finishedTasks" width="25%"/>	
	<acme:list-column code="administrator.task.list.label.nonFinishedTasks" path="nonFinishedTasks" width="25%"/>	
</acme:list2>

<acme:list2 readonly="true">	
	<acme:list-column code="administrator.task.list.label.averageWorkFlow" path="averageWorkFlow" width="25%"/>
	<acme:list-column code="administrator.task.list.label.deviationWorkFlow" path="deviationWorkFlow" width="25%"/>
	<acme:list-column code="administrator.task.list.label.maxWorkFlow" path="maxWorkFlow" width="25%"/>	
	<acme:list-column code="administrator.task.list.label.minWorkFlow" path="minWorkFlow" width="25%"/>	
</acme:list2>

<acme:list2 readonly="true">
	<acme:list-column code="administrator.task.list.label.averageExecutionPeriod" path="averageExecutionPeriod" width="25%"/>
	<acme:list-column code="administrator.task.list.label.deviationExecutionPeriod" path="deviationExecutionPeriod" width="25%"/>
	<acme:list-column code="administrator.task.list.label.maxExecutionPeriod" path="maxExecutionPeriod" width="25%"/>	
	<acme:list-column code="administrator.task.list.label.minExecutionPeriod" path="minExecutionPeriod" width="25%"/>
</acme:list2>


