<?php
      $con = mysqli_connect("pjohnston37.students.cs.qub.ac.uk", "pjohnston37", "pqjg4ll4k3wytzmv", "pjohnston37");
    
    $firstname = $_POST["firstname"];
    $lastname = $_POST["lastname"];
    $username = $_POST["username"];
    $password = $_POST["password"];
    $email = $_POST["email"];
    $statement = mysqli_prepare($con, "INSERT INTO user (first_name, last_name, username, password, email) VALUES ('$firstname', '$lastname', '$username', '$password', '$email')");
    //mysqli_stmt_bind_param($statement, "siss", $firstname, $username, $age, $password, $email, $ dob);
    mysqli_stmt_execute($statement);
    
    $response = array();
    $response["success"] = true;  
    if ($response["success"] == true)
    {
        // Subject of confirmation email.
        $conf_subject = 'New Account';

        // Who should the confirmation email be from?
        $conf_sender = 'info@parkd.co.uk';

        $msg = $firstname . ",\n\nThank you for signing up to parkd.";

        mail( $email, $conf_subject, $msg, 'From: ' . $conf_sender );

    }
    echo json_encode($response);
?>
