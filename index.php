<?php 
 $DB_host = "localhost";
 $DB_user = "id8311683_root";
 $DB_pass = "76199766";
 $DB_name = "id8311683_android";
		try
		{
		     $connect = new PDO("mysql:host={$DB_host};dbname={$DB_name};charset=utf8",
		     	$DB_user,$DB_pass);
		     $connect->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		}
		catch(PDOException $e)
		{
		     echo $e->getMessage();
		}

$sql="select * from student "; 

                     
$stmt1 = $connect->prepare("SELECT * FROM `student` ");
$stmt1->execute();
if ($stmt1->rowCount()>0) {
    $rows = $stmt1->fetchall();
   
    foreach ($rows as $row) {
  $name=$row['name']; 
  $age=$row['age']; 
$location=$row['location'];
  $posts[] = array('name'=> $name, 'age'=> $age);
} 

$response['student'] = $posts;
$response['location']=$location;
$fp = fopen('my.json', 'w');

fwrite($fp, json_encode(array('item' => $response)));
fclose($fp);
}
header("location:my.json");
?> 