<?php
    $con = new mysqli("pjohnston37.students.cs.qub.ac.uk", "pjohnston37", "pqjg4ll4k3wytzmv", "pjohnston37");
	
	
	$user = $_POST['id'];
	$price = $_POST['price'];
	$address = $_POST['address'];
	$postcode = $_POST['post'];
	$datey = date("Y-m-d H:i:s");
	$lat = $_POST['lat'];
	$lng = $_POST['lng'];
	$show = 1;
	$image = "";
	$type = "Private";
	$num = 1;
	$desc = $_POST['des'];
	$loc = $_POST['loc'];

  $day = substr($datey, 8, 2);
  $month = substr($datey, 5, 2);
  $year = substr($datey, 0, 4);
  $finaldate = "$day/$month/$year";
                             
	
	
		
			//NO Bookings found for that time
			$insert_space = "Insert into spaces (lat, lng, userID, numberOfSpaces, price_per_hour, dateAdded, address, postcode, image_address, space_type, description, location, show_space) Values('$lat', '$lng', (Select user_id from user where email = '$user'), $num, $price, '$finaldate', '$address', '$postcode', '$image', '$type', '$desc', '$loc', $show)";
			  $stmt = mysqli_prepare($con, $insert_space) or die(mysqli_error($con));
				mysqli_stmt_execute($stmt);
			
			
			echo "success";
			$to = $user;
                                 $subject = "Space Added";
                                 $message = "
                                 <html>
                                 <head>
                                 <title>Thanks for posting a space</title>
                                 </head>
                                 <body>
                                 <h1>Confirmation of space</h1>
                                 <p>Many thanks for advertising your space with parkd </p>
                                 <p>Details about your space: " . "\r\n" . "Address: $address " . "\r\n" . "Postcode: $postcode " . "\r\n" . "Location: $loc" . "\r\n" . "Price per hour: £$price</p>
								<p>Thanks</p>
                                </body>
                                 </html>
                                 ";

                                 // Always set content-type when sending HTML email
                                 $headers = "MIME-Version: 1.0" . "\r\n";
                                 $headers .= "Content-type:text/html;charset=UTF-8" . "\r\n";

                                 // More headers
                                 $headers .= 'From: <spaces@parkD.com>' . "\r\n";


                                 mail($to,$subject,$message,$headers);
								 
					
								
			
		$con->close();
	
	
	
?>