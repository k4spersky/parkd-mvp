<?php
      $con = mysqli_connect("pjohnston37.students.cs.qub.ac.uk", "pjohnston37", "pqjg4ll4k3wytzmv", "pjohnston37");
    
	
    /* $email = $_POST["email"];
    $card_number = $_POST["number"];
    $expire_date = $_POST["expire"];
    $cvv = $_POST["cvv"]; */
	$email = "pjohnston37@qub.ac.uk";
    $card_number = "123456789101212";
    $expire_date = "10/03/2013";
    $cvv = "345";
	
	$datey = date("Y-m-d H:i:s");
	  $day = substr($datey, 8, 2);
	  $month = substr($datey, 5, 2);
	  $year = substr($datey, 0, 4);
	  $finaldate = "$day/$month/$year";
	
	$query = "Select user_id from user where email = '$email'";
	$result = mysqli_query($con, $query);
    $row = mysqli_fetch_row($result);
	$id = $row[0];

	$sql = "INSERT INTO cardDetails(card_number, expire_date, cvv, dateAdded, user_id) VALUES('$card_number', '$expire_date', '$cvv', '$finaldate', '$id')";
    $stmt = mysqli_prepare($con, $sql) or die(mysqli_error($con));
	//mysqli_stmt_bind_param($statement, "siss", $n, $username, $age, $password);
    mysqli_stmt_execute($stmt);
    
    $response = array();
    $response["success"] = true;  
    
    echo json_encode($response);
?>