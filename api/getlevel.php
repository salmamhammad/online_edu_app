<?php 

require_once 'include/connect.php';
if($_POST){
  $id_course=$_POST['id_course'];
  	$stmt = $connect->prepare("select * from level where idcourse=$id_course ");
  $stmt->execute();
 
  if($stmt->rowCount()>0){

  $rows = $stmt->fetchall();
      foreach ($rows as $row) {
          $status="success";
          $id=$row['id'];
          $name=$row['name']; 
          $idcourse=$row['idcourse'];
          $numques=$row['numques'];
 
    $posts[] = array('status'=>$status,'id'=>$id,'name'=> $name, 'idcourse'=> $idcourse ,'numques'=>$numques);
      }
    
  }
  else{
    $status="error";
    $posts[] = array('status'=>$status);
  

  }
  $response['level'] = $posts;
  $fp = fopen('level.json', 'w');


  fwrite($fp, json_encode( $response));
  fclose($fp);
  
  header("location:level.json");
}
?>