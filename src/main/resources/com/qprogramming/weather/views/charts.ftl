<#---------------IMPORTS--------------------->
<#import "/com/qprogramming/weather/views/main.ftl" as main>
<#-- @ftlvariable name="content" type="com.qprogramming.weather.views.ChartsView" -->
<#------------RENDER PART-------------------->
<@main.view>
	NESTED : ${view?html} for meter ${meter?html}
</@main.view>
