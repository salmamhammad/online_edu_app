<?php 

require_once 'include/connect.php';
if($_POST){
$time=$_POST['time'];


	$stmt = $connect->prepare("select * from course where time>$time ");
  $stmt->execute();
 
  if($stmt->rowCount()>0){
    $stmt1 = $connect->prepare("select * from course ");
    $stmt1->execute();
    
    if($stmt1->rowCount()>0){
       $rows = $stmt1->fetchall();
       $response['status'] ="success";
         foreach ($rows as $row) {
      
          $id=$row['id'];
          $name=$row['name']; 
          $discrp=$row['description'];
          $numlevel=$row['num_level'];
 
    $posts[] = array('id'=>$id,'name'=> $name, 'discrp'=> $discrp ,'numlevel'=>$numlevel);
      }
      $response['course'] = $posts;
    
  }
  else{
    $response['status'] ="error";
  
  

  }
}
  else{
    $response['status'] ="notupdate";
  



  } 

  $fp = fopen('ucourse.json', 'w');


  fwrite($fp, json_encode( $response));
  fclose($fp);
  
  header("location:course.json");
}
?>