<#---------------IMPORTS---------------------> <#import
"/com/qprogramming/weather/views/main.ftl" as main> <#-- @ftlvariable
name="content" type="com.qprogramming.weather.views.ChartsView" -->
<#------------RENDER PART--------------------> <@main.view> NESTED :
${view?html} for meter ${meter?html}
<div id="container" style="height: 500px"></div>
<script>
	$(function() {
		var url = '/api/meter/${meter}/values';
		$.get(url , function(data) {
		    $('#container').highcharts({
		    	chart: {
		            type: 'spline'
		        },
		        title: {
		            text: 'Temperatures'
		        },

		        xAxis: {
		            type: 'datetime'
		        },

		        yAxis: [{
		        	labels: {
		                format: '{value}°C',
		                style: {
		                    color: Highcharts.getOptions().colors[1]
		                }
		            },
		            floor: 0,
		            ceiling: 60,
		            title: {
		            	text: 'Temperature',
		                style: {
		                    color: Highcharts.getOptions().colors[1]
		                }
		            }
		        },{
		        	title: {
		                text: 'Humidity',
		                style: {
		                    color: Highcharts.getOptions().colors[0]
		                }
		            },
		            floor: 0,
		            ceiling: 100,
		            tickPositions: [0, 25, 50, 75, 100],
		            labels: {
		                format: '{value} %',
		                style: {
		                    color: Highcharts.getOptions().colors[0]
		                }
		            },
		            opposite: true
		        }],

		        tooltip: {
		            crosshairs: true,
		            shared: true,
		            valueSuffix: '°C'
		        },

		        legend: {
		        },

		        series: [{
		            name: 'Humidity',
		            type: 'column',
		            yAxis: 1,
		            data: data.humidity,
		            tooltip: {
		                valueSuffix: ' %'
		            }

		        },{
		            name: 'Temperature',
		            data: data.temperature,
		            lineWidth: 2,
		            zIndex: 1,
		            tooltip: {
		                valueSuffix: ' °C'
		            },
		            marker: {
		            	radius: 4,
		                fillColor: 'white',
		                lineWidth: 4,
		                lineColor: Highcharts.getOptions().colors[1]
		            }
		        }]
		    });
		});
	});
</script>
</@main.view>
