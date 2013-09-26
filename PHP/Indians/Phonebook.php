<?php
include_once 'PhonebookEntry.php';
class Phonebook {
    private $entries=array();

    public function __construct($entries = null){
    if($entries != null) $this->entries=$entries;
    }

    public function add($entry){
        $this->entries[]=$entry;
    }

    public function getSorted ($direction) {
        $copy = $this->entries;
        if($direction == 'asc')array_multisort($copy,SORT_ASC);
        if($direction == 'desc')array_multisort($copy,SORT_DESC);
        return $copy;
        
    }

    public function getByFirstLetter ($letter){
        if(is_string($letter)==false){echo 'Wrong argument'; exit;}
      $letter=trim($letter);
      $letter = str_split($letter);
      $letter = strtoupper($letter[0]);
      $copy = $this->entries;
      $sorted=array();
      foreach ($copy as $entry){
            $a=str_split($entry->getFirstname());
            $b=str_split($entry->getFamilyname());
            if($a[0] == $letter || $b[0] == $letter) $sorted[]=$entry;
      }
      return $sorted;
     
    }

    public function getByPhoneNumber ($numberPart){
$sorted = array();
        foreach ($this->entries as $entry){
$z=strpos(' '.$entry->getHomePhone(),$numberPart);
if($z!=false){$sorted[]=$entry; continue;}
$z=strpos(' '.$entry->getMobilePhone(),$numberPart);
if($z!=false)$sorted[]=$entry;
}
return $sorted;
}
}
?>
