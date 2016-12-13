<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="io.drtti.eve.dom.sso.CcpEveSsoConfig" %>
<%@ page import="io.drtti.eve.dom.core.DrttiUser" %>
<%
    DrttiUser user = (DrttiUser) request.getSession().getAttribute(CcpEveSsoConfig.DRTTI_USER_KEY);
    if (user == null) {
        response.sendRedirect(request.getContextPath());
    }
%>
<!doctype html>

<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>drtti.io Home</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=GFS+Didot|Tinos|Tangerine"/>
    <link rel="stylesheet" href="css/main.css"/>
</head>

<body>
<p class="f-corner a"><a class="nl" title="Care Factor" href="https://carefactor.org/">&alpha;</a></p>
<p class="f-corner o"><a class="nl" title="Log out of DRTTI" href="/ccp/eve/sso/logout/">&Omega;</a></p>
<p class="f-corner user">
    <img alt="<% out.print( ((DrttiUser) request.getSession().getAttribute(CcpEveSsoConfig.DRTTI_USER_KEY)).getPilot().getCharacterName() ); %> Portrait" src="https://imageserver.eveonline.com/Character/<% out.print( ((DrttiUser) request.getSession().getAttribute(CcpEveSsoConfig.DRTTI_USER_KEY)).getPilot().getCharacterId() ); %>_64.jpg" />
    <% out.print( ((DrttiUser) request.getSession().getAttribute(CcpEveSsoConfig.DRTTI_USER_KEY)).getPilot().getCharacterName() ); %>&nbsp;[<a title="Log out" href="/ccp/eve/sso/logout/">Log out</a>]</p>
<div class="f-center">
    <p class="h-main"><strong>d</strong>istributed <strong>r</strong>eal<strong>t</strong>ime <strong>t</strong>arget <strong>i</strong>ntelligence</p>
</div>
<div class="f-center">
    <p class="t-center">
        <% out.print( ((DrttiUser) request.getSession().getAttribute(CcpEveSsoConfig.DRTTI_USER_KEY)).getPilot().getCharacterName() ); %> was located in <% out.print(request.getSession().getAttribute(CcpEveSsoConfig.DRTTI_EVE_PILOT_LOCATION_KEY)); %> at login.
    </p>
    <p class="t-center">
      <pre><% out.print(request.getSession().getAttribute(CcpEveSsoConfig.DRTTI_EVE_AUTHENTICATED_PILOT_KEY)); %></pre>
    </p>
</div>
</body>
</html>
