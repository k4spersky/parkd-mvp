 <?php
 $con = mysqli_connect("pjohnston37.students.cs.qub.ac.uk", "pjohnston37", "pqjg4ll4k3wytzmv", "pjohnston37");
  //Getting the page number which is to be displayed

  $user = $_POST["email"];

  $datey = date("Y-m-d H:i:s");
  $day = substr($datey, 8, 2);
  $month = substr($datey, 5, 2);
  $year = substr($datey, 0, 4);
  $finaldate = "$day/$month/$year";



  //Counting the total item available in the database
  $sql = "SELECT u2.first_name, u2.last_name, bookings.datebooking, spaces.address, spaces.postcode, bookings.duration, bookings.price, spaces.image_address FROM bookings JOIN user ON bookings.userID = user.user_id JOIN spaces ON bookings.spaceID = spaces.id JOIN user u2 ON spaces.userID = u2.user_id WHERE bookings.datebooking >= '$finaldate' AND user.email =  '$user'";


  //Getting result
  $result = mysqli_query($con,$sql);

  //Adding results to an array
  $res = array();

  while($row = mysqli_fetch_array($result)){
  array_push($res, array(
  //"firstname"=>$row['first_name'],
  //"lastname"=>$row['last_name'],
  "bookingdate"=>$row['datebooking'],
  "address"=>$row['address'],
  "postcode"=>$row['postcode'],
  //"duration"=>$row['duration'],
  //"price"=>$row['price'],
  "image"=>$row['image_address'])
  );
  }
  //Displaying the array in json format
  echo json_encode($res);


            // echo "over";

 ?>
