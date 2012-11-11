<?php
$firstName=$_POST['firstName'];
$middleName=$_POST['middleName'];
$lastName=$_POST['lastName'];
$address=$_POST['address'];
$city=$_POST['city'];
$state=$_POST['state'];
$zipCode=$_POST['zipCode'];
$loginName=$_POST['loginName'];
$password=$_POST['password'];

$checkLoginName="<loginName>".$loginName."</loginName>";
@ $fp=fopen("regHistory.xml",'rb');
$checkXmlSize=filesize("regHistory.xml");
$checkOriginalInfo=fgets($fp,$checkXmlSize+1);
fclose($fp);

if(strstr($checkOriginalInfo,$checkLoginName))
{
    echo "<html><head><title>Error</title></head><body>Your Login Name has already been used, please <a href='fillInfo.html'>go back to the form </a>to choose another name.</body></html>";
}else
{
   $xmlInfo="<user><firstName>".$firstName."</firstName><middleName>".$middleName."</middleName><lastName>".$lastName."</lastName><address>".$address."</address><city>".$city."</city><state>".$state."</state><zipCode>".$zipCode."</zipCode><loginName>".$loginName."</loginName><password>".$password."</password></user></users>";


   @ $fp=fopen("regHistory.xml",'rb');

   $xmlSize=filesize("regHistory.xml");
   $originalInfo=fgets($fp,$xmlSize+1);
   $subOriginalInfo=substr($originalInfo,0,$xmlSize-8);
   $totalXmlInfo=$subOriginalInfo.$xmlInfo;
   fclose($fp);

   @ $fp=fopen("regHistory.xml",'wb');

   fwrite($fp,$totalXmlInfo,strlen($totalXmlInfo));
   fclose($fp);

   echo "<html><head><title>Confirm Page</title></head><body>You have already created your account. <a href='phpExercise.html'>Please click here to go back main menu.</a></body></html>";
}
?>