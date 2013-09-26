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
        $result = 'SELECT * FROM entries
ORDER BY firstname '.$direction.' ';
        $result = mysql_query($result);
while ($rowData = mysql_fetch_assoc($result)){
	    $phonebookEntry = new PhonebookEntry();
	    $phonebookEntry->setData($rowData);
            $this->add($phonebookEntry);
	}
        return $this->entries;
    }

    public function getByFirstLetter ($letter){
        if(is_string($letter)==false){echo 'Wrong argument'; exit;}
      $letter = str_split(trim($letter));
      $letter = strtoupper($letter[0]);
       $result = 'SELECT * FROM entries
        WHERE firstname LIKE \''.$letter.'%\' OR familyname LIKE \''.$letter.'%\'
ORDER BY firstname ASC ';
        $result = mysql_query($result);
while ($rowData = mysql_fetch_assoc($result)){
	    $phonebookEntry = new PhonebookEntry();
	    $phonebookEntry->setData($rowData);
            $this->add($phonebookEntry);
	}
        return $this->entries;
    }

    public function getByPhoneNumber ($numberPart){
$result = 'SELECT * FROM entries
        WHERE home_phone LIKE \'%'.$numberPart.'%\' OR mobile_phone LIKE \'%'.$numberPart.'%\'
ORDER BY firstname ASC ';
        $result = mysql_query($result);
while ($rowData = mysql_fetch_assoc($result)){
	    $phonebookEntry = new PhonebookEntry();
	    $phonebookEntry->setData($rowData);
            $this->add($phonebookEntry);
	}
        return $this->entries;
}

    public function getByFirstLetterAndNumber($letter,$numberPart){
         if(is_string($letter)==false){echo 'Wrong argument'; exit;}
      $letter = str_split(trim($letter));
      $letter = strtoupper($letter[0]);
       $result = 'SELECT * FROM entries
        WHERE firstname LIKE \''.$letter.'%\' OR familyname LIKE \''.$letter.'%\'
ORDER BY firstname ASC ';
        $result = mysql_query($result);
while ($rowData = mysql_fetch_assoc($result)){
	    $phonebookEntry = new PhonebookEntry();
	    $phonebookEntry->setData($rowData);
            $this->add($phonebookEntry);
	}$sorted = array();
        foreach ($this->entries as $entry){
$z=strpos(' '.$entry->getHomePhone(),$numberPart);
if($z!=false){$sorted[]=$entry; continue;}
$z=strpos(' '.$entry->getMobilePhone(),$numberPart);
if($z!=false)$sorted[]=$entry;
}
$this->entries=$sorted;
return $this->entries;
}

public function hasEntry($PhonebookEntry){
$row = mysql_fetch_assoc(mysql_query(' SELECT COUNT(*) AS total FROM entries
        WHERE firstname LIKE \''.$PhonebookEntry->getFirstname().'\' AND familyname LIKE \''.$PhonebookEntry->getFamilyname().'\''));
    if($row['total'] == 0) return false;
    else return true;
}

}
?>
