<?php
      $con = mysqli_connect("pjohnston37.students.cs.qub.ac.uk", "pjohnston37", "pqjg4ll4k3wytzmv", "pjohnston37");
    
	
    $email = $_POST["email"];
    $card_number = $_POST["card_number"];
    $expire_date = $_POST["expire_date"];
    $cvv = $_POST["cvv"]; 
    $type = $_POST["type"];
    $digits = $_POST["digits"];
    $manual = $_POST["manual"];
    $url = "":
     
   if($type == "VISA"){
	$url = "http://pjohnston37.students.cs.qub.ac.uk/Android/cardPics/visa.png"
	} else if($type == "MASTERCARD"){
		$url = "http://pjohnston37.students.cs.qub.ac.uk/Android/cardPics/mastercard.png"}
          else if($type == "American Express"){
$url = "http://pjohnston37.students.cs.qub.ac.uk/Android/cardPics/americanexpress.png"}
else{}

		
	
	$datey = date("Y-m-d H:i:s");
	  $day = substr($datey, 8, 2);
	  $month = substr($datey, 5, 2);
	  $year = substr($datey, 0, 4);
	  $finaldate = "$day/$month/$year";
	
	$query = "Select user_id from user where email = '$email'";
	$result = mysqli_query($con, $query);
    	$row = mysqli_fetch_row($result);
	$id = $row[0];

	$sql = "Select card_number from cardDetails where card_number = '$card_number' AND user_id = '$id'";
        if ($result = $mysqli->query($sql)){
        $row_cnt = $result->num_rows;
        if ($row_cnt == 0){
	
	if($manual == "Yes")
	{
		$len = strlen($card_number);
		if($len == 20)
		{
			$digits = substr($card_number, 16, 4);
                }else{
			$digits = substr($card_number, 11, 4);
                }
		$sql = "INSERT INTO cardDetails(card_number, expire_date, cvv, dateAdded, user_id, card_type, digits) VALUES('$card_number', '$expire_date', '$cvv', '$finaldate', '$id', '', '$digits')";
		
	}else{
		$sql = "INSERT INTO cardDetails(card_number, expire_date, cvv, dateAdded, user_id, card_type, digits) VALUES('$card_number', '$expire_date', '$cvv', '$finaldate', '$id', '$type', '$digits')";
	}

	
    $stmt = mysqli_prepare($con, $sql) or die(mysqli_error($con));
	//mysqli_stmt_bind_param($statement, "siss", $n, $username, $age, $password);
    mysqli_stmt_execute($stmt);
    
    $response = array();
    $response["success"] = true;  
    
    echo json_encode($response);
	}else{
		$response = array();
    		$response["success"] = false; 
	}
?>