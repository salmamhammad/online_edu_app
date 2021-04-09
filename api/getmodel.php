<?php 

require_once 'include/connect.php';


  $stmt = $connect->prepare("select * from models  ");
  $stmt->execute();
 
  if($stmt->rowCount()>0){

  $rows = $stmt->fetchall();
      foreach ($rows as $row) {
          $status="success";
          $id=$row['id'];
          $title=$row['title']; 
          $text=$row['text'];
          $img=$row['img'];
          $counter=$row['counter'];
          $id_level=$row['id_level']; 
 
    $posts[] = array('status'=>$status,'id'=>$id,'title'=> $title, 'text'=> $text ,'counter'=>$counter,'id_level'=>$id_level);
      }
    
  }
  else{
    $status="error";
    $posts[] = array('status'=>$status);
  

  }
  $response['model'] = $posts;
  $fp = fopen('models.json', 'w');


  fwrite($fp, json_encode( $response));
  fclose($fp);
  
  header("location:models.json");

?>