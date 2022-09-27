<#import "parts/common.ftlh" as c>
<#import "parts/loginForm.ftlh" as lf>

<@c.page "Login">
    <h5 class="mb-3">Login page</h5>
    ${message?ifExists}
    <@lf.login "/login" false/>
</@c.page>