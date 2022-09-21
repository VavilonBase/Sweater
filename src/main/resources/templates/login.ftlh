<#import "parts/common.ftlh" as c>
<#import "parts/loginForm.ftlh" as lf>

<@c.page "Login">
    Login page
    <@lf.login "/login" />
    <a href="/registration">Регистрация</a>
</@c.page>