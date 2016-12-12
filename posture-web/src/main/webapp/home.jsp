<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>

<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>drtti.io Home</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=GFS+Didot|Tinos|Tangerine" />
    <link rel="stylesheet" href="css/main.css" />
</head>

<body>
<p class="f-corner a"><a title="Care Factor" href="https://carefactor.org/">&alpha;</a></p>
<p class="f-corner o"><a title="Logout of DRTTI" href="/ccp/eve/sso/logout/">&Omega;</a></p>
<div class="f-center">
    <p class="h-main"><strong>d</strong>istributed <strong>r</strong>eal<strong>t</strong>ime <strong>t</strong>arget <strong>i</strong>ntelligence</p>
</div>
<div class="f-center">
    <p class="t-center">
      <pre><% out.print(request.getSession().getAttribute("AuthenticatedPilotName")); %></pre>
    </p>
</div>
</body>
</html>
