<?php
    $con = new mysqli("pjohnston37.students.cs.qub.ac.uk", "pjohnston37", "pqjg4ll4k3wytzmv", "pjohnston37");
	
	$locate = $_POST["loc"];
	//$locate = "BT9";
	$sql = "Select id, lat, lng, price_per_hour, postcode from spaces where  postcode = '$locate' OR location = '$locate' OR SUBSTRING(postcode,1, 3) = '$locate'";
	$result = mysqli_query($con,$sql);
	
    /*$response["success"] = true;

    
    while(mysqli_stmt_fetch($statement)){
		$response["lat"] = $lat;
		$response["lng"] = $lng;
		$response["price"] = $price;
		$postcode["post"] = $post;
           
    }*/
	$res = array();

  while($row = mysqli_fetch_array($result)){
  array_push($res, array(
	"lat"=>$row['lat'],
	"id"=>$row['id'],
  "lng"=>$row['lng'],
  "postcode"=>$row['postcode'],
  "price"=>$row['price_per_hour'])
  );
  }

	echo json_encode($res);
	
?>
