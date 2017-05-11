<?php
$con = mysqli_connect("pjohnston37.students.cs.qub.ac.uk", "pjohnston37", "pqjg4ll4k3wytzmv", "pjohnston37");
//Getting the page number which is to be displayed
 $email = $_GET['id'];
//$email = "pjohnston37@qub.ac.uk";
	
	
  $sql = "select card_type, digits, image_source from cardDetails JOIN user ON cardDetails.user_id = user.user_id Where user.email = '$email'";
  
   //Getting result
  $result = mysqli_query($con,$sql);

  //Adding results to an array
  $res = array();

  while($row = mysqli_fetch_array($result)){
  array_push($res, array(
  
  "type"=>$row['card_type'],
  "digits"=>$row['digits'],
  "url"=>$row['image_source'])
  );
  }
  //Displaying the array in json format
  echo json_encode($res);
?>