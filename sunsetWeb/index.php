
<?php 
	
	 $con = mysql_connect("mysql.solsisters.groupshot.us","navneet","123@navneet");
		if (!$con)
		{
			die('Could not connect: ' . mysql_error());
		}
	
	mysql_select_db("sunsetimges", $con);
?>
<head>
<title>Where in the world is the sun rising and setting right now</title>
<link rel="stylesheet" href="css/prettyPhoto.css" type="text/css" />
<link rel="stylesheet" href="css/flexslider.css" type="text/css" />
<link rel="stylesheet" href="css/style.css" type="text/css" />


<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/all-in-one-min.js"></script>
<script type="text/javascript" src="js/setup.js"></script>
<script type="text/javascript" src="js/main.js"></script>
</head>

 <style type="text/css"> 
body  {
    margin: 0; 
    padding: 0;
    text-align: center;
}
.bodyclass #container { 

    margin: 0 auto;
    text-align: center;
} 
</style>


<img src="a.jpg" height="320px" width="100%" />
<body class="bodyclass">
<div id="container">

RIGHT NOW UTC: <span id="UTC_za00"></span>
 <?php
date_default_timezone_set("UTC");
$tim= date("H", time()); 

switch ($tim) {
   case 01:
	        echo "<font size='4'>the sun is RISING in ISTANBUL(above) and SETTING in Landon (below) </font>";
	        break;
    case 02:
	          echo "<font size='4'>the sun is RISING in Bangladesh(above) and SETTING in Brazil (below)</font>";
			   break;
    case 03:
	        echo "<font size='4'>the sun is RISING in New Dehli (above) and SETTING in Bangladesh (below)</font>";
			break;
    case 04:
	        echo "<font size='4'>the sun is simultaneously RISING in Dubai, Mauritius, Seychelles (above) and SETTING in Denmark (below)</font>";
			break;
    case 05:
	        echo "<font size='4'> rigt now, the sun is simultaneously RISING/\ in Moscow, Tblisi, Ridyah (above) and SETTING\/ in Germany (below).</font>";
			break;

    case 06:
        echo "<font size='4'>the sun is RISING in ISTANBUL(above) and SETTING in Landon (below)</font>";
        break;
    case 07:
        echo "<font size='4'>the sun is RISING in  Tel Aviv(above) and SETTING in Brazil (below)</font>";
        break;
  
	case 09:
    case 10:
    case 11:
    case 12:	
    case 08:
        echo "<font size='4'>the sun is RISING in  MAURITANIA(above) and SETTING in Bangladesh (below)</font>";
        break;
	case 13:
        echo "<font size='4'>the sun is RISING in  Maine & Boston (above) and SETTING in Denmark (below)</font>";
        break;
    case 14:
        echo "<font size='4'>the sun is RISING in  Minnesota(above) and SETTING in Germany (below)</font>";
        break;
    case 15:
        echo "<font size='4'>the sun is RISING in  Vegas (above) and SETTING in Spain (below)</font>";
        break;
	case 16:
        echo "<font size='4'>the sun is RISING in  San Francisco (above) and SETTING in Turkey (below)</font>";
        break;
    case 17:
        echo "<font size='4'>the sun is RISING in  Western Canada (above) and SETTING in United states (below)</font>";
        break;
    case 18:
        echo "<font size='4'>the sun is RISING in  Alaska & Hawaii (above) and SETTING in Switzerland (below)</font>";
        break;
	case 19:
        echo "the sun is RISING in  San RUSSIA (above) and SETTING in Turkey (below)</font>";
        break;
    case 20:
        echo "<font size='4'>the sun is RISING in  San Australia (above) and SETTING in India (below)</font>";
        break;
    case 21:
        echo "<font size='4'>the sun is RISING in  MAURITANIA (above) and SETTING in Egypt (below)</font>";
        break;
	case 22:
        echo "<font size='4'>the sun is RISING in  ISTANBUL (above) and SETTING in France (below)</font>";
        break;
		
			
    case 23:
        echo "<font size='4'>the sun is RISING in  San Tel Aviv (above) and SETTING in Sri Lanka (below)</font>";
        break;
	
	
    
}
?>

<p>To contribute sunset & sunrise photos to this project and help make a real time match (and make $100), get the <a 

href="https://play.google.com/store/apps/details?id=com.joya.matchthesunset" id="time_is_links">android app</a> or follow us <a 

href="https://twitter.com/2pics1sun" id="time_is_linkss">@2pics1sun</a> on twitter </p><!-- Place this tag where you want the +1 button to render. -->
<div class="g-plusone" data-size="tall" data-annotation="inline" data-width="600"></div>

<!-- Place this tag after the last +1 button tag. -->
<script type="text/javascript">
  (function() {
    var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
    po.src = 'https://apis.google.com/js/platform.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
  })();
</script>
<a href="http://time.is/" id="time_is_link"></a>
<script src="http://widget.time.is/en.js"></script>
<script>

time_is_widget.init({
UTC_za00 : {},
New_York_z161 : {
template: "SUN",
sun_format: "srhour:srminute",
coords: "40.71427,-74.00597"
},
Tokyo_z444 : {
coords: "59.33258,18.06490"
}
});
</script></div>
</body>

<!-- Slider -->

    <div >

            <div  >
            <ul >
       <?php $qry="SELECT * from imagepath ORDER BY id DESC LIMIT 1";
             $result=mysql_query($qry);
          while($fetch=mysql_fetch_assoc($result))
          {
        ?>
                <li>
                    <img src="<?php echo $fetch['path']; ?>"  height="360" width="100%" alt="" />
                </li>
               <?php } ?>     
                 
                </ul>
            </div>
        </div>
    

