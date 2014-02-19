<?php
  
   $con = mysql_connect("mysql.solsisters.groupshot.us","navneet","123@navneet");
		if (!$con)
		{
			die('Could not connect: ' . mysql_error());
		}
	
	mysql_select_db("sunsetimges", $con);


   $file_path = "uploads/";
     
   $file_path = $file_path . basename( $_FILES['uploaded_file']['name']);
   if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $file_path)) {

        $qry=mysql_query("Insert into  imagepath (path)  values ('".$file_path."')");

        
       echo "success";
   } else{
       echo "fail";
   }
?>