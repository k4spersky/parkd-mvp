<?php
    $mysqli = new mysqli("pjohnston37.students.cs.qub.ac.uk", "pjohnston37", "pqjg4ll4k3wytzmv", "pjohnston37");
   $firstname = $_POST["firstname"];
    $lastname = $_POST["lastname"];
    $password = $_POST["password"];
    $email = $_POST["email"];


        $sql = "Select email from user where email = '$email'";
        if ($result = $mysqli->query($sql)){
        $row_cnt = $result->num_rows;
        if ($row_cnt == 0)
        {
        //Create User
        $password = password_hash($password, PASSWORD_DEFAULT);
        $sql = "INSERT INTO user (first_name, last_name, password, email) VALUES ('$firstname', '$lastname', '$password', '$email')";
        $result = $mysqli->query($sql);
        //$result->close();
        $mysqli->close();
        //Send email

                             $response = array();
                             $response["success"] = true;
                             if ($response["success"] == true)
                             {
                                 $to = $email;
                                 $subject = "Welcome to parkD";
                                 $message = "
                                 <html>
                                 <head>
                                 <title>Successful Regisration</title>
                                 </head>
                                 <body>
                                 <h1>Welcome to parkD </h2>
                                 <p>Your account has been created and you can now start booking parking spaces</p>
                                 <p>If you would like to contact us for any reason please get in touch through our help desk." . "\r\n" . " They will be able to tell you everything you need to know about your account, booking policy and anything else on your mind.</p>
                                 </body>
                                 </html>
                                 ";

                                 // Always set content-type when sending HTML email
                                 $headers = "MIME-Version: 1.0" . "\r\n";
                                 $headers .= "Content-type:text/html;charset=UTF-8" . "\r\n";

                                 // More headers
                                 $headers .= 'From: <register@parkD.com>' . "\r\n";


                                 mail($to,$subject,$message,$headers);
                 }else{
        //Email Exists
         $response = array();
         $response["success"] = false;
         $response["text"] = "Email Exists";
        }
        }

}
//send results back to app
echo json_encode($response);
?>
