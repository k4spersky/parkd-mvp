<?php
    $con = mysqli_connect("pjohnston37.students.cs.qub.ac.uk", "pjohnston37", "pqjg4ll4k3wytzmv", "pjohnston37");
    
    $username = $_POST["username"];
    $password = $_POST["password"];
    $statement = mysqli_prepare($con, "SELECT * FROM user WHERE username = ?");
    mysqli_stmt_bind_param($statement, "s", $username);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $firstname, $lastname, $username, $colpassword, $email);
    
    $response = array();
    $response["success"] = false;

    
    while(mysqli_stmt_fetch($statement)){
    if(password_verify($password, $colpassword)){
            $response["success"] = true;
            $response["firstname"] = $firstname;
            $response["lastname"] = $lastname;
            $response["username"] = $username;
            $response["password"] = $password;
           $response["email"] = $email;
           }
            }
    
    echo json_encode($response);
?>
