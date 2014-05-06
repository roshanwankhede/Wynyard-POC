<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="/Wynyard-POC/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="/Wynyard-POC/js/jquery-ui-1.8.20.custom.min.js"></script>
<script type="text/javascript" src="/Wynyard-POC/js/search.js"></script>
<style type="text/css" ></style>
<title>Wynyard POC</title>
<script type="text/javascript">

</script>
<style type="text/css">
#top{
clear:both; 
width: 100%; 
margin-bottom: 30px; 
margin-top: 20px;
}

#upright{
width: 20%; 
height:100%;
float: left; 
background: white;
}
#dataSource{
margin-top: 50px
}

#selected_dataSources{
margin-top: 20px;
}

#search_result{
width: 80% ; 
height:100%; 
float:left;
background: white;
}

#search_header_div{
margin-bottom: 20px;
margin-left: 30px;
}

</style>
</head>
<!-- <body onload="displaySearchData()"> -->
<body onload="clearData()">

<div id="top">
	<label><b>Search</b> &nbsp;&nbsp;</label><input  name=""  value="" size="100%" type="text" id="Search_Input"/>
	<button name="go" onclick="getSearchFromButton()">Go</button>	
</div>
<div id="upright">
	<div id="dataSource">
		<label><b style="font-size: 16">DataSources</b></label><br>
	</div>
	<div id="selected_dataSources">
		<input type="checkbox" name="dataSourceArray[]" value="Investigator">Investigator<br>		
		<input type="checkbox" name="dataSourceArray[]" value="OCG-Document">OCG<br>
		<input type="checkbox" name="dataSourceArray[]" value="SharePoint">SharePoint<br>
	</div> 
</div>
<div id="search_result">
	<div id="search_header_div">
		<label id="docFoundId" style="color: red"></label>&nbsp;&nbsp;&nbsp;
		<label id="respTimeId" style="color: red"></label>
	</div>
	<div id="search_details_div">
		<!-- <ul>
			<li>Search Result 1</li>
			<li>Search Result 2</li>
			<li>Search Result 3</li>
			<li>Search Result 4</li>
			<li>Search Result 5</li>
			<li>Search Result 6</li>
		</ul> -->
	</div>	
</div>

</body>
</html>