<#---------------IMPORTS--------------------->
<#import "/com/qprogramming/weather/views/main.ftl" as main>
<#-- @ftlvariable name="content" type="com.qprogramming.weather.views.IndexView" -->
<#------------RENDER PART-------------------->
<@main.view>
	NESTED : ${view?html}</br>
	<a href="/charts/1">Meter 1</a></br>
	<a href="/charts/2">Meter 2</a>
</@main.view>
