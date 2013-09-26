
        <?php
       class PhonebookEntry {

     private $firstname;
     private $familyname;
     private $mobilephone;
     private $homephone;


     public function __construct($firstname, $familyname,$mobilephone = '',$homephone = '') {
          $this->setFirstname($firstname);
          $this->setFamilyname($familyname);
          $this->setMobilePhone($mobilephone);
          $this->setHomePhone($homephone);

     }



     public function setFirstname ($name) {
          $this->firstname = ucfirst(trim($name));
     }
     public function setFamilyname ($name) {
          $this->familyname = ucfirst(trim($name));
     }
     public function setMobilePhone ($number) {
        if (strlen($number) > 0) $this->mobilephone = $number;
	else $this->mobilephone = '';
     }
     public function setHomePhone ($number) {
              $this->homephone = $number;
     }
     public function getFirstname() {
          return $this->firstname;
     }
     public function getFamilyname() {
          return $this->familyname;
     }
     public function getMobilePhone() {
          return $this->mobilephone;
     }
     public function getHomePhone() {
          return $this->homephone;
     }
    

public function __toString() {

           $result = '<table style="border: 1px solid black; font-size: 11px; font-family: Tahoma; margin-bottom: 10px;" width="200" border="0" cellpadding="5" cellspacing="0"><tbody><tr><td colspan="2" bgcolor="silver"><b>'.$this->firstname.' '.$this->familyname.'</b></td></tr>';

          
          if($this->mobilephone != '') $result .= '<tr><td>Mobile:</td><td>'.$this->mobilephone.'</td></tr>';
        if (strlen($this->homephone) > 2)$result .= '<tr><td>Home:</td><td>'.$this->homephone.'</td></tr>';

          $result .= '</tbody></table>';
          return $result;

     }

public function save ($filename = 'contacts.txt') {
     if (!file_exists('data/'.$filename)) return false;
     $handler = fopen('data/'.$filename, 'a+');
     fwrite($handler, $this->firstname.','.$this->familyname.','.$this->mobilephone.','.$this->homephone."\n");
     fclose($handler);
     return true;
}
       }

        ?>
