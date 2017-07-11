<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="http://localhost:8080/appserver/driver/addCarInfo" method="post" enctype="multipart/form-data">
        <input type="text" name="carInfo" value='{"address":"地址：not null","carLength":"车长：not null","carLoad":"载重：not null","carModels":"车型号：not null","plateno":"车牌：not null","userId":100}'/><br />
        <input type="text" name="sign" value="00-23-5A-15-99-42"/><br />
        <%--<input type="file" name="img" id="file" />--%>
        <%--<br />--%>
        <input type="submit" name="submit" value="Submit" />
    </form>
    <br>
    <hr>
    <form action="http://localhost:8080/appserver/driver/publishCarInfo" method="post">
        <input type="text" name="truckInfo" value='{"endPlace":"not null","loadTime":"not null","startPlace":"not null","truckId":100}'/><br />
        <input type="text" name="sign" value="00-23-5A-15-99-42"/><br />
        <%--<input type="file" name="img" id="file" />--%>
        <%--<br />--%>
        <input type="submit" name="submit" value="Submit" />
    </form>
</body>
</html>
