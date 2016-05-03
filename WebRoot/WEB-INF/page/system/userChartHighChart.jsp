<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户按照所属单位分报表统计</title>
<LINK href="${pageContext.request.contextPath }/css/Style.css" type="text/css" rel="stylesheet">
<script type="text/javascript"  src="${pageContext.request.contextPath }/script/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="${pageContext.request.contextPath }/script/highchart/highcharts.js"></script>
<script type="text/javascript"  src="${pageContext.request.contextPath }/script/highchart/highcharts-3d.js"></script>
<script type="text/javascript"  src="${pageContext.request.contextPath }/script/highchart/modules/exporting.js"></script>
<style type="text/css">
#container, #sliders {
	min-width: 310px; 
	max-width: 800px;
	margin: 0 auto;
}
#container {
	height: 400px; 
}
		</style>
		<script type="text/javascript">
		$(function () {
			var jsonXData = [];
			var jsonYData = []; 
			$.post("elecUserAction_highChartUser.do",{},function(data){
				if(data!=null && data.length>0){
					for(var i=0;i<data.length;i++){
						//alert(data[i].name);
						//alert(data[i].data);
						jsonXData.push(data[i].name);
						jsonYData.push(parseInt(data[i].data));
					}
				}
				var chart = new Highcharts.Chart({
			        chart: {
			            renderTo: 'container',
			            type: 'column',
			            margin: 75,
			            options3d: {
			                enabled: true,
			                alpha: 15,
			                beta: 15,
			                depth: 50,
			                viewDistance: 25
			            }
			        },
			        title: {
			            text: '用户按照所属单位分报表统计'
			        },
			        subtitle: {
			            text: '用户统计报表（所属单位）'
			        },
			        xAxis: {
			            //categories: [name]
			        	categories: jsonXData
			        },
			        yAxis: {
			            min: 0,
			            title: {
			                text: '数量'
			            }
			        },
			        plotOptions: {
			            column: {
			                depth: 25
			            }
			        },
			        series: [{
			        	name: "所属单位",
			            data: jsonYData
			        	//data:[data]
			        }]
			    });
			});
		    
		
		    function showValues() {
		        $('#R0-value').html(chart.options.chart.options3d.alpha);
		        $('#R1-value').html(chart.options.chart.options3d.beta);
		    }
		
		    // Activate the sliders
		    $('#R0').on('change', function () {
		        chart.options.chart.options3d.alpha = this.value;
		        showValues();
		        chart.redraw(false);
		    });
		    $('#R1').on('change', function () {
		        chart.options.chart.options3d.beta = this.value;
		        showValues();
		        chart.redraw(false);
		    });
		
		    showValues();
		});
		</script>
	</head>
<body>
<div id="container"></div>
</body>
</html>