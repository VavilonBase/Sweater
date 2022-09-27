<#import "parts/common.ftlh" as c>
<#import "parts/loginForm.ftlh" as lf>

<@c.page "Registration">
    <h5 class="mb-3">Add new user</h5>
    ${message?ifExists}
    <@lf.login "/registration" true/>
</@c.page>