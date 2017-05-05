 <?php
 $con = mysqli_connect("pjohnston37.students.cs.qub.ac.uk", "pjohnston37", "pqjg4ll4k3wytzmv", "pjohnston37");
  //Getting the page number which is to be displayed
  //$page = $_GET['page'];
  $user = $_GET["id"];
   //$user = 'pjohnston37@qub.ac.uk';




  //Counting the total item available in the database
  $sql = "SELECT price_per_hour, address, postcode, space_type, description, show_space, image_address FROM spaces Join user on spaces.userID = user.user_id where user.email = '$user'";


  //Getting result
  $result = mysqli_query($con,$sql);

  //Adding results to an array
  $res = array();

  while($row = mysqli_fetch_array($result)){
  array_push($res, array(
  "show"=>$row['show_space'],
  "type"=>$row['space_type'],
  "desc"=>$row['description'],
  "price"=>$row['price_per_hour'],
  "address"=>$row['address'],
  "postcode"=>$row['postcode'],
  "image"=>$row['image_address'])
  );
  }
  //Displaying the array in json format
  echo json_encode($res);


            // echo "over";

 ?>
