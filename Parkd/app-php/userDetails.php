<?php
    $con = new mysqli("pjohnston37.students.cs.qub.ac.uk", "pjohnston37", "pqjg4ll4k3wytzmv", "pjohnston37");
	
	$email = $_POST["user"];
	//$email = "pjohnston37@qub.ac.uk";
	$sql = "Select first_name, last_name, phone_number, email from user where email = '$email'";
	$statement = mysqli_prepare($con, $sql);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $firstname, $lastname, $number, $email);
	
	$response = array();
    $response["success"] = true;

    
    while(mysqli_stmt_fetch($statement)){
		$response["firstname"] = $firstname;
		$response["lastname"] = $lastname;
		$response["phone"] = $number;
		$response["email"] = $email;
           
    }

	echo json_encode($response);
	
?>
