<?php
    $con = new mysqli("pjohnston37.students.cs.qub.ac.uk", "pjohnston37", "pqjg4ll4k3wytzmv", "pjohnston37");
	
	$locate = $_POST["id"];
	//$locate = "1";
	$sql = "Select price_per_hour, postcode, address, location, image_address, numberOfSpaces, space_type, description from spaces where  id = '$locate'";
	$result = mysqli_query($con,$sql);
	
   
	$res = array();

  while($row = mysqli_fetch_array($result)){
  array_push($res, array(
	"price"=>$row['price_per_hour'],
  "address"=>$row['address'],
  "postcode"=>$row['postcode'],
  "location"=>$row['location'],
  "image"=>$row['image_address'],
  "num"=>$row['numberOfSpaces'],
  "type"=>$row['space_type'],
  "desc"=>$row['description'])
  );
  }

	echo json_encode($res);
	
?>
