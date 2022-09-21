<#import "parts/common.ftlh" as c>
<#import "parts/loginForm.ftlh" as lf>

<@c.page "Registration">
    Add new user
    ${message}
    <@lf.login "/registration"/>
</@c.page>