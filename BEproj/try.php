<?php
$con = mysql_connect("localhost","root","");
if (!$con)
{
die('Could not connect: ' . mysql_error());
}
mysql_select_db("users", $con);
$con = mysql_connect("localhost","root","");
if (!$con)
{
die('Could not connect: ' . mysql_error());
}
mysql_select_db("users", $con);
//$sql="select * from accountdtl";
$result = mysql_query("select fname,lname,domain,topic,abstract from guidedetail");
while($rowval = mysql_fetch_array($result))
 {
$fname= $rowval['fname'];
$lname= $rowval['lname'];
//$fname= $rowval['fname'];
//$lname= $rowval['lname'];
$topic= $rowval['topic'];
$domain= $rowval['domain'];
$abstract= $rowval['abstract'];
//$country= $rowval['country'];
//$pin= $rowval['pcode'];
//$mob= $rowval['con_no'];
//$mailid= $rowval['mail_id'];
//$uname= $rowval['uname'];
//$balance= $rowval['balance'];
}
mysql_close($con);
 
?>
<html>
<body>
<from >
 
  <table>
        <tr>
           
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>FirstName</td>
            <td class="auto-style4">
                <input id="Text1" type="text" value='<?php echo  $fname; ?>'/></td>
        </tr>
        <tr>
            <td>LastName</td>
            <td class="auto-style4">
                <input id="Text2" type="text" value='<?php echo  $lname; ?>'/></td>
        </tr>
       <!-- <tr>
             <td style="color:red>FirstName:</td>
            <td class="auto-style4">
                <input id="Text3" type="text" value='<?php echo  $fname; ?>' /></td>
        </tr>
        <tr>
             <td style="color:red;background-color:aqua;" class="auto-style3">Surname:</td>
            <td class="auto-style4">
                <input id="Text4" type="text" value='<?php echo  $lname; ?>' /></td>
        </tr>-->
        <tr>
            <td>Domain:</td>
            <td class="auto-style4" width="70%" height="50%">
                <input id="Text5" type="text" value='<?php echo  $domain; ?>' /></td>
        </tr>
        <tr>
           <td>Topic:</td>
            <td class="auto-style4">
                <input id="Text6" type="text" style="width:170%" value='<?php echo  $topic; ?>' ></td>
        </tr>
        <tr>
             <td>Abstract:</td>
            <td class="auto-style4">
                <input id="Text7" type="text" style="width:170%;height:70%" value='<?php echo  $abstract; ?>'/></td>
        </tr>
        <!--<tr>
             <td style="color:red;background-color:aqua;" class="auto-style3">Country:</td>
            <td class="auto-style4">
                <input id="Text8" type="text" value='<?php echo  $country; ?>' /></td>
        </tr>
        <tr>
             <td style="color:red;background-color:aqua;" class="auto-style3">Post Code:</td>
            <td class="auto-style4">
                <input id="Text9" type="text"  value='<?php echo  $pin; ?>'/></td>
        </tr>
        <tr>
             <td style="color:red;background-color:aqua;" class="auto-style3">Contact Number:</td>
            <td class="auto-style4">
                <input id="Text10" type="text" value='<?php echo  $mob; ?>'/></td>
        </tr>
        <tr>
            <td style="color:red;background-color:aqua;" class="auto-style3">Email Address:</td>
            <td class="auto-style4">
                <input id="Text11" type="text" value='<?php echo  $mailid; ?>'/></td>
        </tr>
        <tr>
             <td style="color:red;background-color:aqua;" class="auto-style3">User Name:</td>
            <td class="auto-style4">
                <input id="Text12" type="text" value='<?php echo  $uname; ?>'/></td>
        </tr>
        <tr>
            <td style="color:red;background-color:aqua;" class="auto-style3">Balance:</td>
            <td>
                <input id="Text13" type="text" value='<?php echo  $balance; ?>' /></td>
        </tr>
        <tr>
            <td></td>
        </tr>-->
    </table>
</form>
</body>
</html>