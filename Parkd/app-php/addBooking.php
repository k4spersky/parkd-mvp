<?php
    $con = new mysqli("pjohnston37.students.cs.qub.ac.uk", "pjohnston37", "pqjg4ll4k3wytzmv", "pjohnston37");
	
	$digits = $_POST['dig'];
	$diglen = strlen($digits);
	$len = $diglen - 4;
	$digits = substr($digits, $len, 4);
	$user = $_POST['id'];
	$fromTime = $_POST['from'];
	$toTime = $_POST['to'];
	$dateofBooking = $_POST['date'];
	$spaceID = $_POST['space'];
	$price = $_POST['price'];
	$address = "";
	//$response = array();
	$datey = date("Y-m-d H:i:s");
	/*$digits = "0103";
	$user = "pjohnston37@qub.ac.uk";
	$fromTime = "9:00";
	$toTime = "12:00";
	$dateofBooking = "2017-06-12";
	$spaceID = 2;
	$price = "2.35";
	*/
	$duration = $toTime - $fromTime;
	$fee = $price * 0.80;
  $day = substr($datey, 8, 2);
  $month = substr($datey, 5, 2);
  $year = substr($datey, 0, 4);
  $finaldate = "$year-$month-$day";
                             
	
	
			
		$sql = "Select id from bookings where datebooking = '$dateofBooking' AND spaceID = '$spaceID' OR start_time = '$fromTime' OR end_time = '$toTime' OR start_time BETWEEN '$fromTime' AND '$toTime' OR end_time BETWEEN '$fromTime' AND '$toTime' ";
			if ($result = $con->query($sql)){
			$row_cnt = $result->num_rows;
			if ($row_cnt == 0) {
			//NO Bookings found for that time
			$insert_payment = "Insert into payments (card_id, payment_date) Values((Select id from cardDetails where digits = '$digits'), '$finaldate')";
			mysqli_query($con,$insert_payment);
			$paymentID = mysqli_insert_id($con);
			$insert_bookings = "Insert into bookings (userID, datebooking, spaceID, paymentID, price, duration, start_time, end_time) Values ((Select user_id from user where email = '$user'),'$dateofBooking', '$spaceID', '$paymentID', '$price', '$duration', '$fromTime', '$toTime')";
			mysqli_query($con,$insert_bookings);
			echo "success";
			$to = $user;
                                 $subject = "Booking Confirmation";
                                 $message = "
                                 <html>
                                 <head>
                                 <title>Successful Booking</title>
                                 </head>
                                 <body>
                                 <h1>Booking Confirmation for $dateofBooking</h1>
                                 <p>This email is confirmation of your booking. Feel free to get in contact if you have any issues. </p>
                                 <p>Details about your booking can be found within the my bookings section of the app.</p>
								 <p> $dateofBooking between $fromTime - $toTime.</p>
								 <p>Details of transaction: " . "\r\n" . "Booking price: £$price</p>
                                </body>
                                 </html>
                                 ";

                                 // Always set content-type when sending HTML email
                                 $headers = "MIME-Version: 1.0" . "\r\n";
                                 $headers .= "Content-type:text/html;charset=UTF-8" . "\r\n";

                                 // More headers
                                 $headers .= 'From: <bookings@parkD.com>' . "\r\n";


                                 mail($to,$subject,$message,$headers);
								 
					//send email to the renter to confirm booking
					$sql = "select user.email, spaces.address from user join spaces ON user.user_id = spaces.userID where spaces.id = '$spaceID'";
					$result = $con->query($sql);
					while ($obj = $result->fetch_object()) {
							$to =$obj->email;
							$address = $obj->address;
							}

                                 $subject = "Booking Confirmation";
                                 $message = "
                                 <html>
                                 <head>
                                 <title>Successful Booking</title>
                                 </head>
                                 <body>
                                 <h1>Booking Confirmed for $dateofBooking</h1>
                                 <p>$to has booked for your space at $address on $dateofBooking between $fromTime - $toTime.</p>
								 <p>Details of payment: " . "\r\n" . "Booking price: £$price " . "\r\n" . " Commission: 20% " . "\r\n" . " Total earnings: $fee</p>
                                 <p>Payment will be transfered into your account within 2 working days. " . "\r\n" . "If there is any issues please get in contact as soon as possible.</p>
                                 </body>
                                 </html>
                                 ";

                                 // Always set content-type when sending HTML email
                                 $headers = "MIME-Version: 1.0" . "\r\n";
                                 $headers .= "Content-type:text/html;charset=UTF-8" . "\r\n";

                                 // More headers
                                 $headers .= 'From: <bookings@parkD.com>' . "\r\n";


                                 mail($to,$subject,$message,$headers);
								
			}
		else{
			echo  "Booking_found";
			
			
		}
			}	
		$con->close();
	
	
	
?>