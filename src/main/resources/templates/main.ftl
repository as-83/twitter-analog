<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>

    <@l.logout/>


    <div>
        <form method="post">
            <input type="text" name="text" placeholder="Text"/>
            <input type="text" name="tag" placeholder="#tag"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit">Add</button>
        </form>
    </div>
    <div>
        <form method="get" action="/main">
            <input type="text" name="filter" value="${filter}"/>
             <button type="submit">Find</button>
        </form>
    </div>
    <div> Messages !</div>
        <#list messages as message>

            <div>
                <b>${message.id}</b>
                <span>${message.text}</span>
                <i>${message.tag}</i>
                <strong>${message.authorName}</strong>

            </div>
            <#else > No messages

        </#list>



</@c.page>

