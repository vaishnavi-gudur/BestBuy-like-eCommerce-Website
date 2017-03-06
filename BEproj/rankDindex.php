<html>
<head>
<title> Project Guide </title>
<link href="style.css" rel="stylesheet" type="text/css">
<style>
p {
    font-family: sans-serif;
}

label.custom-select {
    position: relative;
    display: inline-block;
}

    .custom-select select {
        display: inline-block;
        border: 2px solid #bbb;
        padding: 4px 3px 3px 5px;
        margin: 0;
        font: inherit;
        outline:none; /* remove focus ring from Webkit */
        line-height: 1.2;
        background: #f8f8f8;
        
        -webkit-appearance:none; /* remove the strong OSX influence from Webkit */
        
        -webkit-border-radius: 6px;
        -moz-border-radius: 6px;
        border-radius: 6px;
    }

    /* for Webkit's CSS-only solution */
    @media screen and (-webkit-min-device-pixel-ratio:0) { 
        .custom-select select {
            padding-right:30px;    
        }
    }
    
    /* Since we removed the default focus styles, we have to add our own */
    .custom-select select:focus {
        -webkit-box-shadow: 0 0 3px 1px #c00;
        -moz-box-shadow: 0 0 3px 1px #c00;
        box-shadow: 0 0 3px 1px #c00;
    }
    
    /* Select arrow styling */
    .custom-select:after {
        content: "â–¼";
        position: absolute;
        top: 0;
        right: 0;
        bottom: 0;
        font-size: 60%;
        line-height: 30px;
        padding: 0 7px;
        background: #bbb;
        color: white;
        
        pointer-events:none;
        
        -webkit-border-radius: 0 6px 6px 0;
        -moz-border-radius: 0 6px 6px 0;
        border-radius: 0 6px 6px 0;
    }
    
    .no-pointer-events .custom-select:after {
        content: none;
    }
</style>
</head>
<body>
<div id="head">
<font size="200px">B.E. PROJECT PORTAL</font> 
</div>
<div class="content">
<div class="header">Guide Portal</div>
<div class="sidebar">

<b>
<br><br>
&raquo; <a href="membersguide.php" style="color:#500000;text-decoration:none">Home</a><br><br>
&raquo; <a href="rankDindex.html" style="color:#500000;text-decoration:none">Rank Domain</a><br><br>
&raquo; <a href="" style="color:#500000;text-decoration:none">Approve Topics&Tasks</a><br><br>
&raquo; <a href="GuideDomainCheckTask.html" style="color:#500000;text-decoration:none">Check Task</a><br><br>
&raquo; <a href="" style="color:#500000;text-decoration:none">Grant Extension</a><br><br>
&raquo; <a href="" style="color:#500000;text-decoration:none">Add Notices</a><br><br>
&raquo; <a href="" style="color:#500000;text-decoration:none">Send Mail</a><br><br>
&raquo; <a href="guidelogout.php" style="color:#500000;text-decoration:none">Logout</a><br><br>
</br>

</div>
<div class= "main">
<h1>Rank Domain</h1>
</div>

<!--<div class= "rankDtable">
<h1>List Of Domains</h1>
&nbsp;&nbsp;&nbsp;Cloud Computing: 1		  <br><br>
&nbsp;&nbsp;&nbsp;Data Mining: 2			 <br><br>
&nbsp;&nbsp;&nbsp;Artificial Intelligence :3  <br><br>
&nbsp;&nbsp;&nbsp;Networking :4 				  <br><br>


</div>
-->

<div class= "rankDdrop">

<form id="nameform"  method="post" action="rankDindex.php">
<p>&nbsp;&nbsp;&nbsp;Domain 1:
<select name="d1">
  <option value="blank"></option>
  <option value="Cloud Computing" name="d1">Cloud Computing</option> 
  <option value="Data Mining" name="d2">Data Mining</option>
  <option value="Artificial Intelligence" name="d3">Artificial Intelligence</option>
  <option value="Networking" name="d4">Networking</option>
 
</select>

<p>&nbsp;&nbsp;&nbsp;Domain 2:
<select name="d2"> 
<option value="blank"></option>
  <option value="Cloud Computing">Cloud Computing</option> 
  <option value="Data Mining">Data Mining</option>
  <option value="Artificial Intelligence">Artificial Intelligence</option>
  <option value="Networking">Networking</option>
 
</select>

<p>&nbsp;&nbsp;&nbsp;Domain 3:
<select name="d3">
<option value="blank"></option>
  <option value="Cloud Computing">Cloud Computing</option> 
  <option value="Data Mining">Data Mining</option>
  <option value="Artificial Intelligence">Artificial Intelligence</option>
  <option value="Networking">Networking</option>
 
</select>

<p>&nbsp;&nbsp;&nbsp;Domain 4:
<select name="d4">
<option value="blank"></option>
  <option value="Cloud Computing">Cloud Computing</option> 
  <option value="Data Mining">Data Mining</option>
  <option value="Artificial Intelligence">Artificial Intelligence</option>
  <option value="Networking">Networking</option>
 
</select>
<br><br>
<input id="saveForm" type="submit" name="submit" value="Rank" />
</form>
</div>


<div id="footer">
<br>

</div>
</body>
</html>


	
	