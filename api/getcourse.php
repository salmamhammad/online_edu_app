<?php 

require_once 'include/connect.php';

	$stmt = $connect->prepare("select * from course ");
  $stmt->execute();
 
  if($stmt->rowCount()>0){

  $rows = $stmt->fetchall();
      foreach ($rows as $row) {
          $status="success";
          $id=$row['id'];
          $name=$row['name']; 
          $discrp=$row['description'];
          $numlevel=$row['num_level'];
 
    $posts[] = array('status'=>$status,'id'=>$id,'name'=> $name, 'discrp'=> $discrp ,'numlevel'=>$numlevel);
      }
    
  }
  else{
    $status="error";
    $posts[] = array('status'=>$status);
  

  }
  $response['course'] = $posts;
  $fp = fopen('course.json', 'w');


  fwrite($fp, json_encode( $response));
  fclose($fp);
  
  header("location:course.json");
?>