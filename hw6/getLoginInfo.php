<?php
$checkFlag=true;
$loginName=$_POST['loginName'];
$password=$_POST['password'];


@ $fp=fopen("regHistory.xml",'rb');
$checkXmlSize=filesize("regHistory.xml");
$checkOriginalInfo=fgets($fp,$checkXmlSize+1);
fclose($fp);

$arr=split('<users>|</users>|<user>|</user>|<firstName>|</firstName>|<middleName>|</middleName>|<lastName>|</lastName>|<address>|</address>|<city>|</city>|<state>|</state>|<zipCode>|</zipCode>|<loginName>|</loginName>|<password>|</password>',$checkOriginalInfo);

for($i=0;$i<=count($arr)-2;$i++)
{
	if($loginName == $arr[$i] && $password == $arr[$i+2])
	{
		$checkFlag=false;
		$url="http://www.flixster.com/showtimes/".$arr[$i-2];
		@$contents = file_get_contents($url);
        
		if($contents)
		{

    		$firstTheater=split('<div class="theater">|<div class="links clearfix">',$contents);
            $secondSplitTheater=split('<div class="address">|<div class="mtitle">',$firstTheater[1]);
		    $theaterName=split('<|>',$secondSplitTheater[0]);
		    $theaterAddress=split('<|>',$secondSplitTheater[1]);
        

		    echo "<html><head><center><b>Welcome ".$arr[$i-14]."!</b><p>You are from ".$arr[$i-8]." ".$arr[$i-6]." ".$arr[$i-4]." ".$arr[$i-2]."</p></center></head><body><center><p><b><font size='5'>The Movies in Theater today</font></b></p></center><center><table border='2' width='1000'><tr><td align='center' colspan='4'><b>".$theaterName[4]."</b><p>".$theaterAddress[2]."</p></td></tr><tr><td align='center'>Cover</td><td align='center'>Title</td><td align='center'>Duration</td><td align='center'>Showtime</td></tr>";

		    for($j=0;$j<=count($secondSplitTheater)-3;$j++)
		    {
		        $movieInfo=split('<div class="times">|<span>|</span>',$secondSplitTheater[$j+2]);
		        $movieTitleAndCover=split('<|>',$movieInfo[0]);
		        $movieCoverUrl=split('"',$movieTitleAndCover[1]);
                $finalMovieCoverUrl="http://www.flixster.com".$movieCoverUrl[1];
		        $coverContents=file_get_contents($finalMovieCoverUrl);
		        $imgSplit=split('<div class="poster">|<div class="actions">',$coverContents);
		        $imgSecondSplit=split('<|>',$imgSplit[1]);
		        $finalImgUrl=split('"',$imgSecondSplit[3]);
		
	        	echo "<tr><td align='center'><img src='".$finalImgUrl[1]."'></img></td><td align='center'>".$movieTitleAndCover[2]."</td><td align='center'>".$movieInfo[1]."</td><td align='center'>".$movieInfo[3]."</td></tr>";
		    }
		    echo "</table></center><p>Please <a href='http://cs-server.usc.edu:26378/phpExercise.html'>click here</a> to go back home page.</p></body></html>";
		}
		else
			echo "<html><head></head><body>No theaters or no movies at the location.<p>Please <a href='http://cs-server.usc.edu:26378/phpExercise.html'>click here</a> to go back home page.</p></body></html>";
	}
}

if($checkFlag)
  echo "<html><head><title>Error</title></head><body>Your Login Name or password is wrong, please <a href='phpExercise.html'>go back to try again.</a></body></html>";

?>