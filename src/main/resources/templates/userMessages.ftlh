<#import "parts/common.ftlh" as c>
<#import "parts/loginForm.ftlh" as lf>
<#include "parts/security.ftlh" />
<@c.page "User messages">
    <h3>${userChannel.username}</h3>
    <#if !isCurrentUser>
        <#if !isSubscriber>
            <a href="/user/subscribe/${userChannel.id}" class="btn btn-info">Подписаться</a>
        <#else>
            <a href="/user/unsubscribe/${userChannel.id}" class="btn btn-info">Отписаться</a>
        </#if>
    </#if>
    <div class="row row-cols-1 row-cols-md-2 g-4">
        <div class="col">
            <div class="card my-3">
                <h3 class="card-title">Subscriptions</h3>
                <h3 class="card-text">
                    <a href="/user/subscriptions/${userChannel.id}/list">${subscriptionsCount}</a>
                </h3>
            </div>
        </div>
        <div class="col">
            <div class="card my-3">
                <h3 class="card-title">Subscribers</h3>
                <h3 class="card-text">
                    <a href="/user/subscribers/${userChannel.id}/list">${subscribersCount}</a>
                </h3>
            </div>
        </div>
    </div>
    <#if isCurrentUser>
        <#include "parts/messageEdit.ftlh" />
    </#if>
    <#include "parts/messageList.ftlh" />
</@c.page>