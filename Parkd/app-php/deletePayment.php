<?php
    $con = new mysqli("pjohnston37.students.cs.qub.ac.uk", "pjohnston37", "pqjg4ll4k3wytzmv", "pjohnston37");
	
	$dig = $_POST["digits"];
	$diglen = strlen($dig);
	$len = $diglen - 4;
	$digits = substr($dig, $len, 4);
	
	$sql = "Delete from cardDetails where digits = '$digits'";
	mysqli_query($con,$sql);
	$con->close();
	echo "Success";
	
?>
