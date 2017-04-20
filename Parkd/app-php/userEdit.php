<?php
    $con = new mysqli("pjohnston37.students.cs.qub.ac.uk", "pjohnston37", "pqjg4ll4k3wytzmv", "pjohnston37");
   $firstname = $_POST["fn"];
    $lastname = $_POST["ln"];
    $number = $_POST["pn"];
    $email = $_POST["user"];
	$newemail = $_POST["em"];
	
	/*$firstname = "f";
    $lastname = "l";
    $number = "06347";
    $email = "pjohnston37@qub.ac.uk";*/
	$sql = "Update user set first_name = '$firstname', last_name = '$lastname', phone_number = '$number', email = '$newemail' where email = '$email'";
        $result = $con->query($sql);
        //$result->close();
        $con->close();
		$response = array();
         $response["success"] = true;
	
	//send results back to app
echo json_encode($response);
?>