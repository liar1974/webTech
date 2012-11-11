<?php

$zipCode=$_GET['zipCode'];






	
		$url="http://community.flixster.com/showtimes/".$zipCode;
		@$contents = file_get_contents($url);
        
     
    		$firstTheater=split('<div class="theater">|<div class="links clearfix">',$contents);
            $secondSplitTheater=split('<div class="address">|<div class="mtitle">',$firstTheater[1]);
		    $theaterName=split('<|>',$secondSplitTheater[0]);
		    $theaterAddress=split('<|>',$secondSplitTheater[1]);
            
			
            $moviesTotal=count($secondSplitTheater)-2;
			$JSONFile = '{"movie":[';
            for($j=0;$j<=count($secondSplitTheater)-4;$j++)
		    {
		        $movieInfo=split('<div class="times">|<span>|</span>',$secondSplitTheater[$j+2]);
		        $movieTitleAndCover=split('<|>',$movieInfo[0]);
		        $movieCoverUrl=split('"',$movieTitleAndCover[1]);
                $finalMovieCoverUrl="http://community.flixster.com".$movieCoverUrl[1];
		        $coverContents=file_get_contents($finalMovieCoverUrl);
		        $imgSplit=split('<div class="poster">|<div class="actions">',$coverContents);
		        $imgSecondSplit=split('<|>',$imgSplit[1]);
		        $finalImgUrl=split('"',$imgSecondSplit[3]);
		
		        $temp = '{"cover":"'.$finalImgUrl[1].'", "title":"'.$movieTitleAndCover[2].'","duration":"'.$movieInfo[1].'","showtime":"'.$movieInfo[3].'","theatre":"'.$theaterName[4].'","url":"'.$finalMovieCoverUrl.'"},';
				$JSONFile = $JSONFile.$temp;

		    }

		       $movieInfo=split('<div class="times">|<span>|</span>',$secondSplitTheater[count($secondSplitTheater)-1]);
		        $movieTitleAndCover=split('<|>',$movieInfo[0]);
		        $movieCoverUrl=split('"',$movieTitleAndCover[1]);
                $finalMovieCoverUrl="http://community.flixster.com".$movieCoverUrl[1];
		        $coverContents=file_get_contents($finalMovieCoverUrl);
		        $imgSplit=split('<div class="poster">|<div class="actions">',$coverContents);
		        $imgSecondSplit=split('<|>',$imgSplit[1]);
		        $finalImgUrl=split('"',$imgSecondSplit[3]);
		
		        $temp = '{"cover":"'.$finalImgUrl[1].'", "title":"'.$movieTitleAndCover[2].'","duration":"'.$movieInfo[1].'","showtime":"'.$movieInfo[3].'","theatre":"'.$theaterName[4].'","url":"'.$finalMovieCoverUrl.'"}]}';
                $JSONFile = $JSONFile.$temp;
				echo $JSONFile;
		
	

?>