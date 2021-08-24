<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list2 readonly="true">
	<acme:list-column code="administrator.workplan.list.label.publicWorkPlans" path="publicWorkPlans" width="25%"/>
	<acme:list-column code="administrator.workplan.list.label.privateWorkPlans" path="privateWorkPlans" width="25%"/>
	<acme:list-column code="administrator.workplan.list.label.finishedWorkPlans" path="finishedWorkPlans" width="25%"/>	
	<acme:list-column code="administrator.workplan.list.label.nonFinishedWorkPlans" path="nonFinishedWorkPlans" width="25%"/>	
</acme:list2>

<acme:list2 readonly="true">	
	<acme:list-column code="administrator.workplan.list.label.averageWorkLoad" path="averageWorkLoad" width="25%"/>
	<acme:list-column code="administrator.workplan.list.label.deviationWorkLoad" path="deviationWorkLoad" width="25%"/>
	<acme:list-column code="administrator.workplan.list.label.maxWorkLoad" path="maxWorkLoad" width="25%"/>	
	<acme:list-column code="administrator.workplan.list.label.minWorkLoad" path="minWorkLoad" width="25%"/>	
</acme:list2>

<acme:list2 readonly="true">
	<acme:list-column code="administrator.workplan.list.label.averageExecutionPeriod" path="averageExecutionPeriod" width="25%"/>
	<acme:list-column code="administrator.workplan.list.label.deviationExecutionPeriod" path="deviationExecutionPeriod" width="25%"/>
	<acme:list-column code="administrator.workplan.list.label.maxExecutionPeriod" path="maxExecutionPeriod" width="25%"/>	
	<acme:list-column code="administrator.workplan.list.label.minExecutionPeriod" path="minExecutionPeriod" width="25%"/>
</acme:list2>


<div class="row text-center">
	<h2>Total Plans: <jstl:out value="${publicWorkPlans + privateWorkPlans}"/> </h2>
</div>
<div class="row text-center" style="height: 300px;">
	<canvas id="myChart"></canvas>
</div>


<script>
var publishedPlans = "${publicWorkPlans}";
var nonPublishedPlans = "${privateWorkPlans}";

var ctx = document.getElementById('myChart').getContext("2d");
var myChart = new Chart(ctx, {
    type: 'pie',
    data: {
        labels: ['Published Plans', 'Non Published Plans'],
        datasets: [{
            label: 'Work Plans Chart',
            data: [publishedPlans, nonPublishedPlans],
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)'
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)'
            ],
            borderWidth: 1
        }]
    },
    options: {
    	responsive: true,
    	maintainAspectRatio: false,
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
});
</script>


