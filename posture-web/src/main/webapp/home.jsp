<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="io.drtti.eve.dom.sso.CcpEveSsoConfig" %>
<%@ page import="io.drtti.eve.dom.core.DrttiUser" %>
<%@ page import="org.apache.log4j.Logger" %>
<%
    Logger log = Logger.getLogger(this.getClass());

    DrttiUser user = (DrttiUser) request.getSession().getAttribute(CcpEveSsoConfig.DRTTI_USER_KEY);

    if (user == null) {
        log.error("Unable to get user in home.jsp; not set in HttpSession or HttpSession not created");
        log.error("Redirecting to CCP EVE SSO Callback endpoint");
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
    <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha256-/SIrNqv8h6QGKDuNoLGA4iret+kyesCkHGzVUUV0shc=" crossorigin="anonymous"></script>
    <script type="application/javascript">
        $(document).ready(function(){
            var dulssUri = 'wss://' + document.location.host + '/websocket/dulss';
            console.log(dulssUri);

            var dulssWs = new WebSocket(dulssUri);
            var dulssDiv = document.getElementById('dulss');

            dulssWs.onopen = function() { dulssDiv.innerHTML = 'OPENED WS: ' + dulssUri; };
            dulssWs.onclose = function() { dulssDiv.innerHTML = 'CLOSED WS: ' + dulssUri; };
            dulssWs.onmessage = function(e) {
                updateDulss(e.data);
            };

            function updateDulss(message) {
                dulssDiv.innerHTML = message;
            }
        });
    </script>
</head>

<body>
<p class="f-corner a"><a class="nl" title="Care Factor" href="https://carefactor.org/">&alpha;</a></p>
<p class="f-corner o"><a class="nl" title="Log out of DRTTI" href="/ccp/eve/sso/logout/">&Omega;</a></p>
<p class="f-corner user">
    <img alt="<% out.print(user.getPilot().getCharacterName()); %> Portrait" src="https://imageserver.eveonline.com/Character/<% out.print(user.getPilot().getCharacterId()); %>_64.jpg" />
    <% out.print(user.getPilot().getCharacterName()); %>&nbsp;[<a title="Log out" href="/ccp/eve/sso/logout/">Log out</a>]</p>
<div class="f-center">
    <p class="h-main"><strong>d</strong>istributed <strong>r</strong>eal<strong>t</strong>ime <strong>t</strong>arget <strong>i</strong>ntelligence</p>
</div>
<div class="f-center">
    <p class="t-center">
        <% out.print(user.getPilot().getCharacterName()); %> was located in <% out.print(request.getSession().getAttribute(CcpEveSsoConfig.DRTTI_EVE_PILOT_LOCATION_KEY)); %> at login.
    </p>
    <p class="t-center">
      <pre><% out.print(request.getSession().getAttribute(CcpEveSsoConfig.DRTTI_EVE_AUTHENTICATED_PILOT_KEY)); %></pre>
    </p>
</div>
<div class="f-center">
    <p class="t-center"><pre id="dulss"></pre></p>
</div>
</body>
</html>
