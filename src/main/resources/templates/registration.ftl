<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>

<h3>Add new user</h3>
        ${message?if_exists}

    <@l.login "/registration"/>

</@c.page>
