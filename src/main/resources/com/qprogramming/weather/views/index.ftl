<#---------------IMPORTS--------------------->
<#import "/com/qprogramming/weather/views/main.ftl" as main>
<#-- @ftlvariable name="content" type="com.qprogramming.weather.views.IndexView" -->
<#------------RENDER PART-------------------->
<@main.view>
	NESTED : ${view?html}
</@main.view>
